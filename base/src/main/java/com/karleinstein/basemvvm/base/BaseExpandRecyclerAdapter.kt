package com.karleinstein.basemvvm.base

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

abstract class BaseExpandRecyclerAdapter<G, C>(
    callback: DiffUtil.ItemCallback<ExpandableItem> = BaseDiffUtil()
) : BaseRecyclerAdapter<ExpandableItem>(callback) {

    protected var isExpanded = false
        private set

    override fun bindFirstTime(baseViewHolder: BaseViewHolder) {
        baseViewHolder.itemView.setOnClickListener {
            getChildItemClicked(baseViewHolder)?.let { child ->
                isExpanded = !child.isExpand
                submitList(
                    currentList.setStateChildView(
                        stateClickedHandler(
                            !child.isExpand,
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
            onBindGroup(holder.itemView, item.toGroupData<G>())
        } else onBindChild(holder.itemView, (item as? ChildItem<*>)?.toChildData<C>())
    }

    abstract fun onBindGroup(itemView: View, item: G?)

    abstract fun onBindChild(itemView: View, item: C?)

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
