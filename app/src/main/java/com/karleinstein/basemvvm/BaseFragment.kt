package com.karleinstein.basemvvm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.navOptions
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        bindView()
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
                    TransferArgument.setArgument(it.first, it.second)
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

    abstract fun callBackOnBackPressed()

    fun assignOnBackPressed(lifecycleOwner: LifecycleOwner) =
        requireActivity().onBackPressedDispatcher.addCallback(
            lifecycleOwner,
            handleCallBackOnBackPressed
        )
}