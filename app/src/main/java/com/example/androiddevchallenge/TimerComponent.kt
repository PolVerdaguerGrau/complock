package com.example.androiddevchallenge

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.isActive

@Composable
fun TimerComponent(viewModel: ScreenViewModel) {
    val MAX_HEIGHT = 280
    val ANIMATION_HEIGHT = 350
    val timeLeft = viewModel.maxValue - viewModel.timePassed
    LaunchedEffect(key1 = Unit) {
        while (isActive) {
            withInfiniteAnimationFrameMillis {
                if (viewModel.timestamp != 0L) {
                    val step = (it - viewModel.timestamp) / 1000
                    if (step > 0) {
                        viewModel.timePassed += step.toInt()
                        viewModel.timestamp = it
                    }
                } else {
                    viewModel.timestamp = it
                }
                if (viewModel.halfTimestamp != 0L) {
                    val step = (it - viewModel.halfTimestamp) / 300
                    if (step > 0) {
                        viewModel.halfTimePassed += 1
                        viewModel.halfTimestamp = it
                    }
                } else {
                    viewModel.halfTimestamp = it
                }
            }
        }
    }
    if (timeLeft == 0) {
        viewModel.maxValue = -1
    }
    androidx.compose.foundation.layout.Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = 150.dp)
            .fillMaxHeight()
    ) {
        val transition = updateTransition(targetState = viewModel.halfTimePassed)
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
                modifier = androidx.compose.ui.Modifier
                    .height(size)
                    .clip(CircleShape)
                    .width(30.dp)
                    .background(Color.Blue)
            )
        }
    }
    androidx.compose.foundation.layout.Column(
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
                    viewModel.maxValue,
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
                    viewModel.maxValue,
                    viewModel.timePassed
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
                val text = viewModel.timePassed
                Text(
                    text = "$text",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}