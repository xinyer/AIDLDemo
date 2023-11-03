package com.demo.server

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log
import com.demo.api.KEY_CONTENT
import com.demo.api.KEY_PACKAGE_NAME
import com.demo.api.MSG_HI

class DemoSdkService : Service() {

    private lateinit var messenger: Messenger

    override fun onBind(intent: Intent?): IBinder {
        messenger = Messenger(IncomingHandler())
        return messenger.binder
    }

    internal class IncomingHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == MSG_HI) {
                msg.data.getString(KEY_PACKAGE_NAME)?.let {
                    Log.d(TAG, "[$it] connected to server")
                    ClientManager.getInstance().add(it, msg.replyTo)
                }
            } else {
                msg.data.getString(KEY_CONTENT)?.let {
                    Log.d(TAG, "eg message content: $it")
                }
            }
        }
    }

    companion object {
        private const val TAG = "DemoSdkService"
    }
}
