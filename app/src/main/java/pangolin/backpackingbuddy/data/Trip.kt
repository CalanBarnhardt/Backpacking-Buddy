package pangolin.backpackingbuddy.data

data class Trip
    ( val tripNameId : String,
      val trails : List<String>,
      val campsites : List<String>)