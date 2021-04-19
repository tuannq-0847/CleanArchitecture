package com.karleinstein.basemvvm.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.navOptions
import com.karleinstein.basemvvm.TransferArgument
import com.karleinstein.basemvvm.data.transfer.DataTransfer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId), BaseView {

    override val viewModel: BaseViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel == null) Log.d("BaseFragment:", "${this::class.simpleName} viewModel is null")
        else {
            viewModel!!.loadingEvent.observe(viewLifecycleOwner, {
                if (!isLoadingInActivity)
                    onHandleShowLoading(it)
            })
            viewModel!!.errorEvent.observe(viewLifecycleOwner, {
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

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStore.clear()
    }

    abstract fun bindView()

    abstract fun setUpView()

    companion object {
        fun <T : BaseFragment> newInstance(entity: T, vararg dataTransfer: DataTransfer) =
            entity.apply {
                dataTransfer.forEach {
                    TransferArgument.setArgument(
                        it.key,
                        it.data
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
