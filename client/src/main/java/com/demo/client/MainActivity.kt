package com.demo.client

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.api.DemoSdk
import com.demo.api.InputData
import com.demo.api.Result
import com.demo.client.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DemoSdk.getInstance(this).connect(object : DemoSdk.ResultCallback {
            override fun <T> onResult(result: Result<T>): String? {
                return when(result.action) {
                    "action_input" -> {
                        val input = (result.data as InputData).value
                        "Hello server, Client got your input value is : $input"
                    }
                    else -> null
                }
            }
        })
    }
}
