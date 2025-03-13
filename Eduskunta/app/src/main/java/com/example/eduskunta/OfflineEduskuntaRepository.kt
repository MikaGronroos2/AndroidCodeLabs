package com.example.eduskunta

import com.example.eduskunta.data.Edustaja
import com.example.eduskunta.data.EdustajaDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class OfflineNotesRepository(private val edustajaDao: EdustajaDao): EduskuntaRepository {
    override suspend fun insert(edustaja: Edustaja) = edustajaDao.insert(edustaja)

    //TODO Fix this later, to expand querys
    //override fun getNotesSince(since: Long): Flow<List<Edustaja>> = edustajaDao.getNotesSince(since)
    override fun getEdustajat(): Flow<List<Edustaja>> = edustajaDao.getEdustajat()


    // next function is an example of manipulating the flow content
    // transform is applied to a flow and returns a flow
    // the transformation could be performed in view model but
    // is probably more appropriate here
//    override fun getEdustaja(): Flow<Set<String>> = edustajaDao.getEdustajat()
//        .transform { edustajaList -> emit(
//            noteList.map { it.subject }.toSet()
//        ) }
    override suspend fun update(edustaja: Edustaja) = edustajaDao.update(edustaja)
    override suspend fun delete(edustaja: Edustaja) = edustajaDao.delete(edustaja)
}