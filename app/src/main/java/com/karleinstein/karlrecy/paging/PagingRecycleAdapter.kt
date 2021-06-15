package com.karleinstein.karlrecy.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.karleinstein.karlrecy.BaseDiffUtil
import com.karleinstein.karlrecy.BaseViewHolder
import com.karleinstein.karlrecy.listener.RecyclerAdapterListener

abstract class PagingRecycleAdapter<Item : Any>(
    diffCallback: DiffUtil.ItemCallback<Item> = BaseDiffUtil(),
    @LayoutRes var swipeForOptionsLayout: Int? = null
) : PagingDataAdapter<Item, BaseViewHolder>(diffCallback),
    RecyclerAdapterListener<Item> {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        getItem(position)?.run {
            onBind(holder, this, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = swipeForOptionsLayout?.let {
            LayoutInflater.from(parent.context).inflate(it, parent, false)
        }
        val frameLayout = FrameLayout(parent.context)
        frameLayout.layoutParams =
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        frameLayout.addView(view)
        frameLayout.addView(
            LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
        )
        return BaseViewHolder(
            itemView = frameLayout,
            parent = parent,
            onSwiped = onSwiped
        ).apply { bindFirstTime(this) }
    }

    open fun bindFirstTime(baseViewHolder: BaseViewHolder) {}

    override fun getItemViewType(position: Int): Int {
        return buildLayoutRes(position)
    }

    private val onSwiped = fun(pos: Int) {
        Log.d("Paging", "pos: /$pos")
    }
}
