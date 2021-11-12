package com.cts.ezcartapp.di.module

import android.app.Application
import com.cts.ezcartapp.utils.SharedPreference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SharedPreferenceModule {
    @Provides
    @Singleton
    fun provideSharedPreference(applicationContext:Application):SharedPreference{
        return SharedPreference(applicationContext)
    }

}