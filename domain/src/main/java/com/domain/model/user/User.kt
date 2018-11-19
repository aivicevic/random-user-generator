package com.domain.model.user

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Class which provides a model for post
 * @constructor Sets all properties of the post
 * @property id the unique identifier of the user object
 * @property name the nested name object of the user object
 * @property location the nested location object of the user object
 * @property email the email of the user
 * @property dob the nested dob object of the user
 * @property phone the home phone number of the user
 * @property cell the cell phone number of the user
 */
@Entity
data class User(
    @Embedded @field:PrimaryKey val id: UserId,
    @Embedded val name: UserName,
    @Embedded val location: UserLocation,
    val email: String,
    @Embedded val dob: UserDob,
    val phone: String,
    val cell: String,
    @Embedded val picture: UserPicture
)