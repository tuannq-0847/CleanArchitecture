package com.karleinstein.basemvvm.base.headerfooter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.karleinstein.basemvvm.base.BaseRecyclerAdapter
import com.karleinstein.basemvvm.base.BaseViewHolder
import com.karleinstein.basemvvm.middleware.Loading
import com.karleinstein.basemvvm.middleware.StateWrapper
import com.karleinstein.basemvvm.middleware.Success
import java.lang.Error

abstract class StateAdapter<Data : StateWrapper>(
    callBack: DiffUtil.ItemCallback<Data>,
    onClickItem: (item: Data) -> Unit = {}
) : BaseRecyclerAdapter<Data>(callBack, onClickItem) {

    override fun onBind(holder: BaseViewHolder, item: Data, position: Int) {
        if (item is Loading) onLoadingListener(true)
        else onLoadingListener(false)
        if (item is Error) onErrorListener(item)
    }

    abstract fun onLoadingListener(isLoading: Boolean)

    abstract fun onErrorListener(throwable: Throwable)

    override fun getItemViewType(position: Int): Int {
        return when {
            currentList[position] is Loading -> LOADING
            currentList[position] is Success<*> -> OTHER
            else -> ERROR
        }
    }
}

const val LOADING = 123
const val ERROR = 321
const val OTHER = -1
