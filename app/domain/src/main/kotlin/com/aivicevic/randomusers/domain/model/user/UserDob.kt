package com.aivicevic.randomusers.domain.model.user

import com.squareup.moshi.Json

/**
 * Class which provides a model for UserDob
 * @constructor Sets all properties of the UserDob
 * @property date the date of birth formatted as "2015-11-04T22:09:36Z"
 * @property age the age of user
 */
data class UserDob(
    @Json(name = "date") val date: String,
    @Json(name = "age") val age: String
)
