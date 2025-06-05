package com.example.timerapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timerapplication.data.TimerViewModel

@Composable
fun TimerTest(
    modifier: Modifier = Modifier,
    viewModel: TimerViewModel = viewModel(),
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("This is a simple Timer Application")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Timer Value: ${viewModel.timerLabel.value}")
        if (viewModel.timerIsRunning.value) {
            Spacer(modifier = Modifier.height(16.dp))
            TimerStatusBar(viewModel, 5)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.timerCountDown(5) },
            enabled = !viewModel.timerIsRunning.value
        ) {
            Text("5 Second Timer")
        }
    }
}