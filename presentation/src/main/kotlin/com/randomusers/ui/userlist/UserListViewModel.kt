package com.randomusers.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.Response
import com.domain.model.user.User
import com.domain.model.user.UsersResponse
import com.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val usersResponseLiveData = MutableLiveData<Response<UsersResponse>>()
    val toggleFavoriteStatusLiveData = MutableLiveData<ToggleFavoriteStatus>()
    var favoriteUsersLiveData: LiveData<List<User>>

    init {
        toggleFavoriteStatusLiveData.value = ToggleFavoriteStatus.enabled()
        favoriteUsersLiveData = userRepository.getFavoritesFromDb()
    }

    fun updateUserList() {
        usersResponseLiveData.value = Response.loading()
        viewModelScope.launch(userListExceptionHandler) {
            val response = userRepository.getUsers()
            usersResponseLiveData.value = Response.success(response)
        }
    }

    private val userListExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        usersResponseLiveData.value = Response.error(throwable)
    }

    fun restoreUserList() {
        usersResponseLiveData.value = Response.success(UsersResponse(obtainCurrentUserList()))
    }

    private fun obtainCurrentUserList() = usersResponseLiveData.value?.data?.results ?: emptyList()

    @TestOnly
    fun reinitializeFavoritesList() {
        favoriteUsersLiveData = userRepository.getFavoritesFromDb()
    }

    fun toggleFavorite(user: User, isFavoritesListSelected: Boolean = false) {
        user.isFavorite = !user.isFavorite  // TODO: Potential bug here if an exception occurs
        viewModelScope.launch(toggleFavoriteExceptionHandler) {
            toggleFavoriteStatusLiveData.value = ToggleFavoriteStatus.disabled()
            if (user.isFavorite) {
                userRepository.saveFavoriteToDb(user)
            } else {
                userRepository.deleteFavoriteFromDb(user)
                if (isFavoritesListSelected) {
                    refreshUserListFavoritesState(user)
                }
            }
            toggleFavoriteStatusLiveData.value = ToggleFavoriteStatus.enabled()
        }
    }

    private val toggleFavoriteExceptionHandler = CoroutineExceptionHandler { _, _ ->
        toggleFavoriteStatusLiveData.value = ToggleFavoriteStatus.error()
    }

    private suspend fun refreshUserListFavoritesState(modifiedUser: User) {
        withContext(Dispatchers.Default) {
            val userList = obtainCurrentUserList().toMutableList()
            userList.forEachIndexed { index, user ->
                if (user.id.value == modifiedUser.id.value) {
                    userList[index] = modifiedUser
                    withContext(Dispatchers.Main) {
                        usersResponseLiveData.value = Response.success(UsersResponse(userList))
                    }
                    return@forEachIndexed
                }
            }
        }
    }
}
