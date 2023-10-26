package com.demo.api

import android.content.Context
import androidx.startup.Initializer

class SdkInitializer : Initializer<DemoSdk> {
    override fun create(context: Context): DemoSdk {
        return DemoSdk.getInstance(context).also { it.connectServer() }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
