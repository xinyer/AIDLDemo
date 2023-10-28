package com.demo.client2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.api.DemoSdk
import com.demo.api.Result
import com.demo.api.ToggleData
import com.demo.client2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DemoSdk.getInstance(this).connect(object : DemoSdk.ResultCallback {
            override fun <T> onResult(result: Result<T>): String? {
                return when(result.action) {
                    "action_toggle" -> {
                        val toggle = (result.data as ToggleData)
                        "Hello server, Client2 got your toggle value is: ${toggle.value}"
                    }
                    else -> null
                }
            }
        })
    }
}
