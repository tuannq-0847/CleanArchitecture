package com.karleinstein.basemvvm.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    val errorEvent: MutableLiveData<Throwable> by lazy { MutableLiveData<Throwable>() }
    val loadingEvent: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    override fun onCleared() {
        super.onCleared()
        Log.d("BaseViewModel", "onCleared....${this.javaClass.name}")
    }
}
