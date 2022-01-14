package com.aivicevic.randomusers.domain.model.user

import com.squareup.moshi.Json

/**
 * Class which provides a model for UserId
 * @constructor Sets all properties of the UserId
 * @property name the name of id ("SSN")
 * @property value the uid value
 */
data class UserId(
    @Json(name = "name") val name: String,
    @Json(name = "value") val value: String
)
