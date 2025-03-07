package pangolin.backpackingbuddy.viewmodel

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pangolin.backpackingbuddy.data.Trip
import java.util.UUID

class BackpackingBuddyViewModel(): ViewModel() {
    companion object {
        private const val LOG_TAG = "448.BackpackingBuddyViewModel"
    }
    private val mTrips = trips.toMutableStateList()

    /**
     * holds list of all characters stored within the view model
     */
    val trips: List<Trip>
        get() = mTrips.toList()

    private val mCurrentTripState: MutableStateFlow<Trip?> = MutableStateFlow(null)

    val currentTripState: StateFlow<Trip?>
        get() = mCurrentTripState.asStateFlow()

    /**
     * Loads a character by id into currentCharacterState, if it exists.  If id is not found
     * in list of characters, then sets currentCharacterState to null.
     * @param uuid id to use for character lookup
     */
    fun loadTripByUUID(uuid: UUID) {
        Log.d(LOG_TAG, "loadTripByUUID($uuid)")
        mCurrentTripState.value = null
        mTrips.forEach { trip ->
            if(trip.id == uuid) {
                Log.d(LOG_TAG, "Trip found! $trip")
                mCurrentTripState.value = trip
                return
            }
        }
        Log.d(LOG_TAG, "Trip not found")
        return
    }
}