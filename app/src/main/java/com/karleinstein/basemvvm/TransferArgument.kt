package com.karleinstein.basemvvm

object TransferArgument {

    private val tranferDataMap = mutableMapOf<String, Any>()


    fun <T> setArgument(key: String, data: T) {
        tranferDataMap[key] = data as Any
    }


    fun getArgument(key: String): `Any`? {
        return tranferDataMap[key] ?: throw Exception("null")
    }
}
