package com.karleinstein.sample.ui

import com.karleinstein.basemvvm.di.BaseApplication
import com.karleinstein.sample.di.DaggerAppComponent
import dagger.android.HasAndroidInjector

class App : BaseApplication(), HasAndroidInjector {

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build()
            .inject(this)
    }
}