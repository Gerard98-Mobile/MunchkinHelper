package gerard.example.munchkinhelper.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gerard.example.munchkinhelper.db.GameDatabase
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameVM(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository

    private val gameUpdatedBoolean = false
    val gameUpdated: MutableLiveData<Boolean> = MutableLiveData()


    init {
        val gameDao = GameDatabase.getDatabase(application).gameDao()
        repository = GameRepository(gameDao)
    }

    fun saveGame(game: Game) = viewModelScope.launch(Dispatchers.IO) {
        if(!repository.checkIfExist(game)){
            repository.insert(game)
        }
        else{
            repository.update(game)
        }
        gameUpdated.postValue(!gameUpdatedBoolean)
    }

}