package com.randomusers.di.module

import com.data.remote.ApiConstants
import com.domain.scheduler.SchedulerProvider
import com.domain.scheduler.SchedulerProviderImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    internal fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .connectTimeout(CONNECT_TIMEOUT_IN_MINUTES.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideSchedulerProvider(): SchedulerProvider = SchedulerProviderImpl()

    companion object {

        private const val CONNECT_TIMEOUT_IN_MINUTES = 1
        private const val READ_TIMEOUT_IN_SECONDS = 60
        private const val WRITE_TIMEOUT_IN_SECONDS = 60
    }
}