package com.demo.api

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.demo.common.ICallback
import com.demo.common.IDemoSdk
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DemoSdk private constructor(private val context: Context) {

    private var sdk: IDemoSdk? = null
    private var callback: ResultCallback? = null
    private val gson = Gson()

    private var deathRecipient = object : IBinder.DeathRecipient {
        override fun binderDied() {
            sdk?.let {
                it.asBinder().unlinkToDeath(this, 0)
                sdk = null
            }
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected: $name")
            sdk = IDemoSdk.Stub.asInterface(service)
            try {
                sdk?.registerCallback(context.packageName, object : ICallback.Stub() {
                    override fun onResult(type: String, result: String): String? {
                        println("onResult: $type, $result")
                        return when(ElementType.valueOf(type)) {
                            ElementType.INPUT -> {
                                val dataType = object : TypeToken<Result<InputData>>() {}.type
                                val input = gson.fromJson<Result<InputData>>(result, dataType)
                                callback?.onResult(input)
                            }
                            ElementType.TOGGLE -> {
                                val dataType = object : TypeToken<Result<ToggleData>>() {}.type
                                val toggle = gson.fromJson<Result<ToggleData>>(result, dataType)
                                callback?.onResult(toggle)
                            }
                        }
                    }
                })
                sdk?.asBinder()?.linkToDeath(deathRecipient, 0)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: $name")
        }
    }

    fun connect(callback: ResultCallback) {
        val intent = Intent().apply {
            component = ComponentName("com.demo.server", "com.demo.server.DemoSdkService")
            action = "com.demo.server.SDK"
            `package` = "com.demo.server"
        }
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        this.callback = callback
    }

    interface ResultCallback {
        fun <T> onResult(result: Result<T>): String?
    }

    companion object {
        private const val TAG = "DemoSdk"

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: DemoSdk? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: DemoSdk(context).also { instance = it }
        }
    }
}
