package com.example.notesandtodo.data

data class TodoItem(
    val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false
) {

    // Function to toggle the completion status of the item
    fun toggleCompletion() = copy(isCompleted = !isCompleted)
}