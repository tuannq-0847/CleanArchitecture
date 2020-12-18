package com.karleinstein.sample.ui.expandlist

import com.karleinstein.basemvvm.base.*
import com.karleinstein.basemvvm.utils.viewBinding
import com.karleinstein.sample.R
import com.karleinstein.sample.databinding.ExpandableListFragmentBinding
import com.karleinstein.sample.model.ExpandableDataSample

class ExpandableListFragment : BaseFragment(R.layout.expandable_list_fragment) {

    override fun bindView() {

    }

    override fun setUpView() {
        val adapter = ExpandableListAdapter()
        val expandableData =
            listOf(
                ExpandableData(
                    groupItem = GroupItem("group 1"),
                    childItems = ChildItems(
                        ExpandableDataSample(
                            "child 1",
                            R.drawable.ic_launcher_background
                        )
                    )
                ),
                ExpandableData(
                    groupItem = GroupItem("group 2"),
                    childItems = ChildItems(
                        ExpandableDataSample(
                            "child 2",
                            R.drawable.ic_launcher_background
                        )
                    )
                ),
                ExpandableData(
                    groupItem = GroupItem("group 3"),
                    childItems = ChildItems(
                        ExpandableDataSample(
                            "child 3",
                            R.drawable.ic_launcher_background
                        )
                    )
                )
            )
        adapter.submitList(expandableData.convertToFlatList())
        viewBinding.recycleExpandable.adapter = adapter
    }

    override val viewBinding by viewBinding(ExpandableListFragmentBinding::bind)

    override val viewModel: BaseViewModel? = null
}