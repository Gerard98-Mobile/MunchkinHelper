package gerard.example.munchkinhelper.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object: Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE 'games' ADD COLUMN 'game_time' INTEGER NOT NULL DEFAULT 0")
    }
}