package com.krause.memedownloader.ui.imageviewer

import android.os.Bundle
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.karleinstein.basemvvm.base.BaseFragment
import com.karleinstein.basemvvm.extension.showToast
import com.karleinstein.basemvvm.utils.viewBinding
import com.krause.memedownloader.R
import com.krause.memedownloader.databinding.FragmentImageViewerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageViewerFragment : BaseFragment(R.layout.fragment_image_viewer) {

    override val viewModel: ImageViewerViewModel by viewModels()

    private val args: ImageViewerFragmentArgs by navArgs()

    override fun bindView() {
        viewBinding.imageMeme.run {
            transitionName = args.urlMeme
            Glide.with(this).load(args.urlMeme).into(viewBinding.imageMeme)
        }
        Glide.with(this).load(R.drawable.icons8_download).into(viewBinding.imageDownload)
        viewBinding.imageDownload.setOnClickListener {
            viewModel.saveImage(viewBinding.imageMeme.drawable.toBitmap())
        }
        viewModel.isSaveSuccessful.observe(this, {
            context?.showToast("Save image successfully")
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun setUpView() {

    }

    override val viewBinding: FragmentImageViewerBinding by viewBinding(FragmentImageViewerBinding::bind)
}
