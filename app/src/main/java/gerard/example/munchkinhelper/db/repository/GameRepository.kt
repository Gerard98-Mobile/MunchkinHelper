package gerard.example.munchkinhelper.db.repository

import gerard.example.munchkinhelper.db.dao.GameDao
import gerard.example.munchkinhelper.model.Game

class GameRepository(private val gameDao: GameDao) {

    suspend fun loadGames() : List<Game>{
        return gameDao.findAll()
    }

    suspend fun insert(game: Game){
        gameDao.insert(game)
    }

    suspend fun update(game: Game){
        gameDao.update(game)
    }

    suspend fun delete(game: Game){
        gameDao.delete(game)
    }

    suspend fun checkIfExist(game: Game): Boolean{
        return if(gameDao.getCountById(game.id) != 0)  true else false
    }


}