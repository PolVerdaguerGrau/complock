package com.example.androiddevchallenge

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SetUpComponent(viewModel: ScreenViewModel) {
    Column() {
        Row() {
            val text = viewModel.timerValue
            Text(text = "$text", Modifier.padding(10.dp))
            Column() {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_up),
                    contentDescription = "ArrowUp",
                    Modifier
                        .padding(10.dp)
                        .clickable {
                            viewModel.timerValue += 1
                        }
                        .border(1.dp, Color.Black)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_up),
                    contentDescription = "ArrowDown",
                    Modifier
                        .padding(10.dp)
                        .clickable {
                            viewModel.timerValue =
                                if (viewModel.timerValue == 0) 0 else viewModel.timerValue - 1
                        }
                        .border(1.dp, Color.Black)
                )
            }
        }
        Text(text = "Start!", modifier = Modifier
            .padding(10.dp)
            .clickable { viewModel.maxValue = viewModel.timerValue })
    }
}