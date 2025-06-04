package com.example.timerapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import com.example.timerapplication.ui.theme.TimerApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimerApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TimerApplication(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

class TimerViewModel : ViewModel() {
    var timerLabel: MutableState<String> = mutableStateOf("Ready")
    var timerValue: MutableState<Int> = mutableIntStateOf(0)
    var timerIsRunning: MutableState<Boolean> = mutableStateOf(false)

    fun timerCountDown(seconds: Int) {
        viewModelScope.launch {
            timerIsRunning.value = true

            for (i in seconds downTo 0) {
                timerValue.value = i
                timerLabel.value = "$i seconds remaining"
                delay(1000L)
            }
            timerLabel.value = "Done"
            timerIsRunning.value = false
        }
    }
}

@Composable
fun TimerApplication(
    modifier: Modifier = Modifier,
    viewModel: TimerViewModel = viewModel()
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

@Composable
fun TimerStatusBar(viewModel: TimerViewModel, totalSeconds: Int) {
    val progress by animateFloatAsState(
        targetValue = viewModel.timerValue.value.toFloat() / totalSeconds,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}


@Preview
@Composable
fun TimerApplicationPreview() {
    TimerApplicationTheme {
        TimerApplication(modifier = Modifier.fillMaxSize())
    }
}
