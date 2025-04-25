package pangolin.backpackingbuddy.data.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import pangolin.backpackingbuddy.data.dataEntries.Trail
import pangolin.backpackingbuddy.data.dataEntries.TripTrailRef
import pangolin.backpackingbuddy.data.dataEntries.Trips

data class TripWithTrails(
    @Embedded val trip: Trips,
    @Relation(
        parentColumn = "tripId",
        entityColumn = "trailId",
        associateBy = Junction(TripTrailRef::class)
    )
    val trails: List<Trail>
)

