package com.example.eduskunta;

import com.example.eduskunta.data.Edustaja;

import java.util.List;

import kotlinx.coroutines.flow.Flow
interface EduskuntaRepository {
    suspend fun insert(edustaja:Edustaja)

    fun getNotes(): Flow<List<Edustaja>>

    suspend fun update(edustaja: Edustaja)

    suspend fun delete(edustaja: Edustaja)
}
