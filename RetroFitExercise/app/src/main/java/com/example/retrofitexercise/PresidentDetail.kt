package com.example.retrofitexercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun PresidentDetail(navController: NavHostController, viewModel: PresidentInfoViewModel, presidentName: String?) {
    val president = viewModel.presidents.find { it.name == presidentName }
    var totalHits by remember { mutableStateOf(0) }

    LaunchedEffect(presidentName) {
        if (presidentName != null) {
            performSearch(presidentName) { hits ->
                totalHits = hits
            }
        }
    }

    if (president != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${president.name}")
            Text(text = "Start Duty: ${president.startDuty}")
            Text(text = "End Duty: ${president.endDuty}")
            Text(text = "Description: ${president.description}")
            Text(text = "Wikipedia Total Hits: $totalHits")
        }
    } else {
        Text(text = "President not found")
    }
}