package com.example.eduskunta

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import com.example.eduskunta.data.Member

/*
* NavFlow & NavController
* Layout
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
fun EduskuntaApp(modifier: Modifier) {

    val sampleMember = Member(
        hetekaId = 1,
        seatNumber = 1,
        lastname = "Doe",
        firstname = "John",
        party = "Independent",
        minister = false,
        pictureUrl = "https://example.com/john_doe.jpg"
    )

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.name
    ) {
        composable(Screens.HomeScreen.name) {
            Home(navController, modifier)
        }
        composable(Screens.MemberScreen.name) {
            MemberScreen(navController, sampleMember, modifier)
        }
    }



}

