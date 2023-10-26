package com.demo.server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.demo.common.ICallback
import com.demo.common.IDemoSdk

class DemoSdkService : Service() {

    private val binder = object : IDemoSdk.Stub() {
        override fun registerCallback(packageName: String, callback: ICallback) {
            ClientManager.getInstance().add(packageName, callback)
        }
    }

    override fun onBind(intent: Intent?): IBinder = binder

}
