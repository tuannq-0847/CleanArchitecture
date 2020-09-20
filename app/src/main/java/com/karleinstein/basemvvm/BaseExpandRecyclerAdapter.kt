package com.karleinstein.basemvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil

abstract class BaseExpandRecyclerAdapter(
    callBack: DiffUtil.ItemCallback<ExpandableItem>
) : BaseRecyclerAdapter<ExpandableItem>(callBack) {

    private var isExpand = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == ExpandableType.GROUP) {
            GroupViewHolder(
                LayoutInflater.from(parent.context).inflate(getLayoutRes(viewType), parent, false)
            ).apply { bindGroupFirstTime(this) }
        } else ChildViewHolder(
            LayoutInflater.from(parent.context).inflate(getLayoutRes(viewType), parent, false)
        ).apply { bindChildFirstTime(this) }
    }

    protected open fun bindGroupFirstTime(baseViewHolder: BaseViewHolder) {
        baseViewHolder.itemView.setOnClickListener {
            isExpand = !isExpand
            submitList(currentList.setStateChildView(isExpand, baseViewHolder.adapterPosition))
        }
    }

    protected open fun bindChildFirstTime(baseViewHolder: BaseViewHolder) {
    }

    override fun onBind(itemView: View, item: ExpandableItem, position: Int) {
        if (item is GroupItem<*>) {
            onBindGroup(itemView, item)
        } else onBindChild(itemView, item as ChildItem<*>)
    }

    override fun getItemViewType(position: Int): Int {
        val data = getItem(position)
        return if (data is GroupItem<*>) ExpandableType.GROUP
        else ExpandableType.CHILD
    }

    abstract fun onBindGroup(itemView: View, item: GroupItem<*>)

    abstract fun onBindChild(itemView: View, item: ChildItem<*>)

    inner class ChildViewHolder(itemView: View) : BaseViewHolder(itemView)

    class GroupViewHolder(itemView: View) : BaseViewHolder(itemView)
}
