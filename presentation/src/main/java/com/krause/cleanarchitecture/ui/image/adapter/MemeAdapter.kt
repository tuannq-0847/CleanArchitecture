package com.krause.cleanarchitecture.ui.image.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.karleinstein.basemvvm.base.BaseDiffUtil
import com.karleinstein.basemvvm.base.BaseViewHolder
import com.krause.cleanarchitecture.R
import com.krause.cleanarchitecture.databinding.LoadStateItemBinding
import com.krause.domain.model.Meme
import java.util.*

class MemeAdapter :
    PagingDataAdapter<Meme, BaseViewHolder>(BaseDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_meme, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        holder.itemView.run {
            val imageMeme = findViewById<ImageView>(R.id.image_meme)
            //call this to clear previous requests
            Glide.with(context).load(item?.url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageMeme)
//            imageMeme.load(item?.url)
        }
    }
}

class ExampleLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}

class LoadStateViewHolder(
    parent: ViewGroup,
    retry: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.load_state_item, parent, false)
) {
    private val binding = LoadStateItemBinding.bind(itemView)
    private val progressBar: ProgressBar = binding.progressBar
//    private val errorMsg: TextView = binding.errorMsg
//    private val retry: Button = binding.retryButton
        .also {
            it.setOnClickListener { retry() }
        }

    fun bind(loadState: LoadState) {
//        if (loadState is LoadState.Error) {
//            errorMsg.text = loadState.error.localizedMessage
//        }

        progressBar.isVisible = loadState is LoadState.Loading
//        retry.isVisible = loadState is LoadState.Error
//        errorMsg.isVisible = loadState is LoadState.Error
    }
}
