package com.krause.memedownloader.ui.main

import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.karleinstein.basemvvm.base.BaseActivity
import com.krause.memedownloader.R
import com.krause.memedownloader.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override val viewBinding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override val viewModel: MainViewModel by viewModels()

    override fun bindView() {
        viewModel.getMemes()
    }

    override fun setUpView() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
    }
}
