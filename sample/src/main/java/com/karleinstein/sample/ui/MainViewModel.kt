package com.karleinstein.sample.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.karleinstein.basemvvm.base.BaseViewModel
import com.karleinstein.sample.data.source.DefaultRepository
import com.karleinstein.sample.model.MoviePopularResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val defaultRepository: DefaultRepository) :
    BaseViewModel() {

    val movieResponse: MutableLiveData<MoviePopularResponse> = MutableLiveData()
    fun getPopularMovies() {
        CoroutineScope(Dispatchers.Default).launch {
            flowOf(defaultRepository.getPopularFilms()).run {
                onStart {
                    loadingEvent.postValue(true)
                }
                onCompletion {
                    loadingEvent.postValue(false)
                }
                collect { data ->
                    movieResponse.postValue(data)
                    Log.d("MovieResponse", "Res: $data")
                    Log.d("ThreadName", "Name: ${Thread.currentThread().name}")
                }
                catch { ex ->
                    errorEvent.postValue(ex)
                }
            }
        }
    }
}
