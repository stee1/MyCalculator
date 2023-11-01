package com.example.mycalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycalculator.ui.theme.LightGrey
import com.example.mycalculator.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<MyCalculatorViewModel>()
            val state = viewModel.state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
                    .padding(16.dp),
            ) {
                MyCalculatorResultTextView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                    state = state
                )
                Divider(
                    color = LightGrey, modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )
                MyCalculatorKeyboard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onAction = viewModel::onAction
                )
            }
        }
    }
}
