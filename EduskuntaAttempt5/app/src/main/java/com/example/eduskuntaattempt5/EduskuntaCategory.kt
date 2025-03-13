package com.example.eduskuntaattempt5

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun EduskuntaCategory(navController: NavController, modifier: Modifier, edustajaDao: EdustajaDao) {
    var categoryList by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            edustajaDao.getAllParties().collect { categories ->
                categoryList = categories
            }
        }
    }

    Column(modifier = modifier) {
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Back to Previous Screen")
        }
        LazyColumn {
            items(categoryList) { category ->
                CategoryRow(modifier, category, navController)
            }
        }
    }
}

@Composable
fun CategoryRow(modifier: Modifier, category: String, navController: NavController) {
    Row(modifier = modifier.clickable
    { navController.navigate("List/$category") }
        .border(1.dp, Color.Gray),
        horizontalArrangement = Arrangement.Center

    ) {
        Text(text = nameCheck(category).toString(), modifier = Modifier.padding(8.dp).align(Alignment.CenterVertically))
    }
}

fun nameCheck(party: String?): String? {
    val fullPartyName = when (party) {
        "kok" -> "Kokoomus"
        "sdp" -> "Suomen Sosiaalidemokraattinen Puolue"
        "ps" -> "Perus Suomalaiset"
        "vihr" -> "Vihreät"
        "r" -> "RKP"
        "sd" -> "Sosialidemokraatit"
        "kd" -> "Kristillisdemokraatit"
        "kesk" -> "Keskusta"
        "vas" -> "Vasemmistoliitto"
        "liik" -> "Liike Nyt"
        "tv" -> "Sininen Tulevaisuus"
        else -> party
    }
    return fullPartyName
}