package com.domain.model.user

/**
 * Class which provides a model for UserName
 * @constructor Sets all properties of the UserName
 * @property title the title of user (ie. "Mr")
 * @property first the first name of user
 * @property last the last name of user
 */
data class UserName(
        val title: String,
        val first: String,
        val last: String
)