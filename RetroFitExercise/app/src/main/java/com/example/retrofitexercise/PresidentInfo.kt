package com.example.retrofitexercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun PresidentInfoApp(viewModel: PresidentInfoViewModel= viewModel(), modifier: Modifier) {

    val navController = rememberNavController()
    NavigationSetup(navController, viewModel, modifier = Modifier)



}

@Composable
fun NavigationSetup(navController: NavHostController, viewModel: PresidentInfoViewModel, modifier: Modifier) {
    NavHost(navController, startDestination = "presidentList") {
        composable("presidentList") { PresidentList(navController, viewModel, modifier) }
        composable("presidentDetail/{presidentName}") { backStackEntry ->
            val presidentName = backStackEntry.arguments?.getString("presidentName")
            PresidentDetail(navController, viewModel, presidentName)
        }
    }
}

