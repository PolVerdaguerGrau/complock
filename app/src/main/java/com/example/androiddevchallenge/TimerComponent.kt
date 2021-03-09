/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.background
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
import androidx.compose.material.MaterialTheme
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
import com.example.androiddevchallenge.ui.theme.typography
import kotlinx.coroutines.isActive

const val MAX_HEIGHT = 230
const val MIN_HEIGHT = 50
const val ANIMATION_HEIGHT = 350

@Composable
fun TimerComponent(viewModel: ScreenViewModel) {
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
                    val step = (it - viewModel.halfTimestamp) / 400
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
    Column(
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
                0 -> 0.dp
                1 -> 0.dp
                2 -> ANIMATION_HEIGHT.dp
                else -> 0.dp
            }
        }
        val size by transition.animateDp { s ->
            when ((s % 3)) {
                0 -> 0.dp
                1 -> ANIMATION_HEIGHT.dp
                2 -> 0.dp
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
                    .background(MaterialTheme.colors.primary)
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
                    viewModel.maxValue,
                    timeLeft
                )
            )
            Column(
                modifier = Modifier
                    .padding(5.dp, 10.dp)
                    .width(size.dp)
                    .height(size.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.secondaryVariant),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Time Left",
                    style = typography.body1,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$timeLeft",
                    textAlign = TextAlign.Center, style = typography.h6,
                    color = Color.White
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
            Column(
                modifier = Modifier
                    .padding(5.dp, 10.dp)
                    .width(size.dp)
                    .height(size.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primaryVariant),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Time Passed",
                    style = typography.body1,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                val text = viewModel.timePassed
                Text(
                    text = "$text",
                    textAlign = TextAlign.Center,
                    style = typography.h6,
                    color = Color.White
                )
            }
        }
    }
}

fun getNormalizedHeight(maxNormalizedHeight: Int, maxValue: Int, value: Int): Int {
    if (maxValue == 0) return MIN_HEIGHT
    val result = MIN_HEIGHT + (maxNormalizedHeight * value) / maxValue
    return if (result <= 0) {
        MIN_HEIGHT
    } else result
}
