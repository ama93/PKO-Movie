package com.pkomovie

import android.app.Application
import coil.Coil
import coil.ImageLoader
import com.ramcosta.composedestinations.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        Coil.setImageLoader(ImageLoader.Builder(this).build())
    }
}
