package com.example.navhosttraining.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navhosttraining.R

enum class NavHostTrainingScreens(@StringRes val title: Int) {
    Home(title = R.string.home),
    Screen1(title = R.string.screen1),
    Screen2(title = R.string.screen2),
    Screen3(title = R.string.screen3),
    Screen4(title = R.string.screen4)
}


@Composable
fun NavHostTrainingApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavHostTrainingScreens.Home.name
        ) {
        composable(NavHostTrainingScreens.Home.name) {HomeScreen(navController)}
        composable(NavHostTrainingScreens.Screen1.name) {FirstScreen(navController)}
        composable(NavHostTrainingScreens.Screen2.name) {SecondScreen(navController)}
        composable(NavHostTrainingScreens.Screen3.name) {ThirdScreen(navController)}
        composable(NavHostTrainingScreens.Screen4.name) {FourthScreen(navController)}
    }
}

@Composable
fun EditableTextField(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier

) {
    TextField(
        value = value,
        modifier = Modifier,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        singleLine = true
    )
}