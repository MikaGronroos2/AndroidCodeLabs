package com.example.eduskunta

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eduskunta.data.Member
import com.example.eduskunta.data.readJsonFile
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MemberScreen(navController: NavController, member: Member, modifier: Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text("Picture: ${member.pictureUrl}", fontSize = 8.sp)
        Text("Name: ${member.firstname} ${member.lastname}")
        Text("Party: ${member.party}")
        Text("Seat Number: ${member.seatNumber}")
        Text("Heteka ID: ${member.hetekaId}")
    }
}

@Preview
@Composable
fun MemberPreview() {
    val context = LocalContext.current
    val members = readJsonFile(context, "eduskunta.json")
    val navController = rememberNavController()
    MemberScreen(navController, members[0], modifier = Modifier)
}