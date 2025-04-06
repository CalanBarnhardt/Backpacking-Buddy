package pangolin.backpackingbuddy.data

data class Element(
    val type: String,
    val id: Long,
    val nodes: List<Long>? = null, // Only for ways
    val lat: Double? = null,       // Only for nodes
    val lon: Double? = null,       // Only for nodes
    val tags: Map<String, String>? = null
)