package gerard.example.munchkinhelper.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gerard.example.munchkinhelper.db.converters.PlayerListConverter
import gerard.example.munchkinhelper.db.dao.GameDao
import gerard.example.munchkinhelper.db.dao.SchemeDao
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Scheme

@Database(entities = [Game::class, Scheme::class], version = 2, exportSchema = true)
@TypeConverters(PlayerListConverter::class)
public abstract class GameDatabase : RoomDatabase(){

    abstract fun gameDao(): GameDao
    abstract fun schemeDao(): SchemeDao

    companion object{
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "game_database"
                ).addMigrations(
                    MIGRATION_1_2
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
