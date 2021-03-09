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