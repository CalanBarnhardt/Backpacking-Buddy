package pangolin.backpackingbuddy.data

object TripsRepo {
    val trip : List<Trip> = listOf(
        Trip(
            tripNameId = "Big Sky",
            trails = emptyList(),
            campsites = emptyList(),
        ),
        Trip(
            tripNameId = "Durango",
            trails = emptyList(),
            campsites = emptyList(),
        ),
        Trip(
            tripNameId = "Steamboat",
            trails = emptyList(),
            campsites = emptyList(),
        )
    )
    var trips = listOf(
        "Big Sky",
        "Durango",
        "Steamboat",
        "Yellowstone National Park"
    )
}