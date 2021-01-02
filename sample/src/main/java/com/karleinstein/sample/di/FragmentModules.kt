package com.karleinstein.sample.di

import com.karleinstein.sample.ui.expandlist.ExpandableListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModules{

    @ContributesAndroidInjector()
    abstract fun contributeExpandListFragment(): ExpandableListFragment
}
