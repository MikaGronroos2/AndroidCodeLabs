package com.example.yliali

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.yliali.ui.theme.YliAliTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlin.Int

class YliAliPeli(val low: Int = 0, val high: Int = 10) {
    var secret = (low..high).random()
    var guesses = 0

    fun arvaa(arvaus: Int): Arvaustulos {
        guesses++
        return if (arvaus > secret)
            Arvaustulos.High
        else if (arvaus < secret)
            Arvaustulos.Low
        else Arvaustulos.Hit
    }

    fun uusiPeli() {
        this.secret = (low..high).random()
    }

}

enum class Arvaustulos {
    Low, Hit, High
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YliAliTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                YliAliPeliApp()
                }

            }
        }
    }
}




@Composable
fun YliAliPeliApp(modifier: Modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)){
    val game by remember { mutableStateOf(YliAliPeli())}
    var answer by remember { mutableStateOf("") }
    var guessInput by remember { mutableStateOf("")}
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "YliAli Peli")
        Text(text = "Arvaa luku väliltä 0-10")
        Text("Arvauksesi: ${answer}")
        Text(text = "Nykyinen määrä arvauksia: ${game.guesses}")
        TextField(value = guessInput, onValueChange = { guessInput = it })
        Button( onClick = {
            val result = game.arvaa(guessInput.toInt())
            answer = when (result) {
                Arvaustulos.Low -> "Luku on suurempi"
                Arvaustulos.High -> "Luku on pienempi"
                Arvaustulos.Hit -> "Arvaus on oikein!"
            }

        })

        {
            Text("Arvaa Numero")
        }


        Button(onClick = { game.uusiPeli()}) {
            Text("Pelaa uusi peli")
        }
    }
}




@Preview(showBackground = true)
@Composable
fun YliAliPreview() {
    YliAliTheme {
        YliAliPeliApp()
    }
}