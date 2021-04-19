package com.karleinstein.sample.ui

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.karleinstein.basemvvm.base.BaseRecyclerAdapter
import com.karleinstein.basemvvm.base.BaseViewHolder
import com.karleinstein.sample.R

class MainAdapter(
    callBack: DiffUtil.ItemCallback<String>,
    onClickItem: (item: String) -> Unit
) : BaseRecyclerAdapter<String>(callBack, onClickItem) {

    override fun onBind(holder: BaseViewHolder, item: String, position: Int) {
        val textView = holder.itemView.findViewById<TextView>(R.id.tv_test)
        textView.text = item
    }

    override fun bindFirstTime(baseViewHolder: BaseViewHolder) {
        baseViewHolder.itemView.setOnClickListener {
            onClickItem(currentList[baseViewHolder.adapterPosition])
        }
    }

    override fun buildLayoutRes(position: Int): Int {
        return R.layout.item_test
    }

}
