package com.krause.data.di

import android.content.Context
import com.krause.data.utils.Connectivity
import com.krause.data.utils.ConnectivityImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped

//@Module
//@InstallIn(ActivityComponent::class)
//object NetworkModule {
//
//    @Provides
//    @ActivityScoped
//    fun provideConnectivity(@ApplicationContext appContext: Context?): Connectivity =
//        ConnectivityImpl(appContext)
//}
