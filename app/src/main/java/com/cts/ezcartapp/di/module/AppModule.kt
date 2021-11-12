package com.cts.ezcartapp.di.module

import android.app.Application
import android.content.Context
import com.cts.ezcartapp.data.database.EZDao
import com.cts.ezcartapp.data.api.EZCartApi
import com.cts.ezcartapp.data.repository.RemoteDataSourceRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, RoomModule::class, SharedPreferenceModule::class])
object AppModule {

    @Provides
    @Singleton
    fun provideAppContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: EZCartApi, localDAO:EZDao):RemoteDataSourceRepository{
        return RemoteDataSourceRepository(apiService,localDAO)
    }
}