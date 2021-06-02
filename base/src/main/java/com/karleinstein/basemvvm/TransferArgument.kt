package com.karleinstein.basemvvm

import java.lang.ref.WeakReference

object TransferArgument {

    private val transferDataMap = mutableMapOf<String, WeakReference<Any>>()


    fun <T> setArgument(key: String, data: T) {
        transferDataMap[key] = WeakReference(data)
    }


    @Suppress("UNCHECKED_CAST")
    fun <T> getArgument(key: String): T? {
        return transferDataMap[key]?.get() as? T
    }
}
