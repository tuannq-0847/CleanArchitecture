package com.krause.cleanarchitecture.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.karleinstein.basemvvm.base.BaseViewModel
import com.krause.domain.model.Meme
import com.krause.domain.repository.MemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val memeRepository: MemeRepository,
//    private val memePagingSource: MemePagingSource
) : BaseViewModel() {

    var memeResponse: MutableLiveData<List<Meme>> = MutableLiveData()

    fun getMemes() {
        viewModelScope.launch {
            loadingEvent.value = true
            val result = memeRepository.getMemes()
            if (result.isSuccess) {
                Log.d("TAG", "getMemes: $result")
                loadingEvent.value = false
                memeResponse.value = result.getOrNull()
            }
            if (result.isFailure) {
                Log.e("TAG", "getMemes:", result.exceptionOrNull())
                loadingEvent.value = false
                errorEvent.value = result.exceptionOrNull()
            }
        }
    }
}
