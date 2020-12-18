package com.karleinstein.basemvvm.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.navOptions
import com.karleinstein.basemvvm.TransferArgument
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId), BaseView {

    override val viewModel: BaseViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel == null) Log.d("BaseFragment:", "${this::class.simpleName} viewModel is null")
        else {
            viewModel!!.loadingEvent.observe(this, Observer {
                if (!isLoadingInActivity)
                    onHandleShowLoading(it)
            })
            viewModel!!.errorEvent.observe(this, Observer {
                if (!isHandleErrorInActivity)
                    onHandleError(it)
            })
        }
        setUpView()
        bindView()
    }

    override val isHandleErrorInActivity: Boolean = true

    override val isLoadingInActivity: Boolean = true

    open fun onHandleShowLoading(isShowLoading: Boolean) {
        Log.d("loadingEvent", "loadingEvent: ${this::class.simpleName} $isShowLoading")
    }

    open fun onHandleError(throwable: Throwable) {
        Log.d("errorEvent", "errorEvent: ${this::class.simpleName} $throwable")
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStore.clear()
    }

    abstract fun bindView()

    abstract fun setUpView()

    companion object {

        inline fun <reified T : Fragment> newInstance(vararg params: Pair<String, Any>) =
            T::class.java.newInstance().apply {
                params.forEach {
                    TransferArgument.setArgument(
                        it.first,
                        it.second
                    )
                }
            }
    }

    fun navOptions(animEnter: Int, animExit: Int, animPopEnter: Int, animPopExit: Int) =
        navOptions {
            anim {
                enter = animEnter
                exit = animExit
                popEnter = animPopEnter
                popExit = animPopExit
            }
        }

    private val handleCallBackOnBackPressed: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                callBackOnBackPressed()
            }
        }

    fun callBackOnBackPressed() {

    }

    fun assignOnBackPressed(lifecycleOwner: LifecycleOwner) =
        requireActivity().onBackPressedDispatcher.addCallback(
            lifecycleOwner,
            handleCallBackOnBackPressed
        )

    fun checkStateVisible(state: Int, vararg views: View) {
        views.forEach {
            it.visibility = state
        }
    }
}
