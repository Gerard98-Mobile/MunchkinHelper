package gerard.example.munchkinhelper.model

import androidx.room.*
import gerard.example.munchkinhelper.db.converters.DateConverter
import gerard.example.munchkinhelper.db.converters.PlayerListConverter
import java.io.Serializable

@Entity(tableName = "games")
class Game(
    // param hold date of last game saving
    @ColumnInfo(name = "game_date")
    @TypeConverters(DateConverter::class)
    var saveDate: Long,
    // player list of game
    @ColumnInfo(name = "players")
    @TypeConverters(PlayerListConverter::class)
    val players: List<Player>

) : Serializable{

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}