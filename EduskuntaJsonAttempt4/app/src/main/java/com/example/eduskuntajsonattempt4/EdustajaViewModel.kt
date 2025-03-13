package com.example.eduskuntajsonattempt4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.simplenotes2.Edustaja
import com.example.simplenotes2.EdustajaDao
import com.example.simplenotes2.EdustajaRepository
import com.example.simplenotes2.OfflineEdustajaRepository
import kotlinx.coroutines.launch

class EdustajaViewModel(private val repository: OfflineEdustajaRepository) : ViewModel() {

    private val _edustajat = MutableLiveData<List<Edustaja>>()
    val edustajat: LiveData<List<Edustaja>> get() = _edustajat

    fun fetchEdustajat() {
        viewModelScope.launch {
            repository.fetchEdustajatFromApi { result ->
                _edustajat.postValue(result)
            }
        }
    }
}

class EdustajaViewModelFactory(private val repository: OfflineEdustajaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EdustajaViewModel::class.java)) {
            return EdustajaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}