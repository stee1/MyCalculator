package com.example.mycalculator

data class MyCalculatorState(
    val number1: String = "",
    val number2: String = "",
    val operation: MyCalculatorAction.Operation? = null,
    val result: String = "0",
    val isFinished: Boolean = false
)
