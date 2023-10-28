package com.demo.server

import com.demo.api.Result
import com.demo.common.ICallback
import com.google.gson.Gson
import java.util.concurrent.ConcurrentHashMap

class ClientManager private constructor() {

    private val callbacks = ConcurrentHashMap<String, ICallback>()
    private val gson = Gson()

    fun add(packageName: String, callback: ICallback) {
        println("add client: $packageName")
        callbacks[packageName] = callback
    }

    private fun remove(packageName: String) {
        callbacks.remove(packageName)
    }

    fun <T> doAction(packageName: String, result: Result<T>) : String? {
        return try {
            callbacks[packageName]?.onResult(result.type.name, gson.toJson(result))
        } catch (e: Exception) {
            e.printStackTrace()
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
