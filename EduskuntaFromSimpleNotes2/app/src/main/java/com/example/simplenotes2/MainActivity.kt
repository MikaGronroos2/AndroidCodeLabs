package com.example.simplenotes2

import EdustajaViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplenotes2.ui.theme.SimpleNotes2Theme

class MainActivity : ComponentActivity() {
    private lateinit var edustajaViewModel: EdustajaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edustajaViewModel = ViewModelProvider(this).get(EdustajaViewModel::class.java)

        setContent {
            SimpleNotes2Theme {
                Eduskunta(edustajaViewModel)
            }
        }
    }
}

@Composable
fun Eduskunta(viewModel: EdustajaViewModel = viewModel()) {
    val edustajat = viewModel.edustajat.observeAsState(emptyList()).value
    Column {
        Button(onClick = { viewModel.fetchEdustajat()}) {
            Text("Fetch Data")
        }
        edustajat.forEach { edustaja ->
            Text(text = edustaja.firstname)
        }
    }

}