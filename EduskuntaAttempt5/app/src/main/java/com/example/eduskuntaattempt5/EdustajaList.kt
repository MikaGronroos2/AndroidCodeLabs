package com.example.eduskuntaattempt5

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun EduskuntaList(navController: NavController, modifier: Modifier, edustajaDao: EdustajaDao, category: String) {
    val scope = rememberCoroutineScope()
    var edustajaList by remember { mutableStateOf(listOf<Edustaja>()) }

    LaunchedEffect(category) {
        CoroutineScope(Dispatchers.IO).launch {
            edustajaList = edustajaDao.getAllEdustajatByParty(category)
        }
    }


    Column(modifier = modifier) {
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Back to Edustaja List")
        }
        LazyColumn {
            items(edustajaList) { edustaja ->
                EdustajaRow(edustaja, navController, modifier)
            }
        }
    }
}

@Composable
fun EdustajaRow(edustaja: Edustaja, navController: NavController, modifier: Modifier) {
    Row(modifier = modifier.clickable( onClick = { navController.navigate("Details/${edustaja.hetekaId}") })
        .border(1.dp, Color.Gray),
        horizontalArrangement = Arrangement.Center) {
        Text(text = "${edustaja.firstname} ${edustaja.lastname}", modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically))
    }
}