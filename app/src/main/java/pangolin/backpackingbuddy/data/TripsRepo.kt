import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import pangolin.backpackingbuddy.data.dataEntries.Trips
import pangolin.backpackingbuddy.data.database.BackpackingBuddyDao
import pangolin.backpackingbuddy.data.database.BackpackingBuddyDatabase
import java.util.Date
import java.util.UUID

class BackpackingBuddyRepo private constructor(private val backpackingBuddyDao: BackpackingBuddyDao,  private val coroutineScope: CoroutineScope) {
    companion object {
        private const val LOG_TAG = "448.BackpackingBuddyRepo"
        private var INSTANCE: BackpackingBuddyRepo? = null

        /**
         * @param context
         */
        fun getInstance(context: Context, coroutineScope: CoroutineScope): BackpackingBuddyRepo {
            var instance = INSTANCE
            if (instance == null) {
                val database = BackpackingBuddyDatabase.getInstance(context)
                val dao = database.backpackingBuddyDao
                instance = BackpackingBuddyRepo(dao, coroutineScope)
                INSTANCE = instance
            }
            return instance
        }
    }

    init {
        Log.d(LOG_TAG, "initializing repository list")

    }

    // TODO: add the functions here!

    fun getTripNames() : Flow<List<String>> = backpackingBuddyDao.getTripNames()

    fun getIDFromName(trip_name: String): Flow<UUID> = backpackingBuddyDao.getIDFromName(trip_name)

    fun getNameFromID(trip_id: UUID): Flow<String> = backpackingBuddyDao.getNameFromID(trip_id)

    suspend fun addTrip(trip_name: String, start_date: Date, end_date: Date) = backpackingBuddyDao.addTrip(
        Trips(trip_name, start_date, end_date)
    )


}