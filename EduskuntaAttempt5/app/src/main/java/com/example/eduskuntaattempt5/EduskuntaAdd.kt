package com.example.eduskuntaattempt5

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.material3.Text
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun EduskuntaAdd(navController: NavController, modifier: Modifier, edustajaDao: EdustajaDao) {

    var hetekaIdInput by remember { mutableStateOf("") }
    var seatNumberInput by remember { mutableStateOf("") }
    var firstNameInput by remember { mutableStateOf("") }
    var lastNameInput by remember { mutableStateOf("") }
    var partyInput by remember { mutableStateOf("") }
    var pictureInput by remember { mutableStateOf("") }


    Column {
        TextField(value = hetekaIdInput, onValueChange = { hetekaIdInput = it },
            label = { Text(" Heteka ID") }, modifier = Modifier.fillMaxWidth())

        TextField(value = seatNumberInput, onValueChange = { seatNumberInput = it },
            label = { Text(" Seat Number") }, modifier = Modifier.fillMaxWidth())

        TextField(value = firstNameInput, onValueChange = { firstNameInput = it },
            label = { Text(" First Name") }, modifier = Modifier.fillMaxWidth())

        TextField(value = lastNameInput, onValueChange = { lastNameInput = it },
            label = { Text(" Last Name") }, modifier = Modifier.fillMaxWidth())

        TextField(value = partyInput, onValueChange = { partyInput = it },
            label = { Text(" Party") }, modifier = Modifier.fillMaxWidth())

        TextField(value = pictureInput, onValueChange = { pictureInput = it },
            label = { Text(" Picture URL") }, modifier = Modifier.fillMaxWidth())



        Button(onClick = {

            CoroutineScope(Dispatchers.IO).launch {

            val edustaja = Edustaja(
                hetekaId = hetekaIdInput.toInt(),
                seatNumber = seatNumberInput.toInt(),
                firstname = firstNameInput,
                lastname = lastNameInput,
                party = partyInput,
                minister = false,
                pictureUrl = pictureInput
            )
            edustajaDao.addEdustaja(edustaja)}

            }
        ) {
            Text("Add Edustaja")
        }
    }
}
