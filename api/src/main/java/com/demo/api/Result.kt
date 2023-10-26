package com.demo.api

enum class ElementType {
    TOGGLE, INPUT
}

data class ToggleData(val value: Boolean)
data class InputData(val value: String)

data class Result<T>(
    val action: String,
    val type: ElementType,
    val data: T
)
