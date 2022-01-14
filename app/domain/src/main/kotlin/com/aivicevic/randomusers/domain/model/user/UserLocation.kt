package com.aivicevic.randomusers.domain.model.user

import androidx.room.Embedded
import com.squareup.moshi.Json

/**
 * Class which provides a model for UserName
 * @constructor Sets all properties of the UserName
 * @property street the street address of user address
 * @property city the city of user address
 * @property state the state of user address
 * @property postcode the postcode of user address
 */
data class UserLocation(
    @Json(name = "street") @Embedded val street: LocationStreet,
    @Json(name = "city") val city: String,
    @Json(name = "state") val state: String,
    @Json(name = "postcode") val postcode: String
)

data class LocationStreet(
    @Json(name = "number") val number: Int,
    @Json(name = "name") val streetName: String
)
