package com.example.eduskunta

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.lang.reflect.Modifier


/*
* NavFlow & NavController
* Layout
*
* Member Class card
*
*
*
*/

enum class Screens(val title: String) {
    HomeScreen(title = "Home"),
    MemberScreen(title = "Member")
}

@Composable
fun EduskuntaApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.title,
    ) {
        composable(Screens.HomeScreen.name) {
            TODO("Add the composable with args here")
            Home(navController)
        }
        composable(Screens.MemberScreen.name) {
            TODO("Add the composable with args here")
            MemberScreen(navController)
        }
    }



}