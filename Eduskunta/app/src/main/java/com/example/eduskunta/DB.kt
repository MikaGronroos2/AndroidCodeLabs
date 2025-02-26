package com.example.eduskunta

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.eduskunta.data.Edustaja
import com.example.eduskunta.data.EdustajaDao


@Database(entities = [Edustaja::class], version = 1, exportSchema = false)
abstract class EduskuntaDatabase: RoomDatabase(){

    abstract fun edustajaDao(): EdustajaDao

    companion object {
        @Volatile
        private var Instance: EduskuntaDatabase? = null

        fun getDatabase(): EduskuntaDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(EduskuntaApplication.appContext, EduskuntaDatabase::class.java, "eduskunta_database")
                    .build().also { Instance = it }
            }
        }
    }


}