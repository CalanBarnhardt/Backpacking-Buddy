package pangolin.backpackingbuddy.data.dataEntries

import androidx.room.Entity
import java.util.UUID

@Entity(primaryKeys = ["tripId", "campsiteId"])
data class TripCampsiteRef(
    val tripId: UUID,
    val campsiteId: UUID
)