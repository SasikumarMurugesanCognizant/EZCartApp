package com.cts.ezcartapp.di

import com.cts.ezcartapp.EZApplication
import com.cts.ezcartapp.di.component.DaggerAppComponent

object AppInjector {
    fun init(app:EZApplication){
        DaggerAppComponent.builder().application(app).build().inject(app)
    }
}