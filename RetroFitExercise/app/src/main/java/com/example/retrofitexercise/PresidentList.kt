package com.example.retrofitexercise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun PresidentList(navController: NavController, viewModel: PresidentInfoViewModel, modifier: Modifier) {
    val presidentsList by viewModel::presidents

    LazyColumn {
        items(presidentsList) { president ->
            PresidentItem(president, navController)
        }
    }
}

@Composable
fun PresidentItem(president: President, navController: NavController) {
    Row(modifier = Modifier.clickable {
        navController.navigate("presidentDetail/${president.name}")
    }) {
        Text(text = "Name: ${president.name}")
    }
}