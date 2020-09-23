package com.karleinstein.basemvvm

import android.app.Application

interface BaseAppComponents {

    interface BaseBuilder {

        fun application(application: Application): BaseBuilder

        fun build(): BaseAppComponents
    }

    fun inject(application: BaseApplication)
}
