package com.karleinstein.basemvvm.base

import androidx.viewbinding.ViewBinding

interface BaseView {

    val isLoadingInActivity: Boolean

    val isHandleErrorInActivity: Boolean

    val viewModel: BaseViewModel?

    val viewBinding: ViewBinding
}

