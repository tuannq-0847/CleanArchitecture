package com.krause.cleanarchitecture.ui.imageviewer

import android.Manifest
import android.os.Bundle
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.karleinstein.basemvvm.base.BaseFragment
import com.karleinstein.basemvvm.extension.showToast
import com.karleinstein.basemvvm.utils.viewBinding
import com.karleinstein.fastpermissions.FastPermission
import com.krause.cleanarchitecture.R
import com.krause.cleanarchitecture.databinding.FragmentImageViewerBinding
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
            FastPermission.check(
                requireActivity(),
                mutableListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                object : FastPermission.PermissionsListener {
                    override fun onGranted() {
                        viewModel.saveImage(viewBinding.imageMeme.drawable.toBitmap())
                    }

                    override fun onPermissionDeniedForever(deniedPermissionsForever: List<String>) {
                        context?.showToast("You must enable permission write external storage to use this feature")
                    }

                    override fun onPermissionDenied(deniedPermissions: List<String>) {
                        FastPermission.showDialogExplain(
                            requireActivity(),
                            "Warning!!!",
                            "You must enable permission write external storage to use this feature",
                            object : FastPermission.DialogExplainListener {
                                override fun onAllowClicked() {

                                }
                            }
                        )
                    }
                })
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
//        assignOnBackPressed(this)
    }

//    override fun callBackOnBackPressed() {
//        super.callBackOnBackPressed()
//    }

    override val viewBinding: FragmentImageViewerBinding by viewBinding(FragmentImageViewerBinding::bind)
}
