package com.mtek

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.provider.Settings
import com.mtek.goarenopoc.data.di.apiModule
import com.mtek.goarenopoc.data.di.retrofitModule
import com.mtek.goarenopoc.data.di.viewBindingModule
import com.mtek.goarenopoc.data.di.viewModelModule
import com.mtek.goarenopoc.utils.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApp : Application(){

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Constants.DEVICES_VALUE = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        startKoin {
            androidLogger()
            androidContext(this@BaseApp)
            modules(listOf(retrofitModule, apiModule, viewModelModule, viewBindingModule))

        }
    }

    companion object {
        lateinit var appContext: Context;
    }
}