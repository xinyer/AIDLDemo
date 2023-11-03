package com.demo.api

import android.os.Bundle
import android.os.Message
import android.os.Messenger

const val MSG_HI = 1
const val KEY_PACKAGE_NAME = "key_package_name"

class HiMessage(private val packageName: String) {

    fun toMessage(messenger: Messenger): Message {
        return Message().apply {
            what = MSG_HI
            replyTo = messenger
            data = Bundle().apply {
                putString(KEY_PACKAGE_NAME, packageName)
            }
        }
    }
}
