package com.example.simplenotes2

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
import com.example.eduskuntajsonattempt4.EdustajaViewModel
import com.example.eduskuntajsonattempt4.EdustajaViewModelFactory
import com.example.eduskuntajsonattempt4.ui.theme.EduskuntaJsonAttempt4Theme
import com.example.simplenotes2.EduskuntaDB

class MainActivity : ComponentActivity() {
    private lateinit var edustajaViewModel: EdustajaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edustajaViewModel = ViewModelProvider(this).get(EdustajaViewModel::class.java)

        setContent {
            EduskuntaJsonAttempt4Theme {
                Eduskunta()
            }
        }
        val database = EduskuntaDB.getDatabase()
        val edustajaDao = database.edustajaDao()
        val repository = OfflineEdustajaRepository(edustajaDao)
        val factory = EdustajaViewModelFactory(repository)
        edustajaViewModel = ViewModelProvider(this, factory).get(EdustajaViewModel::class.java)
    }
}

@Composable
fun EduskuntaWithViewModel(viewModel: EdustajaViewModel = viewModel()) {
    val edustajat = viewModel.edustajat.observeAsState(emptyList()).value
    Text("Hello It's Me")

}

@Composable
fun Eduskunta() {
    Text("Hello it me")
}