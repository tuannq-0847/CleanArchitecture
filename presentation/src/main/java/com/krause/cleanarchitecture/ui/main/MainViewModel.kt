package com.krause.cleanarchitecture.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.karleinstein.basemvvm.base.BaseViewModel
import com.krause.data.repository.MemePagingSource
import com.krause.domain.model.Meme
import com.krause.domain.repository.MemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val memeRepository: MemeRepository,
    private val memePagingSource: MemePagingSource
) : BaseViewModel() {

    var memeResponse: MutableLiveData<List<Meme>> = MutableLiveData()

    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 5)
    ) {
        memePagingSource
    }.flow
        .cachedIn(viewModelScope)

//    fun getMemes() {
//        viewModelScope.launch {
//            val result = memeRepository.getMemes()
//            if (result.isSuccess) {
//                memeResponse.value = result.getOrNull()
//            }
//            if (result.isFailure) {
//                errorEvent.value = result.exceptionOrNull()
//            }
//        }
//    }
}
