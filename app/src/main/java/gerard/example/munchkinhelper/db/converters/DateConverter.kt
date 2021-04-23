package gerard.example.munchkinhelper.db.converters

import androidx.room.TypeConverter
import java.util.*


class DateConverter {

    @TypeConverter
    fun toDate(timeInLong : Long) : Date {
        return Date(timeInLong)
    }

    @TypeConverter
    fun toLongDate(date: Date) : Long{
        return date.time
    }

}