package pangolin.backpackingbuddy.data.dataEntries

import androidx.room.Entity


@Entity(primaryKeys = ["tripId", "trailId"])
data class TripTrailRef(
    val tripId: Long,
    val trailId: Long
)
