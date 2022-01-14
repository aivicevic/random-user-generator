package com.aivicevic.randomusers.domain.model.user

import com.squareup.moshi.Json

/**
 * Class which provides a model for UserName
 * @constructor Sets all properties of the UserName
 * @property title the title of user (ie. "Mr")
 * @property first the first name of user
 * @property last the last name of user
 */
data class UserName(
    @Json(name = "title") val title: String,
    @Json(name = "first") val first: String,
    @Json(name = "last") val last: String
)
