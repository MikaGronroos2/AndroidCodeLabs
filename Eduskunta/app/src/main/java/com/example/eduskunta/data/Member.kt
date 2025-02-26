package com.example.eduskunta.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

data class Member(
    val hetekaId: Int,
    val seatNumber: Int,
    val lastName: String,
    val firstName: String,
    val party: String,
    val minister: Boolean,
    val pictureUrl: String
)

fun readJsonFile(filePath: String): List<Member> {
    val jsonFile = File(filePath).readText()
    val gson = Gson()
    val memberListType = object : TypeToken<List<Member>>() {}.type
    return gson.fromJson(jsonFile, memberListType)
}
