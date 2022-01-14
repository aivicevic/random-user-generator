package com.aivicevic.randomusers.presentation.main.userlist

class ToggleFavoriteStatus(
    val isEnabled: Boolean,
    val hasError: Boolean
) {

    companion object {

        fun enabled() = ToggleFavoriteStatus(isEnabled = true, hasError = false)

        fun disabled() = ToggleFavoriteStatus(isEnabled = false, hasError = false)

        fun error() = ToggleFavoriteStatus(isEnabled = true, hasError = true)
    }
}
