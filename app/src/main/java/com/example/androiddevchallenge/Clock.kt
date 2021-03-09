package com.example.androiddevchallenge

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable

@Composable
fun Clock(viewModel: ScreenViewModel) {

    Crossfade(targetState = viewModel.maxValue) {
        when (viewModel.maxValue) {
            0 -> {
                SetUpComponent(viewModel)
            }
            -1 -> {
                DoneComponent(viewModel)
            }
            else -> {
                TimerComponent(viewModel)
            }
        }
    }
}

fun getNormalizedHeight(maxNormalizedHeight: Int, maxValue: Int, value: Int): Int {
    if (maxValue == 0) return 20
    if (value <= 0) return 20
    return 20 + (maxNormalizedHeight * value) / maxValue
}