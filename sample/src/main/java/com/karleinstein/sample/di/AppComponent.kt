package com.karleinstein.sample.di

import android.app.Application
import com.karleinstein.basemvvm.di.BaseAppComponents
import com.karleinstein.basemvvm.di.ViewModelBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelBuilder::class,
        ActivityModules::class]
)
interface AppComponent : BaseAppComponents {

    @Component.Builder
    interface Builder : BaseAppComponents.BaseBuilder {

        @BindsInstance
        override fun application(application: Application): BaseAppComponents.BaseBuilder
    }
}
