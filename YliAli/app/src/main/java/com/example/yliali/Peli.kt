package com.example.yliali

class Peli(val low: Int, val high: Int) {
    private var vastaus: Int = (low..high).random()
    private var arvaukset: Int = 0

    fun arvaa(arvaus: Int): String {
        arvaukset++
        return when {
            arvaus < this.vastaus -> "Luku on suurempi"
            arvaus > this.vastaus -> "Luku on pienempi"
            else -> "Oikein! Arvauksia: $arvaukset"
        }
    }
}