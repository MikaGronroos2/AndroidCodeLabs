package com.example.eduskuntaattempt5

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun EdustajaDetail(navController: NavController, modifier: Modifier, edustajaDao: EdustajaDao, hetekaId: Int) {
    val scope = rememberCoroutineScope()
    var edustaja by remember { mutableStateOf<Edustaja?>(null) }

    LaunchedEffect(hetekaId) {
        CoroutineScope(Dispatchers.IO).launch {
            edustaja = edustajaDao.getEdustajaByHetekaId(hetekaId)
        }
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Back to Categories")
        }

        Text("${nameCheck(edustaja?.party)}")
        Text("${edustaja?.pictureUrl}")
        Text("Name: ${edustaja?.firstname} ${edustaja?.lastname}")
        Text("Seat Number: ${edustaja?.seatNumber}")
        Button(onClick = {
            edustaja?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    edustajaDao.deleteEdustaja(it)
                    withContext(Dispatchers.Main) {
                        navController.popBackStack()
                    }
                }
            }
        }) {
            Text("Press here to Delete Edustaja")
        }
    }



}