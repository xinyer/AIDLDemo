package com.demo.api

import android.os.Bundle
import android.os.Message

const val MSG_EG = 2
const val KEY_CONTENT = "key_content"

class EgMessage(private val content: String) {

    fun toMessage(): Message {
        return Message().apply {
            what = MSG_EG
            data = Bundle().apply {
                putString(KEY_CONTENT, content)
            }
        }
    }
}
