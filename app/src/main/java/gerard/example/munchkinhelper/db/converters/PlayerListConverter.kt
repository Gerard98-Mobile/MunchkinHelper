package gerard.example.munchkinhelper.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import gerard.example.munchkinhelper.model.Player

class PlayerListConverter {

    val gson = Gson()

    @TypeConverter
    fun toPlayers(players : String) : List<Player>{
        val arrayType = object : TypeToken<List<Player>>() {}.type
        return gson.fromJson(players, arrayType)
    }

    @TypeConverter
    fun toString(players : List<Player>) : String{
        return gson.toJson(players)
    }

}