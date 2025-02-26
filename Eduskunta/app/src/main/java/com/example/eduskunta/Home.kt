package com.example.eduskunta

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun Home(navController: NavController) {
    Column {
        Text("Hello from Text")
    }
}


@Preview
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    Home(navController)
}