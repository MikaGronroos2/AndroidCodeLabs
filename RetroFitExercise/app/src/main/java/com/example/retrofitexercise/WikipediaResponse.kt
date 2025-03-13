package com.example.retrofitexercise


data class WikipediaResponse(
    val batchcomplete: String,
    val query: Query
)

data class Query(
    val searchinfo: SearchInfo,
    val search: List<Search>
)

data class SearchInfo(
    val totalhits: Int,
    val suggestion: String,
    val suggestionsnippet: String
)

data class Search(
    val ns: Int,
    val title: String,
    val pageid: Int,
    val size: Int,
    val wordcount: Int,
    val snippet: String,
    val timestamp: String
)