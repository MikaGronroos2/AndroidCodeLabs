package com.example.eduskuntaattempt5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Edustaja::class], version = 1)
abstract class EdustajaDatabase: RoomDatabase() {

    abstract fun edustajaDao(): EdustajaDao

    companion object {
        @Volatile
        private var Instance: EdustajaDatabase? = null

        fun getDatabase(): EdustajaDatabase{
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(EduskuntaApplication.appContext, EdustajaDatabase::class.java, "edustaja_database").build().also {
                    Instance = it
                }
            }
        }
    }

}