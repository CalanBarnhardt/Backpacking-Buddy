package pangolin.backpackingbuddy.data

data class Element(
    val type: String,
    val id: Long,
    val nodes: List<Long>? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val tags: Map<String, String>? = null
)