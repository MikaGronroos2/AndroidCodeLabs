package com.example.roomexercise

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.lifecycleScope
import com.example.roomexercise.ui.theme.RoomExerciseTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val db by lazy { AppDatabase.getDatabase(this) }
    private val movieDao by lazy { db.movieDao() }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val moviesWithActors by movieDao.getMoviesWithActors().collectAsState(initial = emptyList())

            RoomExerciseTheme {
                Scaffold {
                    Column {
                        InsertMovieView { movie ->
                            lifecycleScope.launch { movieDao.insertMovie(movie) }
                        }
                        MovieListView(moviesWithActors) { actor ->
                            lifecycleScope.launch { movieDao.insertActor(actor) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InsertMovieView(onInsert: (Movie) -> Unit) {
    var title by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }

    Column {
        TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        TextField(value = year, onValueChange = { year = it }, label = { Text("Year") })
        TextField(value = director, onValueChange = { director = it }, label = { Text("Director") })
        Button(onClick = {
            if (title.isNotEmpty() && year.isNotEmpty() && director.isNotEmpty()) {
                onInsert(Movie(title = title, year = year.toInt(), director = director))
            }
        }) {
            Text("Insert Movie")
        }
    }
}

@Composable
fun InsertActorView(movieId: Int, onInsert: (Actor) -> Unit) {
    var name by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }

    Column {
        TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        TextField(value = role, onValueChange = { role = it }, label = { Text("Role") })
        Button(onClick = {
            if (name.isNotEmpty() && role.isNotEmpty()) {
                onInsert(Actor(name = name, role = role, movieId = movieId))
            }
        }) {
            Text("Insert Actor")
        }
    }
}

@Composable
fun MovieListView(moviesWithActors: List<MovieWithActors>, onActorAdded: (Actor) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedMovieId by remember { mutableStateOf<Int?>(null) }

    LazyColumn {
        items(moviesWithActors) { movieWithActors ->
            Text(
                text = "Movie: ${movieWithActors.movie.title} (${movieWithActors.movie.year}), Director: ${movieWithActors.movie.director}",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        selectedMovieId = movieWithActors.movie.id
                        showDialog = true
                    }
            )
            movieWithActors.actors.forEach { actor ->
                Text("  - ${actor.name} as ${actor.role}", modifier = Modifier.padding(start = 16.dp))
            }
        }
    }

    if (showDialog && selectedMovieId != null) {
        Dialog(onDismissRequest = { showDialog = false }) {
            InsertActorView(movieId = selectedMovieId!!) { actor ->
                onActorAdded(actor)
                showDialog = false
            }
        }
    }
}