package com.cts.ezcartapp.di.module

import android.app.Application
import com.cts.ezcartapp.data.database.AppDatabase
import com.cts.ezcartapp.data.database.EZDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RoomModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(applicationContext: Application): AppDatabase {
        return AppDatabase.invoke(applicationContext)
    }

    @Provides
    @Singleton
    fun provideEZCartDAO(appDatabase: AppDatabase): EZDao {
        return appDatabase.ezDAO()
    }
}