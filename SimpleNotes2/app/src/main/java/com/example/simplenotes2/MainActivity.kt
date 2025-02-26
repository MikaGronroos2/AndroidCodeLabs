package com.example.simplenotes2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplenotes2.ui.theme.SimpleNotes2Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            SimpleNotes2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Notes(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Notes(modifier: Modifier = Modifier) {
    val viewModel: NotesViewModel = viewModel()
    val notes = viewModel.allNotes.collectAsState(initial = listOf<Note>())
    val subjects = viewModel.subjects.collectAsState(listOf())

    Column {
        Log.d("QWERTY", "subjects ${subjects.value}")
        Row {
            Text(
                text = "Hello! ${notes.value.size} notes found.",
                modifier = modifier
            )
            Button(onClick = {
                viewModel.clear(notes.value)
            }) {
                Text("Clear")
            }
            Button(onClick = {
                viewModel.generate()
            }) {
                Text("Generate")
            }
        }
        LazyColumn {
            items(notes.value) { note ->
                Text(text = "${note.id} - ${note.subject} - ${note.text}")
            }
        }
    }
}

class NotesViewModel: ViewModel() {
    private val offlineRepo = OfflineNotesRepository(NotesDatabase.getDatabase().notesDao())
    val allNotes = offlineRepo.getNotes()
    val subjects = offlineRepo.getSubjects()

    fun generate() {
        viewModelScope.launch {
            val start = System.currentTimeMillis()
            (1..20).forEach { _ ->
                val now = System.currentTimeMillis()
                val subject = ((now - start) / 10000).toString()
                offlineRepo.insert(Note(0, now, subject, "note${now-start}"))
                delay(1500)
            }
        }
    }

    fun clear(notes: List<Note>) {
        Log.d("QWERTY", "clear()")

        viewModelScope.launch {
            Log.d("QWERTY", "deleting ${notes.size} notes")

            notes.forEach {
                offlineRepo.delete(it)
                Log.d("QWERTY", "delete(${it.id})")
            }
        }
    }

}