package com.example.simplenotes2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: Long,
    val subject: String,
    val text: String
)

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Query("select * from notes order by timestamp desc")
    fun getNotes(): Flow<List<Note>>

    @Query("select * from notes order by timestamp desc")
    fun getNotes1(): List<Note>

    @Query("select * from notes where timestamp > :since")
    fun getNotesSince(since: Long): Flow<List<Note>>

    @Query("select subject from notes")
    fun getSubjects(): Flow<List<String>>

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}