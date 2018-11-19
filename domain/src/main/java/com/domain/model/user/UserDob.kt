package com.domain.model.user

/**
 * Class which provides a model for UserDob
 * @constructor Sets all properties of the UserDob
 * @property date the date of birth formatted as "2015-11-04T22:09:36Z"
 * @property age the age of user
 */
data class UserDob(
        val date: String,
        val age: String
)