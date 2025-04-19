package com.example.viewsexercise

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity


class YliAliPeli(val low: Int, val high:
Int) {
    val secret = (low..high).random()
    var guesses = 0
    fun arvaa(arvaus: Int): Arvaustulos {
        guesses++
        return if (arvaus > secret)
            Arvaustulos.High
        else if (arvaus < secret)
            Arvaustulos.Low
        else Arvaustulos.Hit
    }
}
enum class Arvaustulos {
    Low, Hit, High
}

class MainActivity : AppCompatActivity() {

    private lateinit var peli: YliAliPeli

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        peli = YliAliPeli(1, 40)

        val instructionsText: TextView = findViewById(R.id.instructionsText)
        val guessInput: EditText = findViewById(R.id.guessInput)
        val submitButton: Button = findViewById(R.id.submitButton)
        val resultText: TextView = findViewById(R.id.resultText)

        submitButton.setOnClickListener {
            val guess = guessInput.text.toString().toIntOrNull()
            if (guess != null) {
                val result = peli.arvaa(guess)
                when (result) {
                    Arvaustulos.Low -> resultText.text = "Liian pieni!"
                    Arvaustulos.High -> resultText.text = "Liian suuri!"
                    Arvaustulos.Hit -> {
                        resultText.text = "Oikein! Arvauksia: ${peli.guesses}"
                        peli = YliAliPeli(1, 40)
                    }
                }
            } else {
                resultText.text = "Syötä kelvollinen numero!"
            }
        }
    }
}

