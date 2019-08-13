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
    var favoriteUsersLiveData: LiveData<List<User>>

    init {
        favoriteUsersLiveData = userRepository.getFavoritesFromDb()
    }

    @TestOnly
    fun reinitializeFavoritesList() {
        favoriteUsersLiveData = userRepository.getFavoritesFromDb()
    }

    fun updateUserList() {
        usersResponseLiveData.value = Response.loading()
        viewModelScope.launch(userListExceptionHandler) {
            val response = userRepository.getUsers()
            onRetrieveUserListSuccess(response)
        }
    }

    private val userListExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onRetrieveUserListError(throwable)
    }

    private fun onRetrieveUserListSuccess(usersResponse: UsersResponse) {
        usersResponseLiveData.value = Response.success(usersResponse)
    }

    private fun onRetrieveUserListError(throwable: Throwable) {
        usersResponseLiveData.value = Response.error(throwable)
    }

    fun restoreUserList() {
        usersResponseLiveData.value = Response.success(UsersResponse(obtainCurrentUserList()))
    }

    private fun obtainCurrentUserList() = usersResponseLiveData.value?.data?.results ?: emptyList()

    fun toggleFavorite(user: User) {
        user.isFavorite = !user.isFavorite
        viewModelScope.launch(favoriteUsersExceptionHandler) {
            if (user.isFavorite) {
                userRepository.saveFavoriteToDb(user)
            } else {
                userRepository.deleteFavoriteFromDb(user)
                refreshUserListFavoritesState(user)
            }
        }
    }

    private val favoriteUsersExceptionHandler = CoroutineExceptionHandler { _, _ ->
        // TODO: Add some indication to user that something went wrong
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
