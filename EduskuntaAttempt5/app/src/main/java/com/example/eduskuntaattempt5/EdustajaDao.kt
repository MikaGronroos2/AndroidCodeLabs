package com.example.eduskuntaattempt5

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EdustajaDao {

    @Query("SELECT * from edustaja")
     fun getAllEdustajat(): Flow<List<Edustaja>>

    @Query("SELECT * from edustaja WHERE party = :party")
     fun getAllEdustajatByParty(party: String): List<Edustaja>

    @Query("SELECT DISTINCT party FROM edustaja")
     fun getAllParties(): Flow<List<String>>

    @Query("SELECT * FROM edustaja WHERE hetekaId = :hetekaId")
    fun getEdustajaByHetekaId(hetekaId: Int): Edustaja?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addEdustaja(edustaja: Edustaja)

    @Delete
    fun deleteEdustaja(edustaja: Edustaja)

}