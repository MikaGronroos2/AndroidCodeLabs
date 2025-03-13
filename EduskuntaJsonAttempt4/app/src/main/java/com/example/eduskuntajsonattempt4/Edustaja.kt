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


@Entity(tableName = "edustajat")
data class Edustaja(
    @PrimaryKey(autoGenerate = true)
    val hetekaId: Int,
    val seatNumber: Int,
    val lastname: String,
    val firstname: String,
    val party: String,
    val minister: Boolean,
    val pictureUrl: String
)

@Dao
interface EdustajaDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(edustaja: Edustaja)

    @Query("select * from edustajat")
    fun getEdustajat(): Flow<List<Edustaja>>

    @Update
    suspend fun update(edustaja: Edustaja)

    @Delete
    suspend fun delete(edustaja: Edustaja)
}

