package com.aivicevic.randomusers.domain.model.user

import com.squareup.moshi.Json

/**
 * Class which provides a model for UserPicture
 * @constructor Sets all properties of the UserPicture
 * @property large the url of large image
 * @property medium the url of medium image
 * @property thumbnail the url of thumbnail image
 */
data class UserPicture(
    @Json(name = "large") val large: String,
    @Json(name = "medium") val medium: String,
    @Json(name = "thumbnail") val thumbnail: String
)
