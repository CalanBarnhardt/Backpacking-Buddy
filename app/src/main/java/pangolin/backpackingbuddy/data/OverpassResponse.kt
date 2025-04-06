package pangolin.backpackingbuddy.data

data class OverpassResponse(
    val version: Double,
    val elements: List<Element>
)