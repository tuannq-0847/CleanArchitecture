package com.karleinstein.sample.ui.expandlist

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.karleinstein.basemvvm.ExpandableType
import com.karleinstein.basemvvm.base.BaseExpandRecyclerAdapter
import com.karleinstein.basemvvm.base.ChildItem
import com.karleinstein.basemvvm.base.ExpandableItem
import com.karleinstein.basemvvm.base.GroupItem
import com.karleinstein.sample.R
import com.karleinstein.sample.model.ExpandableDataSample

class ExpandableListAdapter() :
    BaseExpandRecyclerAdapter() {

    override fun onBindGroup(itemView: View, item: GroupItem<*>) {
        val tvTest = itemView.findViewById<TextView>(R.id.tv_test)
        tvTest.text = item.input.toString()
    }

    override fun onBindChild(itemView: View, item: ChildItem<*>) {
        if (item.input is ExpandableDataSample) {
            itemView.visibility = if (item.isExpand) View.VISIBLE else View.GONE
            val textExpandable = itemView.findViewById<TextView>(R.id.text_expandable)
            val imageExpandable = itemView.findViewById<ImageView>(R.id.image_expandable)
            textExpandable.text = (item.input as ExpandableDataSample).title
            imageExpandable.setImageResource((item.input as ExpandableDataSample).imageSource)
        }
    }

    override fun calculateLayoutViewType(position: Int): Int {
        Log.d("TAG", "calculateLayoutViewType: ${currentList[position]}")
        return when (currentList[position]) {
            is ChildItem<*> -> {
                R.layout.item_expandable
            }
            else -> {
                R.layout.item_test
            }
        }
    }
}
