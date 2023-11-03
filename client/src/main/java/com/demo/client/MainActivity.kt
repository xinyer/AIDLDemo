package com.demo.client

import android.os.Bundle
import android.os.Messenger
import androidx.appcompat.app.AppCompatActivity
import com.demo.api.DemoSDK
import com.demo.api.EgMessage
import com.demo.api.model.Element
import com.demo.api.model.Result
import com.demo.client.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DemoSDK.getInstance().connect(this, object : DemoSDK.ResultCallback {
            override fun <T : Element?> onResult(messenger: Messenger, result: Result<T>) {
                println("get result from server: $result")
                messenger.send(EgMessage("message from client").toMessage())
            }
        })
    }
}
