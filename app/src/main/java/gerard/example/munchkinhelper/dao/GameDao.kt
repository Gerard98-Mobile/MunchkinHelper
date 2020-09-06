package gerard.example.munchkinhelper.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import gerard.example.munchkinhelper.converters.PlayerListConverter
import gerard.example.munchkinhelper.model.Game

@Dao
interface GameDao {

    @Insert
    suspend fun insert(game: Game)

    @Update
    suspend fun update(game: Game)

    @Delete
    suspend fun delete(game: Game)

    @Query("SELECT * FROM games ORDER BY game_date DESC")
    fun findAll() : List<Game>

    @Query("SELECT COUNT(*) FROM games WHERE ID LIKE :id")
    fun getCountById(id: Int) : Int

}