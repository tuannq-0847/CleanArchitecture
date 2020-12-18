package com.karleinstein.basemvvm.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.karleinstein.basemvvm.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasAndroidInjector, BaseView {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override val viewModel: BaseViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
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
