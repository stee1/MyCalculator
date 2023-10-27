package com.example.mycalculator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

val REQUIRED_KEYBOARD_BUTTON_SIZE = 80.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var operation by remember {
                mutableStateOf("")
            }
            var result by remember {
                mutableStateOf("0")
            }
            var isFinished by remember {
                mutableStateOf(false)
            }
            var isPointAddedToCurrentNumber by remember {
                mutableStateOf(false)
            }
            val maxNumberLength = 15
            var number1CurrentLength by remember {
                mutableStateOf(0)
            }
            var number2CurrentLength by remember {
                mutableStateOf(0)
            }
            var isFirstNumberInputCompleted by remember {
                mutableStateOf(false)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFAFAFA))
                    .padding(16.dp),
            ) {
                HistoryAndResultTextView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                    operation = operation,
                    result = result,
                    isFinished = isFinished
                )
                Divider(
                    color = Color(0xFF757575), modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )
                MyKeyboard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    when (it) {
                        "AC", "C" -> {
                            operation = ""
                            result = "0"
                            isFirstNumberInputCompleted = false
                            number1CurrentLength = 0
                            number2CurrentLength = 0
                        }

                        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> {
                            if (isFinished) {
                                isFinished = !isFinished
                                operation = ""
                                if (isPointAddedToCurrentNumber) {
                                    isPointAddedToCurrentNumber = false
                                }
                            }
                            if ((it == "0" && !isFirstNumberInputCompleted && operation.isNotEmpty() ||
                                        it == "0" && isFirstNumberInputCompleted && number2CurrentLength != 0)
                                || it != "0"
                            ) {
                                var valueToAdd = ""
                                if (!isFirstNumberInputCompleted) {
                                    if (number1CurrentLength < maxNumberLength) {
                                        valueToAdd = it
                                        number1CurrentLength++
                                    }
                                } else {
                                    if (number2CurrentLength < maxNumberLength) {
                                        valueToAdd = it
                                        number2CurrentLength++
                                    }
                                }
                                operation += valueToAdd
                            }
                        }

                        "+", "-", "/", "*" -> {
                            isFirstNumberInputCompleted = true
                            if (operation == "") {
                                operation = "0$it"
                            }
                            if (isFinished) {
                                isFinished = !isFinished
                            }
                            if ("+-/*".none { operationCharacter -> operationCharacter in operation }) {
                                // Operation sign pressed for the first time
                                operation += it
                            } else {
                                operation = result + it
                                isFirstNumberInputCompleted = false
                                number1CurrentLength = 0
                                number2CurrentLength = 0
                            }
                            if (isPointAddedToCurrentNumber) {
                                isPointAddedToCurrentNumber = false
                            }
                        }

                        "." -> {
                            if (!isPointAddedToCurrentNumber) {
                                operation += if (operation.isNotEmpty() && !"+-/*".contains(
                                        operation.last()
                                    )
                                ) {
                                    isPointAddedToCurrentNumber = true
                                    it
                                } else {
                                    isPointAddedToCurrentNumber = true
                                    "0."
                                }
                            }
                        }

                        "%" -> {
                            // TODO: implement % button
                            if (!isFirstNumberInputCompleted && number1CurrentLength > 0) {
                                operation = (operation.toDouble() / 100).toString()
                            } else if (number2CurrentLength > 0) {
                                val number1 = operation.take(number1CurrentLength)
                                val number2 = operation.takeLast(number2CurrentLength)
                                val number2Percentage = String.format(
                                    "%.2f",
                                    number1.toDouble() * number2.toDouble() / 100
                                )
                                val regex = "\\d[-+/*]\\d".toRegex()
                                Log.d("MyLogs", regex.find(operation)?.range?.last.toString())
                                Log.d("MyLogs", number1)
                                Log.d("MyLogs", number2)
                                Log.d("MyLogs", number2Percentage)
                                operation = operation.replace(number2, number2Percentage)
                            }
                        }

                        "<" -> {
                            operation = operation.dropLast(1)
                            if (isFinished) {
                                isFinished = !isFinished
                            }
                        }

                        "=" -> {
                            isFinished = true
                            isFirstNumberInputCompleted = false
                            number1CurrentLength = 0
                            number2CurrentLength = 0
                        }
                    }
                    result = calculateOperationResult(operation)
                }
            }
        }
    }
}


fun calculateOperationResult(operation: String): String {
    Log.d("MyLogs", operation)
    val spacesTrimmedOperation = operation.replace(" ", "")
    val result = if (spacesTrimmedOperation == "") 0 else
        if ("+-/*".contains(spacesTrimmedOperation.last())) spacesTrimmedOperation.dropLast(1)
            .toDouble() else
            if (spacesTrimmedOperation.contains("+")) {
                val operands = spacesTrimmedOperation.split("+")
                Log.d("MyLogs", operands.toString())
                operands[0].toDouble() + operands[1].toDouble()
            } else if (spacesTrimmedOperation.drop(1).contains("-")) {
                // use kotlin built in function to skip error for expressions with negative values like -a + b
                spacesTrimmedOperation.substringBeforeLast("-").toDouble() -
                        spacesTrimmedOperation.substringAfterLast("-").toDouble()
            } else if (spacesTrimmedOperation.contains("/")) {
                val operands = spacesTrimmedOperation.split("/")
                operands[0].toDouble() / operands[1].toDouble()
            } else if (spacesTrimmedOperation.contains("*")) {
                val operands = spacesTrimmedOperation.split("*")
                operands[0].toDouble() * operands[1].toDouble()
            } else spacesTrimmedOperation.toDouble()

    return formatNumber(result)
}

fun formatNumber(number: Number): String {
    val formatSymbols = DecimalFormatSymbols(Locale.ENGLISH)
    formatSymbols.decimalSeparator = '.'
    formatSymbols.groupingSeparator = ' '
    val formatter = DecimalFormat("###,###.###############", formatSymbols)

    return formatter.format(number)
}

@Composable
fun HistoryAndResultTextView(
    modifier: Modifier = Modifier,
    operation: String,
    result: String,
    isFinished: Boolean,
    regularFontSize: TextUnit = 28.sp,
    increasedFontSize: TextUnit = 44.sp
) {
    Box(modifier = modifier) {
        Text(
            // TODO: format text with DecimalFormat like result
            text = buildAnnotatedString {
                // TODO: implement history
                // History
//                withStyle(
//                    style = SpanStyle(
//                        color = Color(0xFFBDBDBD)
//                    )
//                ) {
//                    append("5+5\n=5\n\n2+4\n=7\n\n")
//                }
                // Current operation
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF212121),
                        fontWeight = FontWeight.Bold,
                        // TODO: fontSize relative to text length
                        fontSize = if (isFinished) regularFontSize else increasedFontSize
                    )
                ) {
                    append("$operation\n")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF212121),
                        fontWeight = FontWeight.Bold,
                        fontSize = if (isFinished) increasedFontSize else regularFontSize
                    )
                ) {
                    append(result)
                }
            }, modifier = Modifier.align(Alignment.BottomEnd), textAlign = TextAlign.End
        )
    }
}

@Composable
fun MyKeyboard(
    modifier: Modifier = Modifier,
    onButtonClick: (String) -> Unit
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
                MyKeyboardButton(
                    text = "C",
                    textColor = Color(0xFF01579B),
                    onClick = onButtonClick
                )
                MyKeyboardButton(text = "7", onClick = onButtonClick)
                MyKeyboardButton(text = "4", onClick = onButtonClick)
                MyKeyboardButton(text = "1", onClick = onButtonClick)
                Spacer(modifier = Modifier.size(REQUIRED_KEYBOARD_BUTTON_SIZE))
            }
            Column(
                modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceAround
            ) {
                MyKeyboardButton(
                    text = "<",
                    textColor = Color(0xFF01579B),
                    onClick = onButtonClick
                )
                MyKeyboardButton(text = "8", onClick = onButtonClick)
                MyKeyboardButton(text = "5", onClick = onButtonClick)
                MyKeyboardButton(text = "2", onClick = onButtonClick)
                MyKeyboardButton(text = "0", onClick = onButtonClick)
            }
            Column(
                modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceAround
            ) {
                MyKeyboardButton(
                    text = "%",
                    textColor = Color(0xFF01579B),
                    onClick = onButtonClick
                )
                MyKeyboardButton(text = "9", onClick = onButtonClick)
                MyKeyboardButton(text = "6", onClick = onButtonClick)
                MyKeyboardButton(text = "3", onClick = onButtonClick)
                MyKeyboardButton(text = ".", onClick = onButtonClick)
            }
            Column(
                modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceAround
            ) {
                MyKeyboardButton(
                    text = "/",
                    textColor = Color(0xFF01579B),
                    onClick = onButtonClick
                )
                MyKeyboardButton(
                    text = "*",
                    textColor = Color(0xFF01579B),
                    onClick = onButtonClick
                )
                MyKeyboardButton(
                    text = "+",
                    textColor = Color(0xFF01579B),
                    onClick = onButtonClick
                )
                MyKeyboardButton(
                    text = "-",
                    textColor = Color(0xFF01579B),
                    onClick = onButtonClick
                )
                MyKeyboardButton(
                    text = "=",
                    textColor = Color(0xFFFAFAFA),
                    background = Color(0xFF01579B),
                    onClick = onButtonClick
                )
            }
        }
    }
}

@Composable
fun MyKeyboardButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color(0xFF212121),
    background: Color = Color.Transparent,
    fontSize: TextUnit = 28.sp,
    requiredSize: Dp = REQUIRED_KEYBOARD_BUTTON_SIZE,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .requiredSize(requiredSize)
            .clip(AbsoluteRoundedCornerShape(100))
            .background(background)
            .clickable {
                onClick(text)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}