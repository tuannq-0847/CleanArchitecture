package com.karleinstein.basemvvm.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

abstract class BaseActivity : AppCompatActivity(), BaseView {

    override val viewModel: BaseViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        setUpView()
        bindView()
        if (viewModel == null) Log.d(
            "BaseActivity :",
            "${this::class.simpleName} viewModel is null"
        )
        else {
            viewModel!!.loadingEvent.observe(this, Observer {
                if (isLoadingInActivity)
                    onHandleShowLoading(it)
            })
            viewModel!!.errorEvent.observe(this, Observer {
                if (isHandleErrorInActivity)
                    onHandleError(it)
            })
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
    }

    override val isHandleErrorInActivity: Boolean = true

    override val isLoadingInActivity: Boolean = true

    open fun onHandleShowLoading(isShowLoading: Boolean) {
        Log.d("loadingEvent", "loadingEvent: ${this::class.simpleName} $isShowLoading")
    }

    open fun onHandleError(throwable: Throwable) {
        Log.d("errorEvent", "errorEvent Base Activity: ${this::class.simpleName} $throwable")
    }

    abstract fun setUpView()

    abstract fun bindView()

    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
    }

    companion object {

        const val DEFAULT_LAYOUT_RES = 1
    }
}
