package com.karleinstein.basemvvm

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module(includes = [BaseViewModelModule::class])
class AppModule {

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext
}
