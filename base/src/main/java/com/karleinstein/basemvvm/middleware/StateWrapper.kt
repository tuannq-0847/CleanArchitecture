package com.karleinstein.basemvvm.middleware

sealed class StateWrapper

sealed class Success<T>(data: T) : StateWrapper()

sealed class Loading : StateWrapper()

sealed class Error(throwable: Throwable) : StateWrapper()
