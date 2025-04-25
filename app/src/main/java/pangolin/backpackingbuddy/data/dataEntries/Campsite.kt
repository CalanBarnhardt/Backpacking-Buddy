package pangolin.backpackingbuddy.data.dataEntries

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "campsites")
    data class Campsite(
        val name: String,
        val lat: Double,
        val lon: Double,
        val date: String? = null,
        @PrimaryKey
        val campsite_id: UUID = UUID.randomUUID()
    )
