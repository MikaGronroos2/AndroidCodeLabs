package com.example.eduskuntaattempt5

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.eduskuntaattempt5.eduskunnanEdustajat
import kotlinx.coroutines.flow.first


@Composable
fun EduskuntaHome(navController: NavController, modifier: Modifier, edustajaDao: EdustajaDao) {

    Column(
        modifier = Modifier
            .padding()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Button( onClick = { navController.navigate(EduskunnanSivut.EduskuntaCategory.title) } ) {
            Text("Press here to check the Edustajat")
        }

        Button( onClick = { navController.navigate(EduskunnanSivut.EduskuntaAdd.title) } ) {
            Text("Press here to go to the page to add a Edustaja")
        }

        Button(onClick = {FillEduskunta(edustajaDao) }){
            Text("Fiill the DB with Edustajat")
        }
    }

}




fun FillEduskunta(edustajaDao: EdustajaDao) {
    CoroutineScope(Dispatchers.IO).launch {
        val dbEdustajat = edustajaDao.getAllEdustajat().first()

        val missingEdustajat = eduskunnanEdustajat.filterNot { ed ->
            dbEdustajat.any {
                it.lastname == ed.lastname &&
                        it.firstname == ed.firstname &&
                        it.seatNumber == ed.seatNumber
            }
        }

        if (missingEdustajat.isNotEmpty()) {
            missingEdustajat.forEach { edustaja ->
                edustajaDao.addEdustaja(edustaja)
            }
        }
    }
}