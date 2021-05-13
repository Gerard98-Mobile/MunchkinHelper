package gerard.example.munchkinhelper.ui.fragments.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gerard.example.munchkinhelper.Cfg
import gerard.example.munchkinhelper.db.GameDatabase
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.db.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.coroutineContext

class GameFragmentVM(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository

    private var gameExist = false

    init{
        val gameDao = GameDatabase.getDatabase(application).gameDao()
        repository = GameRepository(gameDao)
    }

    fun updateGame(game: Game?) = viewModelScope.launch(Dispatchers.IO) {
        game?.let {
            if(gameExist == false){
                gameExist = repository.checkIfExist(game)
            }
            it.saveDate = Date().time
            if(gameExist){
                repository.update(it)
            }
            else{
                repository.insert(it)
            }
        }
    }

    fun autosave(game: Game?) = viewModelScope.launch(Dispatchers.IO) {
        if(Cfg.autoSave.value.get() == false) return@launch

        game?.let {
            if(!gameExist){
                gameExist = repository.checkIfExist(game)
            }
            it.saveDate = Date().time
            if(gameExist){
                repository.update(it)
            }
            else{
                it.id = repository.insert(it).toInt()
            }
        }
    }
}