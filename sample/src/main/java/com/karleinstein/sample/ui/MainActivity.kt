package com.karleinstein.sample.ui

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.karleinstein.basemvvm.base.BaseActivity
import com.karleinstein.basemvvm.base.BaseViewModel
import com.karleinstein.basemvvm.extension.addFragment
import com.karleinstein.basemvvm.middleware.Loading
import com.karleinstein.basemvvm.middleware.StateWrapper
import com.karleinstein.basemvvm.middleware.Success
import com.karleinstein.basemvvm.utils.viewBinding
import com.karleinstein.sample.R
import com.karleinstein.sample.databinding.ActivityMainBinding
import com.karleinstein.sample.ui.expandlist.ExpandableListAdapter
import com.karleinstein.sample.ui.expandlist.ExpandableListFragment
import com.karleinstein.sample.ui.expandlist.FooterAdapter
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel: BaseViewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun bindView() {
        viewModel.loadingEvent.value = true
    }

    override val viewBinding by viewBinding(ActivityMainBinding::inflate)

    override fun setUpView() {
        val adapter = MainAdapter(object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return true
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return true
            }

        }, clickedEvent)
        adapter.submitList(
            listOf(
                "a",
                "b",
                "c",
                "d",
                "e"
            )
        )

//        val loadingAdapter = FooterAdapter(object:
//            DiffUtil.ItemCallback<StateWrapper>() {
//            override fun areItemsTheSame(oldItem: StateWrapper, newItem: StateWrapper): Boolean {
//                return true
//            }
//
//            override fun areContentsTheSame(oldItem: StateWrapper, newItem: StateWrapper): Boolean {
//                return false
//            }
//
//        })
//        loadingAdapter.submitList(listOf<StateWrapper>(Success("fasd")))
//        val concatAdapter = ConcatAdapter(adapter)
        viewBinding.recyclerTest.adapter = adapter
    }

    private val clickedEvent = { item: String ->
        Log.d("clickedEvent:", "$item")
        if (item == "a") {
            supportFragmentManager.addFragment(ExpandableListFragment(), R.id.main_container)
        }
    }
}