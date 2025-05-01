import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import pangolin.backpackingbuddy.data.dataEntries.Campsite
import pangolin.backpackingbuddy.data.dataEntries.Trail
import pangolin.backpackingbuddy.data.dataEntries.TripCampsiteRef
import pangolin.backpackingbuddy.data.dataEntries.TripTrailRef
import pangolin.backpackingbuddy.data.dataEntries.Trips
import pangolin.backpackingbuddy.data.database.BackpackingBuddyDao
import pangolin.backpackingbuddy.data.database.BackpackingBuddyDatabase
import pangolin.backpackingbuddy.data.database.TripDates
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


    suspend fun deleteTrip (tripId: UUID) = backpackingBuddyDao.deleteTrip(tripId)

    suspend fun deleteTrail(trail: Trail) = backpackingBuddyDao.deleteTrail(trail)

    suspend fun deleteCampsite(campsite: Campsite) = backpackingBuddyDao.deleteCampsite(campsite)

    fun getTripNames(email: String) : Flow<List<String?>> = backpackingBuddyDao.getTripNames(email)

    fun getIDFromName(trip_name: String): Flow<UUID?> = backpackingBuddyDao.getIDFromName(trip_name)

    fun getNameFromID(trip_id: UUID): Flow<String?> = backpackingBuddyDao.getNameFromID(trip_id)

    suspend fun addTrip(trip_name: String, start_date: Date, end_date: Date, email: String) =
        backpackingBuddyDao.addTrip(
            Trips(
                trip_name = trip_name,
                start_date = start_date,
                end_date = end_date,
                email = email
            )
        )

    fun getTripDates(tripId: UUID): Flow<TripDates?> = backpackingBuddyDao.getTripDates(tripId)

    fun getAllTrips(): Flow<List<Trips?>> = backpackingBuddyDao.getAllTrips()

    suspend fun addTrail(trail: Trail, tripId: UUID) {
        backpackingBuddyDao.insertTrail(trail)
        backpackingBuddyDao.insertTripTrailCrossRef(TripTrailRef(tripId, trail.trail_id))
    }

    suspend fun associateCampsiteWithDate(campsite: Campsite, date: String) =
        backpackingBuddyDao.updateCampsiteDate(campsite.campsite_id, date)

    suspend fun associateTrailWithDate(trail: Trail, date: String) =
        backpackingBuddyDao.updateTrailDate(trail.trail_id, date)

    fun getTrailsForTrip(tripId: UUID): Flow<List<Trail?>> =
        backpackingBuddyDao.getTrailsForTrip(tripId)

    suspend fun addCampsite(campsite: Campsite, tripId: UUID) {
        backpackingBuddyDao.insertCampsite(campsite)
        backpackingBuddyDao.insertTripCampsiteRef(
            TripCampsiteRef(tripId = tripId, campsiteId = campsite.campsite_id)
        )
    }

    fun getCampsitesForTrip(tripId: UUID): Flow<List<Campsite?>> =
        backpackingBuddyDao.getCampsitesForTrip(tripId)

}