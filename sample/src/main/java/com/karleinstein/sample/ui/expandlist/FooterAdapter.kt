package com.karleinstein.sample.ui.expandlist

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.karleinstein.basemvvm.base.headerfooter.LOADING
import com.karleinstein.basemvvm.base.headerfooter.StateAdapter
import com.karleinstein.basemvvm.middleware.StateWrapper
import com.karleinstein.sample.R

class FooterAdapter<Data : StateWrapper>(
    callBack: DiffUtil.ItemCallback<Data>,
    onClickItem: (item: Data) -> Unit = {}
) : StateAdapter<Data>(callBack, onClickItem) {
    override fun onLoadingListener(isLoading: Boolean) {
        Log.d("FooterAdapter", "onLoadingListener: $isLoading")
    }

    override fun onErrorListener(throwable: Throwable) {
        Log.d("FooterAdapter", "onErrorListener: $throwable")
    }

    override fun calculateLayoutViewType(position: Int): Int {
        return R.layout.loading_layout
    }
}
