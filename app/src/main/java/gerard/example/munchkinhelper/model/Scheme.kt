package gerard.example.munchkinhelper.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import gerard.example.munchkinhelper.converters.DateConverter
import gerard.example.munchkinhelper.converters.PlayerListConverter
import java.io.Serializable

@Entity(tableName = "schemes")
class Scheme(
    // list of players
    @ColumnInfo(name = "players")
    @TypeConverters(PlayerListConverter::class)
    val players: List<Player>,
    // name of scheme
    @ColumnInfo(name = "schemeName")
    var schemeName: String?

) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}