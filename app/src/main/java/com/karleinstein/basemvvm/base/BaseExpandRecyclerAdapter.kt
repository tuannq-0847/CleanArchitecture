package com.karleinstein.basemvvm.base

import android.view.View
import androidx.recyclerview.widget.DiffUtil

abstract class BaseExpandRecyclerAdapter() : BaseRecyclerAdapter<ExpandableItem>() {

    override fun bindFirstTime(baseViewHolder: BaseViewHolder) {
        baseViewHolder.itemView.setOnClickListener {
            getChildItemClicked(baseViewHolder)?.run {
                submitList(
                    currentList.setStateChildView(
                        stateClickedHandler(
                            !isExpand,
                            baseViewHolder.absoluteAdapterPosition
                        ),
                        baseViewHolder.absoluteAdapterPosition
                    )
                )
            }
        }
    }

    override fun onBind(holder: BaseViewHolder, item: ExpandableItem, position: Int) {
        if (item is GroupItem<*>) {
            onBindGroup(holder.itemView, item)
        } else onBindChild(holder.itemView, item as ChildItem<*>)
    }

    abstract fun onBindGroup(itemView: View, item: GroupItem<*>)

    abstract fun onBindChild(itemView: View, item: ChildItem<*>)

    private fun getChildItemClicked(baseViewHolder: BaseViewHolder): ChildItem<*>? {
        if (baseViewHolder.absoluteAdapterPosition <= -1) return null
        val currentItem = currentList[baseViewHolder.absoluteAdapterPosition]
        if (baseViewHolder.absoluteAdapterPosition + 1 >= currentList.size) return null
        val nextItem = currentList[baseViewHolder.absoluteAdapterPosition + 1]
        return if (currentItem is GroupItem<*> && nextItem is ChildItem<*>)
            nextItem
        else null
    }
}
