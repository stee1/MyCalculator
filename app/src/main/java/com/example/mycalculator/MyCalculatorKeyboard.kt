package com.example.mycalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mycalculator.ui.theme.Blue
import com.example.mycalculator.ui.theme.White


@Composable
fun MyCalculatorKeyboard(
    modifier: Modifier = Modifier,
    state: MyCalculatorState,
    onAction: (MyCalculatorAction) -> Unit
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                MyCalculatorKeyboardButton(
                    text = if (state.number1.isEmpty()) "AC" else "C",
                    textColor = Blue,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Clear)
                    }
                )
                MyCalculatorKeyboardButton(
                    text = "7",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Number(7))
                    }
                )
                MyCalculatorKeyboardButton(
                    text = "4",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Number(4))
                    }
                )
                MyCalculatorKeyboardButton(
                    text = "1",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Number(1))
                    }
                )
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                MyCalculatorKeyboardButton(
                    text = "<",
                    textColor = Blue,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Delete)
                    }
                )
                MyCalculatorKeyboardButton(
                    text = "8",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Number(8))
                    }
                )
                MyCalculatorKeyboardButton(
                    text = "5",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Number(5))
                    }
                )
                MyCalculatorKeyboardButton(
                    text = "2",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Number(2))
                    }
                )
                MyCalculatorKeyboardButton(
                    text = "0",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Number(0))
                    }
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                MyCalculatorKeyboardButton(
                    text = "%",
                    textColor = Blue,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Percentage)
                    }
                )
                MyCalculatorKeyboardButton(text = "9",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Number(9))
                    })
                MyCalculatorKeyboardButton(text = "6",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Number(6))
                    })
                MyCalculatorKeyboardButton(text = "3",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Number(3))
                    })
                MyCalculatorKeyboardButton(text = ".",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Decimal)
                    })
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                MyCalculatorKeyboardButton(
                    text = "/",
                    textColor = Blue,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Operation.Divide)
                    }
                )
                MyCalculatorKeyboardButton(
                    text = "*",
                    textColor = Blue,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Operation.Multiply)
                    }
                )
                MyCalculatorKeyboardButton(
                    text = "+",
                    textColor = Blue,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Operation.Add)
                    }
                )
                MyCalculatorKeyboardButton(
                    text = "-",
                    textColor = Blue,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    onClick = {
                        onAction(MyCalculatorAction.Operation.Subtract)
                    }
                )
                MyCalculatorKeyboardButton(
                    text = "=",
                    textColor = White,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(Blue),
                    onClick = {
                        onAction(MyCalculatorAction.Calculate)
                    }
                )
            }
        }
    }
}