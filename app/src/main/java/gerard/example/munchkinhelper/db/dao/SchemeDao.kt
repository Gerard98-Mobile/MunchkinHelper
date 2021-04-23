package gerard.example.munchkinhelper.db.dao

import androidx.room.*
import gerard.example.munchkinhelper.model.Scheme

@Dao
interface SchemeDao {

    @Insert
    suspend fun insert(scheme: Scheme)

    @Delete
    suspend fun delete(scheme: Scheme)

    @Query("SELECT * FROM schemes")
    fun findAll() : List<Scheme>

}