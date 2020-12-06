package com.karleinstein.basemvvm.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasAndroidInjector{

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        setUpView()
        bindView()
    }

    override fun onAttachFragment(fragment: Fragment) {
        Log.d("TAG", "onAttachFragment: ${fragment.javaClass.simpleName}")
        if (fragment is BaseFragment<*>){
            fragment.viewModel.loadingEvent.observe(this, Observer {
                onHandleShowLoading(it)
            })
            fragment.viewModel.errorEvent.observe(this, Observer {
                onHandleError(it)
            })
        }
    }

    abstract fun onHandleShowLoading(isShowLoading: Boolean)

    abstract fun setUpView()

    abstract fun bindView()

    abstract fun onHandleError(throwable: Throwable)

    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
    }
}
