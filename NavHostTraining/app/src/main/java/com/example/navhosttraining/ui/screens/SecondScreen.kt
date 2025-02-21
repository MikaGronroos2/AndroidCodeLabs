package com.example.navhosttraining.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navhosttraining.R


@Composable
fun SecondScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Text(stringResource(R.string.screen2))
        Button( onClick = { navController.navigate(NavHostTrainingScreens.Screen1.name)} ) { Text(text=stringResource(R.string.button_screen1)) }
        //Button( onClick = { navController.navigate(NavHostTrainingScreens.Screen2.name)} ) { Text(text=stringResource(R.string.button_screen2)) }
        Button( onClick = { navController.navigate(NavHostTrainingScreens.Screen3.name)} ) { Text(text=stringResource(R.string.button_screen3)) }
        Button( onClick = { navController.navigate(NavHostTrainingScreens.Screen4.name)} ) { Text(text=stringResource(R.string.button_screen4)) }
    }


}