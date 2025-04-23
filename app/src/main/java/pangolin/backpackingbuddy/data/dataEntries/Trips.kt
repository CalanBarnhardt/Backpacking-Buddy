package pangolin.backpackingbuddy.data.dataEntries

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "trips")
data class Trips(val trip_name: String,
                 val start_date: Date,
                 val end_date: Date,
                 val trip_detail: String? = null,
                 val email: String,
                 @PrimaryKey
                               val trip_id: UUID = UUID.randomUUID())