package com.ericjoseph.pokedex

import android.app.Application
import android.net.Uri
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


@HiltAndroidApp
class BaseApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }

}