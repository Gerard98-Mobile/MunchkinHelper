package gerard.example.munchkinhelper.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import gerard.example.munchkinhelper.db.GameDatabase
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class GameFragmentVM(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository

    init{
        val gameDao = GameDatabase.getDatabase(application).gameDao()
        repository = GameRepository(gameDao)
    }

    fun updateGame(game: Game?) = viewModelScope.launch(Dispatchers.IO) {
        game?.let {
            it.saveDate = Date().time
            repository.update(it)
        }
    }

    fun insertGame(game: Game) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(game)
    }
}