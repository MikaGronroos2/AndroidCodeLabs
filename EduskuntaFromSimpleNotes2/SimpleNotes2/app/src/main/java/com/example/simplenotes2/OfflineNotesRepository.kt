package com.example.simplenotes2

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class OfflineNotesRepository(private val notesDao: NoteDao): NotesRepository {
    override suspend fun insert(note: Note) = notesDao.insert(note)
    override fun getNotes(): Flow<List<Note>> = notesDao.getNotes()
    override fun getNotesSince(since: Long): Flow<List<Note>> = notesDao.getNotesSince(since)
    // next function is an example of manipulating the flow content
    // transform is applied to a flow and returns a flow
    // the transformation could be performed in view model but
    // is probably more appropriate here
    override fun getSubjects(): Flow<Set<String>> = notesDao.getNotes()
        .transform { noteList -> emit(
            noteList.map { it.subject }.toSet()
        ) }
    override suspend fun update(note: Note) = notesDao.update(note)
    override suspend fun delete(note: Note) = notesDao.delete(note)
}