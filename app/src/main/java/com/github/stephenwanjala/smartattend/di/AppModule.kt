package com.github.stephenwanjala.smartattend.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.github.stephenwanjala.smartattend.auth.data.network.AuthApi
import com.github.stephenwanjala.smartattend.auth.data.repositoryImpl.AuthRepositoryImpl
import com.github.stephenwanjala.smartattend.auth.login.domain.repository.AuthRepository
import com.github.stephenwanjala.smartattend.preferences.data.SmartAttendPreferencesImpl
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences
import com.github.stephenwanjala.smartattend.preferences.domain.SmartAttendPreferences.Companion.SMART_ATTENT_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASEURL = "https://smartattendweb.onrender.com"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASEURL)
        .client(okHttpClient)
        .build()


    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)


    @Singleton
    @Provides
    fun provideDataPreferences(app: Application): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                app.preferencesDataStoreFile(SMART_ATTENT_PREFERENCES_NAME)
            }
        )

    @Singleton
    @Provides
    fun provideSmartAttendPreferences(dataStore: DataStore<Preferences>): SmartAttendPreferences =
        SmartAttendPreferencesImpl(dataStore = dataStore)

    @Singleton
    @Provides
    fun provideAuthRepository(
        authApi: AuthApi,
        preferences: SmartAttendPreferences
    ): AuthRepository = AuthRepositoryImpl(authApi = authApi, preferences = preferences)


}