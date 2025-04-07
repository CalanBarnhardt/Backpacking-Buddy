package pangolin.backpackingbuddy.data.dataEntries

import androidx.room.Entity
import java.util.UUID


@Entity(primaryKeys = ["tripId", "trailId"])
data class TripTrailRef(
    val tripId: UUID,
    val trailId: UUID
)
