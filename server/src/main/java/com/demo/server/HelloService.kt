package com.demo.server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.demo.common.IHello
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class HelloService : Service() {

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind: $intent")
        return HelloBinder()
    }

    override fun onCreate() {
        super.onCreate()
        var i = 0
        thread {
            while (true) {
                sleep(1000)
                Log.d(TAG, "Hello Server $i")
                i++
            }
        }
    }

    inner class HelloBinder : IHello.Stub() {
        override fun say() {
            Log.d(TAG, "say Hello from server ")
        }
    }

    companion object {
        private val TAG = HelloService::class.java.simpleName
    }
}
