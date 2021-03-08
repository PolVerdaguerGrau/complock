package com.example.androiddevchallenge

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.isActive
import java.util.*

@Composable
fun Clock() {
    val calendar = remember { Calendar.getInstance() }
    var seconds by remember { mutableStateOf(calendar[Calendar.SECOND]) }
    var minutes by remember { mutableStateOf(calendar[Calendar.MINUTE]) }
    var hours by remember { mutableStateOf(calendar[Calendar.HOUR_OF_DAY]) }
    LaunchedEffect(key1 = Unit) {
        while (isActive) {
            withInfiniteAnimationFrameMillis {
                calendar.timeInMillis = System.currentTimeMillis()
                seconds = calendar[Calendar.SECOND]
                minutes = calendar[Calendar.MINUTE]
                hours = calendar[Calendar.HOUR_OF_DAY]
            }
        }
    }
    val hour1 = (hours / 10)
    val hour2 = (hours % 10)
    val minute1 = (minutes / 10)
    val minute2 = (minutes % 10)
    val seconds1 = (seconds / 10)
    val seconds2 = (seconds % 10)


    val transition = updateTransition(targetState = seconds)
    val offset by transition.animateDp { s ->
        when ((s % 10) % 4) {
            0 ->
                0.dp
            1 ->
                50.dp
            2 ->
                100.dp
            else -> 150.dp
        }
    }
    val size by transition.animateDp { s ->
        when ((seconds % 10)) {
            0, ->
                50.dp
            1 ->
                100.dp
            2 ->
                50.dp
            else -> 0.dp
        }
    }
    Row(
        Modifier
            .fillMaxWidth()
            .height(30.dp)
            .offset(offset)
    ) {
        Box(
            modifier = Modifier
                .background(Color.Cyan)
                .width(size)
                .height(30.dp)
        )
    }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val size: Int by animateIntAsState(targetValue = 20 + (hour1 * 3))
            Box(
                modifier = Modifier
                    .padding(5.dp, 10.dp)
                    .width(size.dp)
                    .height(size.dp)
                    .clip(CircleShape)
                    .background(Color.Cyan),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$hour1",
                    textAlign = TextAlign.Center
                )
            }
            val size1: Int by animateIntAsState(targetValue = 20 + (hour2 * 3))
            Box(
                modifier = Modifier
                    .padding(5.dp, 10.dp)
                    .width(size1.dp)
                    .height(size1.dp)
                    .clip(CircleShape)
                    .background(Color.Cyan),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$hour2",
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(
            modifier = Modifier.padding(10.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val size1: Int by animateIntAsState(targetValue = 20 + (minute1 * 3))
            Box(
                modifier = Modifier
                    .padding(5.dp, 10.dp)
                    .width(size1.dp)
                    .height(size1.dp)
                    .clip(CircleShape)
                    .background(Color.Cyan),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$minute1",
                    textAlign = TextAlign.Center
                )
            }
            val size: Int by animateIntAsState(targetValue = 20 + (minute2 * 3))
            Box(
                modifier = Modifier
                    .padding(5.dp, 10.dp)
                    .width(size.dp)
                    .height(size.dp)
                    .clip(CircleShape)
                    .background(Color.Cyan),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$minute2",
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(
            modifier = Modifier.padding(10.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val size1: Int by animateIntAsState(targetValue = 20 + (seconds1 * 3))
            Box(
                modifier = Modifier
                    .animateContentSize()
                    .padding(5.dp, 10.dp)
                    .width(size1.dp)
                    .height(size1.dp)
                    .clip(CircleShape)
                    .background(Color.Cyan),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$seconds1",
                    textAlign = TextAlign.Center
                )
            }
            val size: Int by animateIntAsState(targetValue = 20 + (seconds2 * 3))
            Box(
                modifier = Modifier
                    .padding(5.dp, 10.dp)
                    .width(size.dp)
                    .height(size.dp)
                    .clip(CircleShape)
                    .background(Color.Cyan),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$seconds2",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}