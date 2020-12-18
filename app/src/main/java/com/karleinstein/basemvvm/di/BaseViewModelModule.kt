package com.karleinstein.basemvvm.di

import androidx.lifecycle.ViewModelProvider
import com.karleinstein.basemvvm.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelBuilder {
    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: ViewModelFactory
    ): ViewModelProvider.Factory
}
