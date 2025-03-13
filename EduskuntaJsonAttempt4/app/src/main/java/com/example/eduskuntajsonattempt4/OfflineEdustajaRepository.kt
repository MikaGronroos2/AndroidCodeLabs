package com.example.simplenotes2

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfflineEdustajaRepository(private val edustajaDao: EdustajaDao): EdustajaRepository {

    override suspend fun insert(edustaja: Edustaja) = edustajaDao.insert(edustaja)
    override fun getEdustaja(): Flow<List<Edustaja>> = edustajaDao.getEdustajat()

    override suspend fun update(edustaja: Edustaja) = edustajaDao.update(edustaja)
    override suspend fun delete(edustaja: Edustaja) = edustajaDao.delete(edustaja)

    fun fetchEdustajatFromApi(onResult: (List<Edustaja>?) -> Unit) {
        val call = RetroFitAPI.api.getUsers()
        call.enqueue(object : Callback<List<Edustaja>> {
            override fun onResponse(call: Call<List<Edustaja>>, response: Response<List<Edustaja>>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Edustaja>>, t: Throwable) {
                onResult(null)
            }
        })
    }


}