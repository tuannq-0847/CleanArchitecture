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

    fun getMemes() {
        viewModelScope.launch {
            loadingEvent.value = true
            val result = memeRepository.getMemes()
            if (result.isSuccess) {
                loadingEvent.value = false
                memeResponse.value = result.getOrNull()
            }
            if (result.isFailure) {
                loadingEvent.value = false
                errorEvent.value = result.exceptionOrNull()
            }
        }
    }
}
