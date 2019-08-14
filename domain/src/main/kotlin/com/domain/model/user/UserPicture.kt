package com.domain.model.user

/**
 * Class which provides a model for UserPicture
 * @constructor Sets all properties of the UserPicture
 * @property large the url of large image
 * @property medium the url of medium image
 * @property thumbnail the url of thumbnail image
 */
data class UserPicture(
    val large: String,
    val medium: String,
    val thumbnail: String
)