package com.demo.server

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.api.model.ElementType
import com.demo.api.model.Input
import com.demo.api.model.Result
import com.demo.api.model.Toggle
import com.demo.server.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendActionToClientButton.setOnClickListener {
            ClientManager.getInstance().action(
                "com.demo.client",
                Result("action_input", ElementType.INPUT, Input("Hi, client"))
            )
        }
        binding.sendActionToClient2Button.setOnClickListener {
            ClientManager.getInstance().action(
                "com.demo.client2",
                Result("action_toggle", ElementType.TOGGLE, Toggle(true))
            )
        }
    }
}
