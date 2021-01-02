package com.karleinstein.sample.di

import androidx.lifecycle.ViewModel
import com.karleinstein.basemvvm.ViewModelKey
import com.karleinstein.sample.ui.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}
