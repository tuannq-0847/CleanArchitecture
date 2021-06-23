package com.krause.cleanarchitecture.ui.imageviewer

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.karleinstein.basemvvm.base.BaseViewModel
import com.krause.domain.repository.MemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewerViewModel @Inject constructor(private val memeRepository: MemeRepository) :
    BaseViewModel() {

    val isSaveSuccessful = MutableLiveData<Boolean>()

    fun saveImage(bitmap: Bitmap) {
        viewModelScope.launch {
            val res = memeRepository.saveImage(bitmap)
            if (res.isSuccess) {
                isSaveSuccessful.value = true
            }
            if (res.isFailure) {
                errorEvent.value = res.exceptionOrNull()
            }
        }
    }
}
