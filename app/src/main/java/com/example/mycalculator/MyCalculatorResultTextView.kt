package com.example.mycalculator

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun formatNumber(value: String): String {
    if (value.isEmpty()) return value

    val formatSymbols = DecimalFormatSymbols(Locale.ENGLISH)
    formatSymbols.decimalSeparator = '.'
    formatSymbols.groupingSeparator = ' '
    val formatter = DecimalFormat("###,###.###############", formatSymbols)

    return formatter.format(value.toDouble())
}

@Composable
fun MyCalculatorResultTextView(
    modifier: Modifier = Modifier,
    state: MyCalculatorState,
    regularFontSize: TextUnit = 28.sp,
    increasedFontSize: TextUnit = 44.sp
) {
    var regularFontSizeDynamic = regularFontSize
    var increasedFontSizeDynamic = increasedFontSize
    if (state.number1.length > 12 || state.number2.length > 12) {
        regularFontSizeDynamic = 20.sp
        increasedFontSizeDynamic = 34.sp
    }
    Box(modifier = modifier) {
        Text(
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
                        fontSize = if (state.isFinished) regularFontSizeDynamic else increasedFontSizeDynamic
                    )
                ) {
                    append(
                        "${state.number1 + (state.operation?.symbol ?: "") + state.number2}\n"
                    )

                }
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF212121),
                        fontWeight = FontWeight.Bold,
                        fontSize = if (state.isFinished) increasedFontSizeDynamic else regularFontSizeDynamic
                    )
                ) {
                    append(formatNumber(state.result))
                }
            }, modifier = Modifier.align(Alignment.BottomEnd), textAlign = TextAlign.End
        )
    }
}