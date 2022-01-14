package com.aivicevic.randomusers.presentation.main.userlist

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aivicevic.randomusers.domain.model.user.User
import com.aivicevic.randomusers.domain.repository.UserRepository
import com.aivicevic.randomusers.domain.util.Event
import com.aivicevic.randomusers.presentation.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly

class UserListViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> get() = _userList

    private val _onUserClickedEvent = MutableLiveData<Event<User>>()
    val onUserClickedEvent: LiveData<Event<User>> get() = _onUserClickedEvent

    private val _errorEvent = MutableLiveData<Event<@StringRes Int>>()
    val errorEvent: LiveData<Event<Int>> get() = _errorEvent

    // TODO("Remove this and add concurrency helpers to avoid errors")
    private val _toggleFavoriteStatusLiveData = MutableLiveData<ToggleFavoriteStatus>()
    val toggleFavoriteStatusLiveData: LiveData<ToggleFavoriteStatus> get() = _toggleFavoriteStatusLiveData

    // TODO("Fix mutable/exposed live data")
    var userFavoritesList: LiveData<List<User>>

    init {
        _toggleFavoriteStatusLiveData.value = ToggleFavoriteStatus.enabled()
        userFavoritesList = userRepository.getFavoritesFromDb()
    }

    fun updateUserList() {
        showLoading()
        viewModelScope.launch(userListExceptionHandler) {
            val response = userRepository.getUsers()
            _userList.value = response.results

            hideLoading()
        }
    }

    private val userListExceptionHandler = CoroutineExceptionHandler { _, _ ->
        _errorEvent.value = Event(R.string.user_list_load_error)
        hideLoading()
    }

    @TestOnly
    fun reinitializeFavoritesList() {
        userFavoritesList = userRepository.getFavoritesFromDb()
    }

    fun toggleFavorite(user: User) {
        val updatedUser = user.copy(isFavorite = !user.isFavorite)
        viewModelScope.launch(toggleFavoriteExceptionHandler) {
            _toggleFavoriteStatusLiveData.value = ToggleFavoriteStatus.disabled()
            if (updatedUser.isFavorite) {
                userRepository.saveFavoriteToDb(updatedUser)
            } else {
                userRepository.deleteFavoriteFromDb(updatedUser)
            }
            refreshUserListFavoritesState(updatedUser)
            _toggleFavoriteStatusLiveData.value = ToggleFavoriteStatus.enabled()
        }
    }

    private val toggleFavoriteExceptionHandler = CoroutineExceptionHandler { _, _ ->
        _toggleFavoriteStatusLiveData.value = ToggleFavoriteStatus.error()
    }

    private fun refreshUserListFavoritesState(modifiedUser: User) {
        val updatedUserList = _userList.value?.map { user ->
            if (user.id.value == modifiedUser.id.value) {
                modifiedUser
            } else {
                user
            }
        }.orEmpty()
        _userList.value = updatedUserList
    }

    fun onUserClicked(user: User) {
        _onUserClickedEvent.value = Event(user)
    }

    private fun showLoading() {
        _isLoading.value = true
    }

    private fun hideLoading() {
        _isLoading.value = false
    }
}
