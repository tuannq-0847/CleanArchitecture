package com.karleinstein.basemvvm

import android.util.Log
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        Log.d("BaseViewModel","onCleared....${this.javaClass.name}")
    }
}
