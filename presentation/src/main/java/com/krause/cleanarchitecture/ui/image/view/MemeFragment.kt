package com.krause.cleanarchitecture.ui.image.view

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.karleinstein.basemvvm.base.BaseFragment
import com.karleinstein.basemvvm.utils.viewBinding
import com.krause.cleanarchitecture.R
import com.krause.cleanarchitecture.databinding.FragmentMemeBinding
import com.krause.cleanarchitecture.ui.image.adapter.ExampleLoadStateAdapter
import com.krause.cleanarchitecture.ui.image.adapter.MemeAdapter
import com.krause.cleanarchitecture.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MemeFragment : BaseFragment(R.layout.fragment_meme) {
    override val viewBinding: FragmentMemeBinding by viewBinding(FragmentMemeBinding::bind)

    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun bindView() {
        val adapter = MemeAdapter()
        adapter.withLoadStateHeaderAndFooter(
            header = ExampleLoadStateAdapter(adapter::retry),
            footer = ExampleLoadStateAdapter(adapter::retry)
        )
        viewBinding.recyclerMeme.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
        viewBinding.recyclerMeme.adapter = adapter
        lifecycleScope.launch {
            mainViewModel.flow.collectLatest {
                Log.d("collectLatest", "bindView: $it")
                adapter.submitData(it)
            }
        }
    }

    override fun setUpView() {

    }
}
