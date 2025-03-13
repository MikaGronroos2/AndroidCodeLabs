package com.example.eduskuntaattempt5

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Edustaja(
    @PrimaryKey(autoGenerate = true)
    val hetekaId: Int,
    val seatNumber: Int,
    val lastname: String,
    val firstname: String,
    val party: String,
    val minister: Boolean,
    val pictureUrl: String
)

