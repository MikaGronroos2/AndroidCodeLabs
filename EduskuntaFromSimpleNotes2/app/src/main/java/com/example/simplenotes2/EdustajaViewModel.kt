import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenotes2.Edustaja
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