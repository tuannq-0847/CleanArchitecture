package com.karleinstein.sample.di

import com.karleinstein.sample.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModules {

    @ContributesAndroidInjector(modules = [ViewModelModule::class, FragmentModules::class])
    abstract fun contributeMainActivity(): MainActivity
}
