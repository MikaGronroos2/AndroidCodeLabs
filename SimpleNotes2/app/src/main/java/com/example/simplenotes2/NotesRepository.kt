package com.example.simplenotes2

import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun insert(note: Note)

    fun getNotes(): Flow<List<Note>>
    fun getNotesSince(since: Long): Flow<List<Note>>
    fun getSubjects(): Flow<Set<String>>

    suspend fun update(note: Note)

    suspend fun delete(note: Note)
}