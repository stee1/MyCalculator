package com.example.mycalculator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mycalculator.ui.theme.DarkGrey


@Composable
fun MyCalculatorKeyboardButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = DarkGrey,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(AbsoluteRoundedCornerShape(100))
            .clickable {
                onClick()
            }
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}