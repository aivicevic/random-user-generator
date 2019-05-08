package com.randomusers.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.domain.Response
import com.domain.model.user.User
import com.domain.model.user.UsersResponse
import com.domain.repository.UserRepository
import com.domain.scheduler.SchedulerProvider
import com.randomusers.common.BaseViewModel
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val usersResponseLiveData = MutableLiveData<Response<UsersResponse>>()

    lateinit var favoriteUsersLiveData: LiveData<List<User>>

    fun initFavoritesList() {
        if (!::favoriteUsersLiveData.isInitialized) {
            favoriteUsersLiveData = userRepository.getFavoritesFromDb()
        }
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
        // TODO: Add necessary logic
    }

    fun toggleFavorite(user: User) {
        user.isFavorite = !user.isFavorite
        if (user.isFavorite) {
            userRepository.saveFavoriteToDb(user)
        } else {
            userRepository.deleteFavoriteFromDb(user)
        }
    }
}
