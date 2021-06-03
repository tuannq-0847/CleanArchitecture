package com.karleinstein.sample.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.karleinstein.basemvvm.base.BaseFragment
import com.karleinstein.basemvvm.data.transfer.DataTransfer
import com.karleinstein.basemvvm.extension.replaceFragment
import com.karleinstein.basemvvm.utils.viewBinding
import com.karleinstein.sample.R
import com.karleinstein.sample.databinding.FragmentABinding
import com.karleinstein.sample.ui.expandlist.ExpandableListFragment

class AFragment : BaseFragment(R.layout.fragment_a) {
    override fun bindView() {
        viewBinding.textTest.setOnClickListener {
            parentFragmentManager.replaceFragment(
                ExpandableListFragment.newInstance(
                    dataTransfer = arrayOf(
                        DataTransfer(
                            "name",
                            "tuan"
                        )
                    )
                ), R.id.main_container,
                isAddToBackStack = true
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG", "onCreateView: ")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", "onViewCreated: ")
    }

    override fun setUpView() {

    }

    override val viewBinding: FragmentABinding
            by viewBinding(FragmentABinding::bind)
}
