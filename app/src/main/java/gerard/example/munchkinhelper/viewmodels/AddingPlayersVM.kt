package gerard.example.munchkinhelper.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import gerard.example.munchkinhelper.db.GameDatabase
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Scheme
import gerard.example.munchkinhelper.repository.GameRepository
import gerard.example.munchkinhelper.repository.SchemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddingPlayersVM(application: Application) : AndroidViewModel(application) {
    private val repository: SchemeRepository

    init {
        val schemeDao = GameDatabase.getDatabase(application).schemeDao()
        repository = SchemeRepository(schemeDao)
    }

    fun insertScheme(scheme: Scheme) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(scheme)
    }
}