package com.example.eduskunta.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.content.Context
import java.io.File

data class Member(
    val hetekaId: Int,
    val seatNumber: Int,
    val lastname: String,
    val firstname: String,
    val party: String,
    val minister: Boolean,
    val pictureUrl: String
)

fun readJsonFile(context: Context, fileName: String): List<Member> {
    val jsonFile = context.assets.open(fileName).bufferedReader().use { it.readText() }
    val gson = Gson()
    val memberListType = object : TypeToken<List<Member>>() {}.type
    return gson.fromJson(jsonFile, memberListType)
}
