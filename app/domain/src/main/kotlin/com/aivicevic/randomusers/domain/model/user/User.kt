package com.aivicevic.randomusers.domain.model.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Class which provides a model for api response as well as an entity object for local db
 * @constructor Sets all properties of the user
 * @property id the unique identifier of the user object
 * @property name the nested name object of the user object
 * @property location the nested location object of the user object
 * @property email the email of the user
 * @property dob the nested dob object of the user
 * @property phone the home phone number of the user
 * @property cell the cell phone number of the user
 * @property isFavorite flag to determine if use is a favorite
 */
// TODO("Determine how to parcelize")
@Entity(tableName = "favorites")
data class User(
    @Json(name = "id") @Embedded @field:PrimaryKey val id: UserId,
    @Json(name = "name") @Embedded val name: UserName,
    @Json(name = "location") @Embedded val location: UserLocation,
    @Json(name = "email") val email: String,
    @Json(name = "dob") @Embedded val dob: UserDob,
    @Json(name = "phone") val phone: String,
    @Json(name = "cell") val cell: String,
    @Json(name = "picture") @Embedded val picture: UserPicture,
    @Json(name = "isFavorite") var isFavorite: Boolean = false
)
