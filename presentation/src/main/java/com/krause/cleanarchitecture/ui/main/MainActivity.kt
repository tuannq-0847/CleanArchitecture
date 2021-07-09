package com.krause.cleanarchitecture.ui.main

import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.karleinstein.basemvvm.base.BaseActivity
import com.karleinstein.basemvvm.utils.viewBinding
import com.krause.cleanarchitecture.R
import com.krause.cleanarchitecture.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.sentry.Sentry

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override val viewBinding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override val viewModel: MainViewModel by viewModels()

    override fun bindView() {
        viewModel.getMemes()
    }

    override fun setUpView() {
//        Sentry.captureMessage("zzzz")
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
    }
}
