package pangolin.backpackingbuddy.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import pangolin.backpackingbuddy.data.dataEntries.Campsite
import pangolin.backpackingbuddy.data.dataEntries.Trail
import pangolin.backpackingbuddy.data.dataEntries.TripCampsiteRef
import pangolin.backpackingbuddy.data.dataEntries.TripTrailRef
import pangolin.backpackingbuddy.data.dataEntries.Trips
import java.util.Date
import java.util.UUID

@Dao
interface BackpackingBuddyDao {

    // get trails associated with a trip
    @Query("""
    SELECT trails.* FROM trails
    INNER JOIN TripTrailRef ON trails.trail_Id = TripTrailRef.trailId
    WHERE TripTrailRef.tripId = :tripId
""")
    suspend fun getTrailsByTripId(tripId: Long): List<Trail?>

    // retrieve all trip names
    @Query ("SELECT trip_name FROM Trips WHERE email = :email")
    fun getTripNames(email: String): Flow<List<String?>>

    // gets trip ID from name
    //TODO: doesn't work with duplicate names
    @Query ("SELECT trip_id FROM Trips WHERE trip_name = :tripName")
    fun getIDFromName(tripName: String): Flow<UUID?>

    // get name from trip ID
    @Query ("SELECT trip_name FROM Trips WHERE trip_id = :id")
    fun getNameFromID(id: UUID): Flow<String?>

    @Query("SELECT start_date, end_date FROM Trips WHERE trip_id = :tripId")
    fun getTripDates(tripId: UUID): Flow<TripDates?>

    // delete trip with id
    @Query("DELETE FROM Trips WHERE trip_id = :tripId")
    suspend fun deleteTrip(tripId: UUID)

    @Delete
    suspend fun deleteTrail(trail: Trail)

    @Delete
    suspend fun deleteCampsite(campsite: Campsite)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrail(trail: Trail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTripTrailCrossRef(ref: TripTrailRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTripCampsiteRef(ref: TripCampsiteRef)

    @Query("SELECT * FROM trips")
    fun getAllTrips(): Flow<List<Trips?>>

    @Transaction
    @Query("""
    SELECT * FROM trails
    INNER JOIN TripTrailRef ON trails.trail_id = TripTrailRef.trailId
    WHERE TripTrailRef.tripId = :tripId
    """)
    fun getTrailsForTrip(tripId: UUID): Flow<List<Trail?>>

    @Transaction
    @Query("""
    SELECT * FROM campsites
    INNER JOIN TripCampsiteRef ON campsites.campsite_id = TripCampsiteRef.campsiteId
    WHERE TripCampsiteRef.tripId = :tripId
    """)
    fun getCampsitesForTrip(tripId: UUID): Flow<List<Campsite?>>


    // add a trail to the trails and return id
    @Insert
    suspend fun addTrail(trail: Trail): Long

    // add a trip to trips and return id
    @Insert
    suspend fun addTrip(trip: Trips): Long

    // link trip and trail
    @Insert
    suspend fun addTripTrailRef(crossRef: TripTrailRef)

    // delete trail reference
    @Query("DELETE FROM TripTrailRef WHERE trailId = :trailId")
    suspend fun deleteTrailRefsByTrailId(trailId: Long)

    // delete trail from trails table
    @Query("DELETE FROM trails WHERE trail_Id = :trailId")
    suspend fun deleteTrailById(trailId: Long)

    // access specific trail information based on key
    @Query("SELECT * FROM trails WHERE trail_Id = :trailId")
    suspend fun getTrailById(trailId: Long): Trail?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCampsite(campsite: Campsite)

    // TODO: delete a trip

    // TODO : delete reference to a trip (while trip is deleted)

    @Query("UPDATE campsites SET date = :date WHERE campsite_id = :campsiteId")
    suspend fun updateCampsiteDate(campsiteId: UUID, date: String)

    @Query("UPDATE trails SET date = :date WHERE trail_id = :trailId")
    suspend fun updateTrailDate(trailId: UUID, date: String)




}

data class TripDates(
    val start_date: Date,
    val end_date: Date
)