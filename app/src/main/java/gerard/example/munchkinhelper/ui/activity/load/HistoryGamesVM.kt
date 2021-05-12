package gerard.example.munchkinhelper.ui.activity.load

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gerard.example.munchkinhelper.SingleLiveEvent
import gerard.example.munchkinhelper.db.GameDatabase
import gerard.example.munchkinhelper.db.repository.GameRepository
import gerard.example.munchkinhelper.db.repository.SchemeRepository
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Scheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryGamesVM(application: Application) : AndroidViewModel(application) {

    // repositories needed in that view
    private val gameRepository: GameRepository

    // single live data for load data
    val games: SingleLiveEvent<MutableList<Game>> = SingleLiveEvent()


    init{
        val gameDao = GameDatabase.getDatabase(application).gameDao()
        gameRepository = GameRepository(gameDao)
    }

    // function loading schemes and history games from db
    fun loadAllObjects() = viewModelScope.launch(Dispatchers.IO){
        games.postValue(gameRepository.loadGames().toMutableList())
    }

    // function remove game from db
    fun removeGame(game : Game) = viewModelScope.launch(Dispatchers.IO) {
        gameRepository.delete(game)
    }

}