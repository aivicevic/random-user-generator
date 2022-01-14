package com.aivicevic.randomusers.di

import android.content.Context
import androidx.room.Room
import com.aivicevic.randomusers.data.local.db.RandomUsersDatabase
import com.aivicevic.randomusers.data.remote.ApiConstants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Provides [RandomUsersDatabase] instance used to store local data
 *
 * @return [RandomUsersDatabase] instance
 */
internal fun provideAppDatabase(context: Context): RandomUsersDatabase {
    return Room.databaseBuilder(
        context,
        RandomUsersDatabase::class.java,
        "RandomUsers.db"
    ).build()
}

/**
 * Provides [Retrofit] instance used to create services to interact with network
 *
 * @return [Retrofit] instance
 */
internal fun provideRetrofit(): Retrofit {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    return Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .client(provideOkHttpClient())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}

/**
 * Provides [Retrofit] service of specified type
 *
 * @return [T] retrofit service instance
 */
internal inline fun <reified T> provideRetrofitService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}

/**
 * Provides [OkHttpClient] instance used as the underlying client in Retrofit adapter
 *
 * @return [OkHttpClient] instance
 */
private fun provideOkHttpClient(): OkHttpClient {
    val connectTimeoutMinutes = 1L
    val readTimeoutSeconds = 60L
    val writeTimeoutSeconds = 60L

    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(connectTimeoutMinutes, TimeUnit.MINUTES)
        .readTimeout(readTimeoutSeconds, TimeUnit.SECONDS)
        .writeTimeout(writeTimeoutSeconds, TimeUnit.SECONDS)
        .build()
}
