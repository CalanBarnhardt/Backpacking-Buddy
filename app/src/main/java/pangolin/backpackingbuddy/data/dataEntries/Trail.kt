package pangolin.backpackingbuddy.data.dataEntries

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "trails")
data class Trail (val name : String,
                  val location : String,
                  val photo: Int,
                  val distance: Int,
                  val difficulty: String? = null,
                  val description: String? = null,
                  @PrimaryKey
                    val trail_id: UUID = UUID.randomUUID())