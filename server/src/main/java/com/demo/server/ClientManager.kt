package com.demo.server

import com.demo.common.ICallback
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap
import com.demo.api.Result
import com.google.gson.Gson

class ClientManager private constructor() {

    private val callbacks = ConcurrentHashMap<String, ICallback>()
    private val gson = Gson()

    fun add(packageName: String, callback: ICallback) {
        callbacks[packageName] = callback
    }

    private fun remove(packageName: String) {
        callbacks.remove(packageName)
    }

    fun <T> doAction(packageName: String, result: Result<T>) : String? {
        return try {
            callbacks[packageName]?.onResult(result.type.name, gson.toJson(result))
        } catch (e: Exception) {
            remove(packageName)
            null
        }
    }

    companion object {
        @Volatile
        private var instance: ClientManager? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: ClientManager().also { instance = it }
        }
    }
}
