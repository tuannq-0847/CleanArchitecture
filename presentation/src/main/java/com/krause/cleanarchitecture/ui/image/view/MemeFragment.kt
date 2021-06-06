package com.krause.cleanarchitecture.ui.image.view

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.karleinstein.basemvvm.base.BaseFragment
import com.karleinstein.basemvvm.utils.viewBinding
import com.krause.cleanarchitecture.R
import com.krause.cleanarchitecture.databinding.FragmentMemeBinding
import com.krause.cleanarchitecture.ui.animscroll.StateAppBarShrink
import com.krause.cleanarchitecture.ui.image.adapter.MemeAdapter
import com.krause.cleanarchitecture.ui.image.viewmodel.MemeViewModel
import com.krause.cleanarchitecture.ui.imageviewer.ImageViewerFragmentArgs
import com.krause.cleanarchitecture.ui.main.MainViewModel
import com.krause.cleanarchitecture.utils.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class MemeFragment : BaseFragment(R.layout.fragment_meme), AppBarLayout.OnOffsetChangedListener {
    override val viewBinding: FragmentMemeBinding by viewBinding(FragmentMemeBinding::bind)
    private val args: ImageViewerFragmentArgs by navArgs()
    private lateinit var space: SpacesItemDecoration

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val memeViewModel by viewModels<MemeViewModel>()
    private var maxVerticalOffset = 0

    override val isLoadingInActivity: Boolean
        get() = false

    override val isHandleErrorInActivity: Boolean
        get() = false

    override fun onHandleShowLoading(isShowLoading: Boolean) {
        super.onHandleShowLoading(isShowLoading)
        Log.d("HandleShowLoading", "onHandleShowLoading: isShowLoading: $isShowLoading ")
        viewBinding.progressBar.visibility = if (isShowLoading) View.VISIBLE else View.GONE
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun bindView() {
        val adapter = MemeAdapter { meme, view ->
            val extras = FragmentNavigatorExtras(
                view to meme.url
            )
            val action = MemeFragmentDirections.navToImageViewerFragment(meme.url)
            memeViewModel.previousAlpha = viewBinding.recyclerMeme.background.alpha
            findNavController().navigate(action, extras)
        }
        val layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
        viewBinding.recyclerMeme.run {
            setBackgroundResource(R.drawable.bg_shape_white)
            background.alpha = memeViewModel.previousAlpha
            if (memeViewModel.previousAlpha != 0) {
                resetCornerRadius()
            }
            this.layoutManager = layoutManager
            space = SpacesItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.spacing
                )
            )
            addItemDecoration(space)
//            adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            this.adapter = adapter
            lifecycleScope.launch {
                mainViewModel.memeResponse.observe(this@MemeFragment, {
                    adapter.submitList(it)
                })
            }
        }
    }

    override fun setUpView() {
        viewBinding.textDiscover.post {
            maxVerticalOffset = viewBinding.textDiscover.height
        }
        viewBinding.appBarMeme.addOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        Log.d(
            "OFFSETCHANGED",
            "verticalOffset: ${abs(verticalOffset)}"
        )
        viewBinding.recyclerMeme.stateAppBarShrink.value = when {
            abs(verticalOffset) >= appBarLayout.totalScrollRange / 2 -> {
                Log.d(
                    "OFFSETCHANGED",
                    "verticalOffset: COLLAPSED ${abs(verticalOffset)}"
                )
                StateAppBarShrink.COLLAPSED
            }
            else -> {
                Log.d(
                    "OFFSETCHANGED",
                    "verticalOffset: EXPANDED ${abs(verticalOffset)}"
                )
                StateAppBarShrink.EXPANDED
            }
        }
    }
}
