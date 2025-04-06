package pangolin.backpackingbuddy.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pangolin.backpackingbuddy.data.dataEntries.Trail
import pangolin.backpackingbuddy.data.dataEntries.TripTrailRef
import pangolin.backpackingbuddy.data.dataEntries.Trips

@Database(
    entities = [Trips::class, Trail::class, TripTrailRef::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class BackpackingBuddyDatabase : RoomDatabase() {

    abstract val backpackingBuddyDao : BackpackingBuddyDao

    companion object {
        @Volatile private var INSTANCE: BackpackingBuddyDatabase? = null
        fun getInstance(context: Context): BackpackingBuddyDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(context, BackpackingBuddyDatabase::class.java,
                        "backpacking-buddy-database").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}