package com.krause.cleanarchitecture

import com.karleinstein.basemvvm.base.BaseActivity
import com.karleinstein.basemvvm.utils.viewBinding
import com.krause.cleanarchitecture.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    override val viewBinding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun bindView() {

    }

    override fun setUpView() {

    }
}
