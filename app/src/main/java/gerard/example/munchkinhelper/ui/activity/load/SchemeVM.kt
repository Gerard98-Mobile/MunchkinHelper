package gerard.example.munchkinhelper.ui.activity.load

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gerard.example.munchkinhelper.SingleLiveEvent
import gerard.example.munchkinhelper.db.GameDatabase
import gerard.example.munchkinhelper.db.repository.GameRepository
import gerard.example.munchkinhelper.db.repository.SchemeRepository
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Scheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SchemeVM(application: Application) : AndroidViewModel(application) {

    private val schemeRepository: SchemeRepository

    val schemes: SingleLiveEvent<MutableList<Scheme>> = SingleLiveEvent()

    init{
        val schemeDao = GameDatabase.getDatabase(application).schemeDao()
        schemeRepository = SchemeRepository(schemeDao)
    }

    // function loading schemes and history games from db
    fun loadAllObjects() = viewModelScope.launch(Dispatchers.IO){
        schemes.postValue(schemeRepository.loadSchemes().toMutableList())
    }

    // function remove scheme from db
    fun removeScheme(scheme: Scheme) = viewModelScope.launch(Dispatchers.IO) {
        schemeRepository.delete(scheme)
    }

}