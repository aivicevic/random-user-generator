package com.aivicevic.randomusers

import com.aivicevic.randomusers.domain.model.user.LocationStreet
import com.aivicevic.randomusers.domain.model.user.User
import com.aivicevic.randomusers.domain.model.user.UserDob
import com.aivicevic.randomusers.domain.model.user.UserId
import com.aivicevic.randomusers.domain.model.user.UserLocation
import com.aivicevic.randomusers.domain.model.user.UserName
import com.aivicevic.randomusers.domain.model.user.UserPicture

fun getFakeUser(isFavorite: Boolean = false): User {
    return User(
        id = UserId("fake", "0"),
        name = UserName("Mr.", "Test", "McTesterson"),
        location = UserLocation(LocationStreet(123, "Test Rd"), "City of Test", "TS", "50842"),
        email = "tester@mailinator.com",
        dob = UserDob("10/08/1994", "24"),
        phone = "928-542-8520",
        cell = "928-540-5854",
        picture = UserPicture("NA", "NA", "NA"),
        isFavorite = isFavorite
    )
}
