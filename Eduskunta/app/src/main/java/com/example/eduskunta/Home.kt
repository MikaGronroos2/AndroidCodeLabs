package com.example.eduskunta

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eduskunta.data.readJsonFile
import com.example.eduskunta.data.Member
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize

@Composable
fun Home(navController: NavController, modifier: Modifier) {
    val context = LocalContext.current
    val members = readJsonFile(context, "eduskunta.json")
    Column {
        members.forEach { member ->
            Text(text = "${member.firstname} ${member.lastname}, Party: ${member.party}")
        }
    }
}


@Preview
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    Home(navController, modifier = Modifier.fillMaxSize())
}