package com.example.notesandtodo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(modifier: Modifier) {
    // This is where you would implement the UI for the Home screen
    // For example, you could display a welcome message, a list of recent notes or todos, etc.
    // You can use Material3 components or any other UI components as needed

    // Example placeholder text
    // Replace this with your actual UI implementation
    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Screeen")
        // Add more UI components here as needed

    }
}