package com.example.androiddevchallenge

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.typography

@Composable
fun SetUpComponent(viewModel: ScreenViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.height(230.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            val text = viewModel.timerValue
            Text(
                text = "$text",
                Modifier.padding(10.dp),
                textAlign = TextAlign.End,
                style = typography.h2
            )
            Column {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(20.dp, 5.dp)
                        .width(30.dp)
                        .height(30.dp)
                        .clickable {
                            viewModel.timerValue += 1
                        }
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(2.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_up),
                        contentDescription = "ArrowUp",
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                    )
                }
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(20.dp, 5.dp)
                        .width(30.dp)
                        .height(30.dp)
                        .clickable {
                            if (viewModel.timerValue > 0) viewModel.timerValue -= 1
                        }
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(2.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_down),
                        contentDescription = "ArrowDown",
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = { viewModel.maxValue = viewModel.timerValue }) {
            Text(
                text = "START", style = typography.button
            )
        }
    }
}