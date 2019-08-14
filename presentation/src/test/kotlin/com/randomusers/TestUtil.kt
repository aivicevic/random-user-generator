package com.randomusers

import com.domain.model.user.*
import com.domain.scheduler.SchedulerProvider

fun triggerScheduledActions(schedulerProvider: SchedulerProvider) {
    (schedulerProvider as? TestSchedulerProviderImpl)?.triggerActions()
}

fun getFakeUser(isFavorite: Boolean = false): User {
    return User(
        id = UserId("fake", "0"),
        name = UserName("Mr.", "Test", "McTesterson"),
        location = UserLocation("123 Test Dr.", "City of Test", "TS", "50842"),
        email = "tester@mailinator.com",
        dob = UserDob("10/08/1994", "24"),
        phone = "928-542-8520",
        cell = "928-540-5854",
        picture = UserPicture("NA", "NA", "NA"),
        isFavorite = isFavorite
    )
}