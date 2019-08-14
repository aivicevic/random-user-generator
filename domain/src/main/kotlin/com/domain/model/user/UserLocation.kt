package com.domain.model.user

/**
 * Class which provides a model for UserName
 * @constructor Sets all properties of the UserName
 * @property street the street address of user address
 * @property city the city of user address
 * @property state the state of user address
 * @property postcode the postcode of user address
 */
data class UserLocation(
    val street: String,
    val city: String,
    val state: String,
    val postcode: String
)