package com.demo.server

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.api.ElementType
import com.demo.api.InputData
import com.demo.api.Result
import com.demo.api.ToggleData
import com.demo.server.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendActionToClientButton.setOnClickListener {
            val result = ClientManager.getInstance().doAction(
                "com.demo.client",
                Result(
                    action = "action_input",
                    type = ElementType.INPUT,
                    data = InputData("hello input")
                )
            )
            println("result got from clint: $result")
        }
        binding.sendActionToClient2Button.setOnClickListener {
            val result = ClientManager.getInstance().doAction(
                "com.demo.client2",
                Result(action = "action_toggle", type = ElementType.TOGGLE, data = ToggleData(true))
            )
            println("result got from clint2: $result")
        }
    }
}
