package com.aivicevic.randomusers.presentation.main.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aivicevic.randomusers.domain.repository.UserRepository
import com.aivicevic.randomusers.util.DatesUtil

// TODO("Add functionality to get a random user detail page)
// TODO("Add functionality for favorite status to save from details page)
class UserDetailsViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _userProfilePic = MutableLiveData<String>()
    val userProfilePic: LiveData<String> get() = _userProfilePic

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _userDob = MutableLiveData<String>()
    val userDob: LiveData<String> get() = _userDob

    private val _userLocation = MutableLiveData<String>()
    val userLocation: LiveData<String> get() = _userLocation

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    private val _userPhone = MutableLiveData<String>()
    val userPhone: LiveData<String> get() = _userPhone

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun populateUserData(
        userProfilePic: String,
        userName: String,
        userDob: String,
        userLocation: String,
        userEmail: String,
        userPhone: String,
        isFavorite: Boolean
    ) {
        _userProfilePic.value = userProfilePic
        _userName.value = userName
        _userDob.value = DatesUtil.parseAndFormatDate(
            userDob,
            DatesUtil.ISO_SERVER_DATE,
            DatesUtil.BASIC_DATE_FORMAT_LONG
        )
        _userLocation.value = userLocation
        _userEmail.value = userEmail
        _userPhone.value = userPhone
        _isFavorite.value = isFavorite
    }

    fun toggleIsFavorite() {
        isFavorite.value?.let {
            _isFavorite.value = !it
        }
    }
}
