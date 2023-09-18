package com.demo.client

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.widget.Button
import com.demo.common.IHello

class MainActivity : AppCompatActivity() {

    private var hello: IHello? = null

    private var deathRecipient = object : IBinder.DeathRecipient {
        override fun binderDied() {
            hello?.let {
                it.asBinder().unlinkToDeath(this, 0)
                hello = null
            }
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected: $name")
            hello = IHello.Stub.asInterface(service)
            try {
                hello?.asBinder()?.linkToDeath(deathRecipient, 0)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: $name")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.connectServerButton).setOnClickListener {
            connectServer()
        }
        findViewById<Button>(R.id.sayHelloButton).setOnClickListener {
            sayHello()
        }
    }

    private fun connectServer() {
        val intent = Intent().apply {
            component = ComponentName("com.demo.server", "com.demo.server.HelloService")
            action = "hello"
            `package` = "com.demo.server"
        }
        bindService(intent, connection, BIND_AUTO_CREATE)
    }

    private fun sayHello() {
        hello?.say()
    }

    companion object {
        const val TAG = "AIDLClient"
    }
}
