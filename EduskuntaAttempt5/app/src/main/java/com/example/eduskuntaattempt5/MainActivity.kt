package com.example.eduskuntaattempt5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import com.example.eduskuntaattempt5.ui.theme.EduskuntaAttempt5Theme
import com.example.eduskuntaattempt5.Edustaja
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.room.Room

class MainActivity : ComponentActivity() {

    private lateinit var edustajaList: MutableList<Edustaja>
    private lateinit var edustajaDao: EdustajaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = EdustajaDatabase.getDatabase()
        edustajaDao = db.edustajaDao()

        enableEdgeToEdge()
        setContent {
            EduskuntaAttempt5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Eduskunta(modifier = Modifier.padding(innerPadding),  edustajaDao)
                }
            }
        }
    }
}

enum class EduskunnanSivut (val title: String){
    EduskuntaHome("Home"),
    EduskuntaList("List"),
    EduskuntaDetail("Details"),
    EduskuntaCategory("Category"),
    EduskuntaAdd("Add");

    companion object {
        fun createRouteForList(category: String) = "List/$category"
    }

}

@Composable
fun Eduskunta(modifier: Modifier = Modifier, edustajaDao: EdustajaDao) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = EduskunnanSivut.EduskuntaHome.title,
        modifier = modifier
    ) {
        composable(EduskunnanSivut.EduskuntaHome.title) {
            EduskuntaHome(navController, modifier = Modifier.fillMaxSize(), edustajaDao)
        }
        composable(
            route = "List/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            EduskuntaList(navController, modifier = Modifier.fillMaxSize(), edustajaDao, category)
        }
        composable(
            route = "Details/{hetekaId}",
            arguments = listOf(navArgument("hetekaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val hetekaId = backStackEntry.arguments?.getInt("hetekaId") ?: 0
            EdustajaDetail(navController, modifier = Modifier.fillMaxSize(), edustajaDao, hetekaId)
        }
        composable(EduskunnanSivut.EduskuntaCategory.title) {
            EduskuntaCategory(navController, modifier = Modifier.fillMaxSize(), edustajaDao)
        }
        composable(EduskunnanSivut.EduskuntaAdd.title) {
            EduskuntaAdd(navController, modifier = Modifier.fillMaxSize(), edustajaDao)
        }
    }
}
