package com.example.retrofitexercise

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


fun performSearch(query: String, onResult: (Int) -> Unit) {
    val retrofit = RetroFitHelper.getInstance()
    val apiService = retrofit.create(ApiService::class.java)

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = apiService.search("query", "json", "search", query)
            withContext(Dispatchers.Main) {
                onResult(response.query.searchinfo.totalhits)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.e("SearchHelper", "Request failed", e)
                onResult(0)
            }
        }
    }
}