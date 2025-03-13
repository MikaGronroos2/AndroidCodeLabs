package com.example.simplenotes2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Edustaja::class], version = 1, exportSchema = false)
abstract class EduskuntaDB: RoomDatabase() {
    abstract fun edustajaDao(): EdustajaDao

    companion object {
        @Volatile
        private var Instance: EduskuntaDB? = null

        fun getDatabase(): EduskuntaDB {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(EduskuntaApplication.appContext, EduskuntaDB::class.java, "edustaja_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

