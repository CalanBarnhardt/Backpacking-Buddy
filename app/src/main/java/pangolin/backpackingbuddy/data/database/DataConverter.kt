package pangolin.backpackingbuddy.data.database

import androidx.room.TypeConverter
import java.util.Date

class DataConverter {

    // Converts Long (timestamp) to Date
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    // Converts Date to Long (timestamp)
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}