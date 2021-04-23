package gerard.example.munchkinhelper.ui.activity.load

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gerard.example.munchkinhelper.SingleLiveEvent
import gerard.example.munchkinhelper.db.GameDatabase
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Scheme
import gerard.example.munchkinhelper.db.repository.GameRepository
import gerard.example.munchkinhelper.db.repository.SchemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoadGameVM(application: Application) : AndroidViewModel(application) {
    // repositories needed in that view
    private val gameRepository: GameRepository
    private val schemeRepository: SchemeRepository

    // single live data for load data
    val games: SingleLiveEvent<List<Game>> = SingleLiveEvent()
    val schemes: SingleLiveEvent<List<Scheme>> = SingleLiveEvent()

    // live data for deleting schemes and games
    val gameToDelete : MutableLiveData<Game> = MutableLiveData()
    val schemeToDelete : MutableLiveData<Scheme> = MutableLiveData()

    init{
        val gameDao = GameDatabase.getDatabase(application).gameDao()
        val schemeDao = GameDatabase.getDatabase(application).schemeDao()
        gameRepository = GameRepository(gameDao)
        schemeRepository = SchemeRepository(schemeDao)
    }

    // function loading schemes and history games from db
    fun loadAllObjects() = viewModelScope.launch(Dispatchers.IO){
        games.postValue(gameRepository.loadGames())
        schemes.postValue(schemeRepository.loadSchemes())
    }

    // function remove game from db
    fun removeGame(game : Game) = viewModelScope.launch(Dispatchers.IO) {
        gameRepository.delete(game)
    }

    // function remove scheme from db
    fun removeScheme(scheme: Scheme) = viewModelScope.launch(Dispatchers.IO) {
        schemeRepository.delete(scheme)
    }

}