package com.example.androiddevchallenge

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.isActive

@Composable
fun Clock() {
    var timePassed by remember { mutableStateOf(0) }
    var halfTimePassed by remember { mutableStateOf(0) }
    var timestamp: Long by remember { mutableStateOf(0) }
    var halfTimestamp: Long by remember { mutableStateOf(0) }

    var timerValue by remember { mutableStateOf(0) }
    var maxValue by remember {
        mutableStateOf(0)
    }
    Crossfade(targetState = maxValue) {
        when (maxValue) {
            0 -> {
                Column() {
                    Row() {
                        Text(text = "$timerValue", Modifier.padding(10.dp))
                        Column() {
                            Image(
                                painter = painterResource(id = R.drawable.ic_arrow_up),
                                contentDescription = "ArrowUp",
                                Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        timerValue += 1
                                    }
                                    .border(1.dp, Color.Black)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_arrow_up),
                                contentDescription = "ArrowDown",
                                Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        timerValue = if (timerValue == 0) 0 else timerValue - 1
                                    }
                                    .border(1.dp, Color.Black)
                            )
                        }
                    }
                    Text(text = "Start!", modifier = Modifier
                        .padding(10.dp)
                        .clickable { maxValue = timerValue })
                }
            }
            -1 -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color.Green)
                ) {

                    Text(
                        text = "DONE",
                    )
                    Button(onClick = { maxValue = 0 }) {
                        Text("Take Me Out!")
                    }
                }
            }
            else -> {
                val MAX_HEIGHT = 280
                val ANIMATION_HEIGHT = 350
                val timeLeft = maxValue - timePassed
                LaunchedEffect(key1 = Unit) {
                    while (isActive) {
                        withInfiniteAnimationFrameMillis {
                            if (timestamp != 0L) {
                                val step = (it - timestamp) / 1000
                                if (step > 0) {
                                    timePassed += step.toInt()
                                    timestamp = it
                                }
                            } else {
                                timestamp = it
                            }
                            if (halfTimestamp != 0L) {
                                val step = (it - halfTimestamp) / 300
                                if (step > 0) {
                                    halfTimePassed += 1
                                    halfTimestamp = it
                                }
                            } else {
                                halfTimestamp = it
                            }
                        }
                    }
                }
                if (timeLeft == 0) {
                    maxValue = -1
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 150.dp)
                        .fillMaxHeight()
                ) {
                    val transition = updateTransition(targetState = halfTimePassed)
                    val offset by transition.animateDp { s ->
                        when (s % 3) {
                            0 ->
                                0.dp
                            1 ->
                                0.dp
                            2 ->
                                ANIMATION_HEIGHT.dp
                            else -> 0.dp
                        }
                    }
                    val size by transition.animateDp { s ->
                        when ((s % 3)) {
                            0 ->
                                0.dp
                            1 ->
                                ANIMATION_HEIGHT.dp
                            2 ->
                                0.dp
                            else -> 0.dp
                        }
                    }
                    Row(
                        Modifier
                            .fillMaxHeight()
                            .weight(1f, false)
                            .width(30.dp)
                            .offset(y = offset)
                    ) {
                        Box(
                            modifier = Modifier
                                .height(size)
                                .clip(CircleShape)
                                .width(30.dp)
                                .background(Color.Blue)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp, 10.dp)
                            .fillMaxHeight()
                            .weight(1f, false),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val size: Int by animateIntAsState(
                            targetValue = getNormalizedHeight(
                                MAX_HEIGHT,
                                maxValue,
                                timeLeft
                            )
                        )
                        Box(
                            modifier = Modifier
                                .padding(5.dp, 10.dp)
                                .width(size.dp)
                                .height(size.dp)
                                .border(shape = CircleShape, width = 1.dp, color = Color.Blue)
                                .clip(CircleShape)
                                .background(Color.Cyan),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$timeLeft",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(10.dp, 10.dp)
                            .fillMaxHeight()
                            .weight(1f, false),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val size: Int by animateIntAsState(
                            targetValue = getNormalizedHeight(
                                MAX_HEIGHT,
                                maxValue,
                                timePassed
                            )
                        )
                        Box(
                            modifier = Modifier
                                .padding(5.dp, 10.dp)
                                .width(size.dp)
                                .height(size.dp)
                                .clip(CircleShape)
                                .border(shape = CircleShape, width = 1.dp, color = Color.Blue)
                                .background(Color.Cyan),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$timePassed",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

fun getNormalizedHeight(maxNormalizedHeight: Int, maxValue: Int, value: Int): Int {
    if (maxValue == 0) return 20
    if (value <= 0) return 20
    return 20 + (maxNormalizedHeight * value) / maxValue
}