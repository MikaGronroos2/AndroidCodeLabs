package com.example.notesandtodo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesandtodo.screens.HomeScreen
import com.example.notesandtodo.screens.NotesScreen
import com.example.notesandtodo.screens.TodoScreen

@Composable
fun Navigation(modifier: Modifier) {
    NavHost(navController = rememberNavController(), startDestination = "home") {
        composable("home") { HomeScreen(modifier = modifier ) }
        composable("notes") { NotesScreen(modifier = modifier ) }
        composable("todo") { TodoScreen(modifier = modifier) }
    }

}