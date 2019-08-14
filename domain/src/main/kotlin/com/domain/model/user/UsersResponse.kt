package com.domain.model.user

/**
 * Class which provides a model for UserId
 * @constructor Sets all properties of the UserId
 * @property results the list of users
 */
data class UsersResponse(
    val results: List<User>
)
