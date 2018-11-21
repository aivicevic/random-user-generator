package com.randomusers.ui.userlist

import android.arch.lifecycle.MutableLiveData
import com.domain.model.user.User
import com.domain.repository.UserRepository
import com.domain.scheduler.SchedulerProvider
import com.randomusers.R
import com.randomusers.common.BaseViewModel
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val stateLiveData = MutableLiveData<UserListState>()
    var isFavoritesList = false

    init {
        stateLiveData.value = UserListState(UserListStateType.DEFAULT, emptyList())
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }

    fun updateUserList() {
        stateLiveData.value = UserListState(UserListStateType.LOADING, obtainCurrentData())
        getUserList()
    }

    fun resetUserList() {
        stateLiveData.value = UserListState(UserListStateType.LOADING, emptyList())
        if (!isFavoritesList) {
            userRepository.deleteNonFavoritesFromDb()
        }
        updateUserList()
    }

    fun restoreUserList() {
        stateLiveData.value = UserListState(UserListStateType.DEFAULT, obtainCurrentData())
    }

    fun toggleFavorite(user: User) {
        userRepository.updateFavoriteInDb(user.also { it.isFavorite = !it.isFavorite })
        if (isFavoritesList) {
            resetUserList()
        }
    }

    private fun getUserList() {
        val observable = if (isFavoritesList) userRepository.getFavoritesFromDb() else userRepository.getUsers(100)
        addDisposable(observable
            .compose(applySchedulersSingle(schedulerProvider))
            .doOnSubscribe { onLoadUserList() }
            .subscribe(
                { result -> onRetrieveUserListSuccess(result) },
                { onRetrieveUserListError() }
            )
        )
    }

    private fun onLoadUserList() {
        stateLiveData.value = UserListState(UserListStateType.LOADING, emptyList())
    }

    private fun onRetrieveUserListSuccess(users: List<User>) {
        val userList = obtainCurrentData().toMutableList()
        userList.addAll(users)
        stateLiveData.value = UserListState(UserListStateType.DEFAULT, userList)
        if (!isFavoritesList) {
            userRepository.saveUsersToDb(users)
        }
    }

    private fun onRetrieveUserListError() {
        stateLiveData.value = UserListState(UserListStateType.ERROR, obtainCurrentData(), R.string.load_users_error)
    }

    // TODO: Refactor this and add pagination
    private fun obtainCurrentData() = stateLiveData.value?.data ?: emptyList()
}