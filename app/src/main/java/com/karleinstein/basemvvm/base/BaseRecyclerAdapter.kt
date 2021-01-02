package com.karleinstein.basemvvm.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

abstract class BaseRecyclerAdapter<Item : Any>(
    callBack: DiffUtil.ItemCallback<Item> = BaseDiffUtil(),
    val onClickItem: (item: Item) -> Unit = {}
) : ListAdapter<Item, BaseViewHolder>(
    AsyncDifferConfig.Builder<Item>(callBack)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {

    private val states = mutableMapOf<Item, Boolean>()

//    /**
//     * override this with new list to pass check "if (newList == mList)" in AsyncListDiffer
//     */
//    override fun submitList(list: List<Item>?) {
//        super.submitList(ArrayList<Item>(list ?: listOf()))
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
        )
            .apply {
                bindFirstTime(this)
                currentList.forEach { states[it] = false }
            }
    }

    protected open fun bindFirstTime(baseViewHolder: BaseViewHolder) {}

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)
        onBind(holder, item, position)
    }

    override fun getItemViewType(position: Int): Int {
        return calculateLayoutViewType(position)
    }

    abstract fun onBind(holder: BaseViewHolder, item: Item, position: Int)

    @LayoutRes
    abstract fun calculateLayoutViewType(position: Int): Int


    protected fun stateClickedHandler(isStateChanged: Boolean, position: Int): Boolean {
        val key = states.keys.toList()[position]
        states[key] = isStateChanged
        return isStateChanged
    }
}

class BaseDiffUtil<Item: Any> : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem === newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

}

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
