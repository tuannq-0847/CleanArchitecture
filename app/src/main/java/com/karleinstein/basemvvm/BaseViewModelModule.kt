package com.karleinstein.basemvvm

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class BaseViewModelModule

@Module
abstract class ViewModelBuilder {
    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: ViewModelFactory
    ): ViewModelProvider.Factory
}
