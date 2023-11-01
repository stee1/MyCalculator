package com.example.mycalculator

sealed class MyCalculatorAction {
    data class Number(val number: Int) : MyCalculatorAction()
    object Clear : MyCalculatorAction()
    object Delete : MyCalculatorAction()
    object Decimal : MyCalculatorAction()
    object Calculate : MyCalculatorAction()
    object Percentage : MyCalculatorAction()

    sealed class Operation(val symbol: String) : MyCalculatorAction() {
        object Divide : Operation("/")
        object Add : Operation("+")
        object Subtract : Operation("-")
        object Multiply : Operation("*")
    }

    companion object {
        fun convertOperationFromStr(symbol: String): Operation? {
            when (symbol) {
                "*" -> return Operation.Multiply
                "-" -> return Operation.Subtract
                "+" -> return Operation.Add
                "/" -> return Operation.Divide
            }
            return null
        }
    }

}
