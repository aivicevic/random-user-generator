package com.domain.repository

import androidx.lifecycle.LiveData
import com.domain.model.user.User
import com.domain.model.user.UsersResponse
import io.reactivex.Single

interface UserRepository {

    fun getUsers(results: Int): Single<UsersResponse>

    fun getFavoriteFromDb(user: User): LiveData<User?>

    fun getFavoritesFromDb(): LiveData<List<User>>

    fun saveFavoriteToDb(user: User)

    fun deleteFavoriteFromDb(user: User)

    fun deleteAllFavoritesFromDb()
}
