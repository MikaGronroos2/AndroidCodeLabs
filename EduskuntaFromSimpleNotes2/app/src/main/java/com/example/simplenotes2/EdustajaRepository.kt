package com.example.simplenotes2



import kotlinx.coroutines.flow.Flow

interface EdustajaRepository {
    suspend fun insert(edustaja: Edustaja)

    fun getEdustaja(): Flow<List<Edustaja>>
    suspend fun update(edustaja: Edustaja)
    suspend fun delete(edustaja: Edustaja)
}