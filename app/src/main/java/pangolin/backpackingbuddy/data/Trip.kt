package pangolin.backpackingbuddy.data

import java.util.UUID

data class Trip
    ( val tripNameId : String,
      val trails : List<String>,
      val campsites : List<String>,
      val id: UUID = UUID.randomUUID())