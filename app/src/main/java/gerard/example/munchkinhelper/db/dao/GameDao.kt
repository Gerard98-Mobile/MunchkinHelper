package gerard.example.munchkinhelper.db.dao

import androidx.room.*
import gerard.example.munchkinhelper.model.Game

@Dao
interface GameDao {

    @Insert
    suspend fun insert(game: Game) : Long

    @Update
    suspend fun update(game: Game)

    @Delete
    suspend fun delete(game: Game)

    @Query("SELECT * FROM games ORDER BY game_date DESC")
    fun findAll() : List<Game>

    @Query("SELECT COUNT(*) FROM games WHERE ID LIKE :id")
    fun getCountById(id: Int) : Int

}