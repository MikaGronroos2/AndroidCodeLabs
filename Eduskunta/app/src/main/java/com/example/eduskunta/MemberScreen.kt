package com.example.eduskunta

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.lang.reflect.Modifier

@Composable
fun MemberScreen(navController: NavController) {
    
}

@Preview
@Composable
fun MemberPreview() {
    val navController = rememberNavController()
    MemberScreen(navController)
}