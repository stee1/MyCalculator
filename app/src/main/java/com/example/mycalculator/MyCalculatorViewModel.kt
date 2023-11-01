package com.example.mycalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mycalculator.MyCalculatorAction.Companion.convertOperationFromStr

class MyCalculatorViewModel : ViewModel() {
    companion object {
        private const val MAX_NUM_LENGTH = 14
    }

    var state by mutableStateOf(MyCalculatorState())

    fun onAction(action: MyCalculatorAction) {
        when (action) {
            is MyCalculatorAction.Number -> enterNumber(action.number)
            is MyCalculatorAction.Operation -> enterOperation(action.symbol)
            is MyCalculatorAction.Decimal -> enterDecimal()
            is MyCalculatorAction.Delete -> deleteLastCharacter()
            is MyCalculatorAction.Calculate -> calculate(isFinished = true)
            is MyCalculatorAction.Clear -> clear()
            is MyCalculatorAction.Percentage -> calculatePercentage()
        }
    }

    private fun clear() {
        state = when (state.number1) {
            "" -> MyCalculatorState()
            else -> MyCalculatorState(history = state.history)
        }
    }

    private fun calculatePercentage() {
        state = when {
            state.number1.isNotEmpty() && state.operation == null ->
                state.copy(
                    number1 = (state.number1.toDouble() / 100).toString(),
                    result = (state.number1.toDouble() / 100).toString()
                )

            state.number2.isNotEmpty() ->
                state.copy(number2 = (state.number1.toDouble() * state.number2.toDouble() / 100).toString())

            else -> return
        }
        calculate()
    }

    private fun calculate(isFinished: Boolean = false) {
        val number1 = state.number1.toBigDecimalOrNull()
        val number2 = state.number2.toBigDecimalOrNull()

        if (number1 != null && number2 != null) {
            val result = when (state.operation) {
                is MyCalculatorAction.Operation.Add -> number1 + number2
                is MyCalculatorAction.Operation.Divide -> number1 / number2
                is MyCalculatorAction.Operation.Multiply -> number1 * number2
                is MyCalculatorAction.Operation.Subtract -> number1 - number2
                else -> return
            }
            state = state.copy(
                result = result.toString(),
                isFinished = isFinished,
                // TODO: fix history (currently every operation is added)
                history = state.history
                        + state.number1 + state.operation?.symbol
                        + state.number2 + "=" + result.toString() + "\n"
            )
        }
    }

    private fun deleteLastCharacter() {
        state = when {
            state.number2.length == 1 ->
                state.copy(
                    number2 = state.number2.dropLast(1),
                    result = state.number1,
                    isFinished = false
                )

            state.number2.isNotEmpty() ->
                state.copy(
                    number2 = state.number2.dropLast(1)
                )

            state.operation != null ->
                state.copy(
                    operation = null
                )

            state.number1.isNotEmpty() ->
                state.copy(
                    number1 = state.number1.dropLast(1),
                    result = state.number1.dropLast(1)
                )

            else -> return
        }
        calculate()
    }

    private fun enterDecimal() {
        if (state.operation == null) {
            if (!state.number1.contains(".")) {
                state =
                    if (state.number1.isEmpty()) {
                        state.copy(number1 = "0.")
                    } else {
                        state.copy(number1 = state.number1 + ".")
                    }
            }
        } else if (!state.number2.contains(".")) {
            state =
                if (state.number2.isEmpty()) {
                    state.copy(number2 = "0.")
                } else {
                    state.copy(number2 = state.number2 + ".")
                }
        }
    }

    private fun enterOperation(symbol: String) {
        if (state.number1.isNotEmpty()) {
            if (state.isFinished) {
                state = MyCalculatorState(
                    number1 = state.result,
                    history = state.history
                )
            }
            if (state.operation == null) { // operation clicked first time
                state = state.copy(
                    operation = convertOperationFromStr(symbol),
                    isFinished = false
                )
            } else { // operation clicked second time
                state = if (state.number2.isNotEmpty()) {
                    calculate()
                    MyCalculatorState(
                        number1 = state.result,
                        operation = convertOperationFromStr(symbol),
                        history = state.history
                    )
                } else {
                    state.copy(operation = convertOperationFromStr(symbol))
                }
            }
        }
    }

    private fun enterNumber(number: Int) {
        if (state.isFinished) {
            state = MyCalculatorState(history = state.history)
        }
        if (state.operation == null) { // entering first number
            // leading zeros
            if (state.number1.isEmpty() && number == 0) {
                return
            }
            // number length
            if (state.number1.length <= MAX_NUM_LENGTH) {
                state = state.copy(
                    number1 = state.number1 + number,
                    result = state.number1 + number,
                    isFinished = false
                )
            }
        } else { // entering second number
            // leading zeros
            if (state.number2.isEmpty() && number == 0) {
                return
            }
            // number length
            if (state.number2.length <= MAX_NUM_LENGTH) {
                state = state.copy(
                    number2 = state.number2 + number,
                    isFinished = false
                )
                calculate()
            }
        }
    }
}