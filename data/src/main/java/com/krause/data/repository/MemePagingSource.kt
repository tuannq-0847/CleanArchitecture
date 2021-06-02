package com.krause.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.krause.data.networking.MemeApi
import com.krause.domain.model.Meme
import javax.inject.Inject

class MemePagingSource @Inject constructor(
    private val memeApi: MemeApi
) : PagingSource<Int, Meme>() {
    var page = 1
    override fun getRefreshKey(state: PagingState<Int, Meme>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Meme> {
        page++
        // Start refresh at page 1 if undefined.
        val nextPageNumber = params.key?.inc()?:1
        val response = memeApi.getMemes()
        return LoadResult.Page(
            data = response,
            prevKey = null, // Only paging forward.
            nextKey = page
        )
    }
}
