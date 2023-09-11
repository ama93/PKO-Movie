package com.pkomovie.di

import android.content.Context
import com.google.gson.Gson
import com.pkomovie.data.locale.DataStore
import com.pkomovie.data.remote.RemoteApi
import com.pkomovie.data.remote.interceptors.AuthorizationInterceptor
import com.pkomovie.domain.repository.Repository
import com.pkomovie.domain.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) = DataStore(context)

    @Provides
    @Singleton
    fun providesRemoteApi(@ApplicationContext context: Context): RemoteApi =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(AuthorizationInterceptor(context))
                    .addInterceptor(HttpLoggingInterceptor().setLevel(BODY))
                    .build()
            )

            .build()
            .create(RemoteApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(remoteApi: RemoteApi, dataStore: DataStore): Repository = RepositoryImpl(
        remoteApi = remoteApi,
        dataStore = dataStore
    )

}
