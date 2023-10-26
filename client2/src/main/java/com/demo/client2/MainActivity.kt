package com.demo.client2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.api.DemoSdk
import com.demo.api.Result
import com.demo.client2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DemoSdk.getInstance(this).setActionCallback(object : DemoSdk.ActionCallback {
            override fun <T> onAction(result: Result<T>): String? {
                return when(result.action) {
                    "action_toggle" -> {
                        ""
                    }
                    else -> null
                }
            }
        })
    }
}
