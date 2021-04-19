package com.karleinstein.sample.ui.expandlist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.karleinstein.basemvvm.base.BaseExpandRecyclerAdapter
import com.karleinstein.basemvvm.base.ChildItem
import com.karleinstein.sample.R
import com.karleinstein.sample.model.ExpandableDataSample

class ExpandableListAdapter :
    BaseExpandRecyclerAdapter<String,ExpandableDataSample>() {

    override fun buildLayoutRes(position: Int): Int {
        return when (currentList[position]) {
            is ChildItem<*> -> {
                R.layout.item_expandable
            }
            else -> {
                R.layout.item_test
            }
        }
    }

    override fun onBindGroup(itemView: View, item: String?) {
        val tvTest = itemView.findViewById<TextView>(R.id.tv_test)
        tvTest.text = item
    }

    override fun onBindChild(itemView: View, item: ExpandableDataSample?) {
        itemView.visibility = if (isExpanded) View.VISIBLE else View.GONE
        val textExpandable = itemView.findViewById<TextView>(R.id.text_expandable)
        val imageExpandable = itemView.findViewById<ImageView>(R.id.image_expandable)
        item?.let {
            textExpandable.text = it.title
            imageExpandable.setImageResource(
                it.imageSource
            )
        }
    }
}
