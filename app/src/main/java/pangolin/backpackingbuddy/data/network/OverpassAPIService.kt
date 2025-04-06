package pangolin.backpackingbuddy.data.network

import pangolin.backpackingbuddy.data.OverpassResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface OverpassApiService {
    @GET("overpass/api/interpreter")
    suspend fun queryOverpass(
        @Query("data") query: String
    ): Response<OverpassResponse>
}