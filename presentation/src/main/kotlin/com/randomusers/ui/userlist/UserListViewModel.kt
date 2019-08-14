package com.randomusers.ui.userlist

import androidx.lifecycle.MutableLiveData
import com.domain.Response
import com.domain.model.user.User
import com.domain.model.user.UsersResponse
import com.domain.repository.UserRepository
import com.domain.scheduler.SchedulerProvider
import com.randomusers.common.BaseViewModel
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val usersResponseLiveData = MutableLiveData<Response<UsersResponse>>()
    var favoriteUsersLiveData = userRepository.getFavoritesFromDb()

    @TestOnly
    fun reinitializeFavoritesList() {
        favoriteUsersLiveData = userRepository.getFavoritesFromDb()
    }

    fun updateUserList() {
        addDisposable(userRepository.getUsers(100)
            .compose(applySchedulersSingle(schedulerProvider))
            .doOnSubscribe { onLoadUserList() }
            .subscribe(::onRetrieveUserListSuccess, ::onRetrieveUserListError)
        )
    }

    private fun onLoadUserList() {
        usersResponseLiveData.value = Response.loading()
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
        if (user.isFavorite) {
            userRepository.saveFavoriteToDb(user)
        } else {
            userRepository.deleteFavoriteFromDb(user)
            refreshUserListFavoritesState(user)
        }
    }

    private fun refreshUserListFavoritesState(modifiedUser: User) {
        val userList = obtainCurrentUserList().toMutableList()
        userList.forEachIndexed { index, user ->
            if (user.id.value == modifiedUser.id.value) {
                userList[index] = modifiedUser
                usersResponseLiveData.value = Response.success(UsersResponse(userList))
                return@forEachIndexed
            }
        }
    }
}
