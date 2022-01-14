package com.aivicevic.randomusers.domain.model.user

import com.squareup.moshi.Json

/**
 * Class which provides a model for UserId
 *
 * @property users the list of users
 */
data class UsersResponse(
    @Json(name = "results") val results: List<User>
)
