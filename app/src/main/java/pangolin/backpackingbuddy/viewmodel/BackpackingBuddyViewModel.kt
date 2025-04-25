package pangolin.backpackingbuddy.viewmodel

import BackpackingBuddyRepo
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pangolin.backpackingbuddy.data.Element
import pangolin.backpackingbuddy.data.dataEntries.Campsite
import pangolin.backpackingbuddy.data.dataEntries.Trail
import pangolin.backpackingbuddy.data.dataEntries.Trips
import pangolin.backpackingbuddy.data.database.TripDates
import pangolin.backpackingbuddy.data.network.RetrofitClient
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

class BackpackingBuddyViewModel(private val backpackingBuddyRepo : BackpackingBuddyRepo): ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    companion object {
        private const val LOG_TAG = "448.BackpackingBuddyViewModel"
    }
    // ===================================================================================================================
    // STATE FLOWS
    // ===================================================================================================================
    private val mTripNames : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val tripNames: StateFlow<List<String>>
        get() = mTripNames.asStateFlow()

    // ===================================================================================================================
    // DATABASE RETRIEVAL OPERATIONS
    // ===================================================================================================================
    // TODO: retrieve all the trips listed under the user to be observed by the profile screen so a button can display per

    // retrieves trip names for profile screen
    fun retrieveNames () : Flow<List<String>> {
        return backpackingBuddyRepo.getTripNames(getCurrentEmail())
    }

    fun deleteTrip (tripId: UUID) {
        viewModelScope.launch {
            backpackingBuddyRepo.deleteTrip(tripId)
        }
    }

    fun deleteTrail (trail: Trail){
        viewModelScope.launch {
            backpackingBuddyRepo.deleteTrail(trail)
        }
    }

    fun deleteCampsite (campsite: Campsite){
        viewModelScope.launch {
            backpackingBuddyRepo.deleteCampsite(campsite)
        }
    }

    // retrieves trip ID from a given trip name
    fun getIDFromName (trip_name: String) : Flow<UUID> {
        return backpackingBuddyRepo.getIDFromName(trip_name)
    }

    // retrieve trip name from given trip ID
    fun getNameFromID (trip_id: UUID) : Flow<String> {
        return backpackingBuddyRepo.getNameFromID(trip_id)
    }

    fun addTrip (tripName: String, start_date: Date, end_date: Date) {
        viewModelScope.launch {
            backpackingBuddyRepo.addTrip(tripName, start_date, end_date, getCurrentEmail())
        }
    }

    fun associateCampsiteWithDate (campsite: Campsite, date: String){
        viewModelScope.launch {
            backpackingBuddyRepo.associateCampsiteWithDate(campsite, date)
        }
    }

    fun associateTrailWithDate (trail : Trail, date: String) {
        viewModelScope.launch {
            backpackingBuddyRepo.associateTrailWithDate(trail, date)
        }
    }

    fun getTripDates(tripId: UUID): Flow<TripDates> {
        return backpackingBuddyRepo.getTripDates(tripId)
    }

    fun getAllTrips(): Flow<List<Trips>> = backpackingBuddyRepo.getAllTrips()

    fun addTrailToTrip(trail: Trail, tripId: UUID) {
        viewModelScope.launch {
            backpackingBuddyRepo.addTrail(trail, tripId)
        }
    }

    fun addCampsiteToTrip(campsite: Campsite, tripId: UUID) {
        viewModelScope.launch {
            backpackingBuddyRepo.addCampsite(campsite, tripId)
        }
    }

    fun getTrailsForTrip(tripId: UUID): Flow<List<Trail>> =
        backpackingBuddyRepo.getTrailsForTrip(tripId)

    fun getCampsitesForTrip(tripId: UUID): Flow<List<Campsite>> {
        return backpackingBuddyRepo.getCampsitesForTrip(tripId)
    }

//    fun convertToDateList(tripId: UUID): List<String> {
//
//        val tripDates = getTripDates(tripId)
//
//        val (start, end) = tripDates.value.range
//        val dateFormatter =  SimpleDateFormat("MM/dd", Locale.getDefault())
//
//        val list = mutableListOf<String>()
//        val calendar = Calendar.getInstance().apply { time = start }
//        val endCalendar = Calendar.getInstance().apply { time = end }
//
//        while (!calendar.after(endCalendar)) {
//            list.add(dateFormatter.format(calendar.time))
//            calendar.add(Calendar.DATE, 1)
//        }
//
//        return list
//    }

    //====================================================================================================================
    // SIGNUP SCREEN OPERATIONS
    //====================================================================================================================

    //Navigating from signup/login
    private val _navigateToHomeEvent = MutableSharedFlow<Unit>()
    val navigateToHomeEvent = _navigateToHomeEvent.asSharedFlow()
    fun triggerNavigateToHome() {
        viewModelScope.launch {
            _navigateToHomeEvent.emit(Unit)
            Log.d(LOG_TAG, "Navigate to home event triggered")
        }
    }

    // Signup Stuff
    private val _signinEmail = MutableStateFlow("")
    val signinEmail: StateFlow<String> = _signinEmail.asStateFlow()

    private val _signinPassword = MutableStateFlow("")
    val signinPassword: StateFlow<String> = _signinPassword.asStateFlow()

    private val _signinIsLoading = MutableStateFlow(false)
    val signinIsLoading: StateFlow<Boolean> = _signinIsLoading.asStateFlow()

    private val _signinError = MutableStateFlow<String?>(null) // Holds potential error messages
    val signinError: StateFlow<String?> = _signinError.asStateFlow()

    private val _signinSuccessEvent = MutableSharedFlow<Unit>()
    val signinSuccessEvent = _signinSuccessEvent.asSharedFlow()

    fun onSigninEmailChange(newEmail: String) {
        _signinEmail.value = newEmail
        if (_signinError.value != null) {
            _signinError.value = null
        }
    }

    fun onSigninPasswordChange(newPassword: String) {
        _signinPassword.value = newPassword
        if (_signinError.value != null) {
            _signinError.value = null
        }
    }

    fun performSignup() {
        if (_signinEmail.value.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_signinEmail.value).matches()) {
            _signinError.value = "Please enter a valid email address."
            return
        }
        if (_signinPassword.value.length < 6) {
            _signinError.value = "Password must be at least 6 characters."
            return
        }

        _signinIsLoading.value = true
        _signinError.value = null

        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(_signinEmail.value.trim(), _signinPassword.value)
                    .addOnCompleteListener { task ->
                        _signinIsLoading.value = false
                        if (task.isSuccessful) {
                            Log.d(LOG_TAG, "createUserWithEmail:success. User: ${auth.currentUser?.uid}")
                            _signinEmail.value = ""
                            _signinPassword.value = ""
                            viewModelScope.launch {
                                _signinSuccessEvent.emit(Unit)
                            }
                        } else {
                            Log.w(LOG_TAG, "createUserWithEmail:failure", task.exception)
                            _signinError.value = task.exception?.message ?: "Signup failed. Please try again."
                        }
                    }
            } catch (e: Exception) {
                _signinIsLoading.value = false
                Log.e(LOG_TAG, "Exception during signup process", e)
                _signinError.value = "An unexpected error occurred: ${e.localizedMessage}"
            }
        }
    }

    fun performLogin() {
            if (_signinEmail.value.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_signinEmail.value).matches()) {
                _signinError.value = "Please enter a valid email address."
                return
            }
            if (_signinPassword.value.length < 6) {
                _signinError.value = "Password must be at least 6 characters."
                return
            }

            _signinIsLoading.value = true
            _signinError.value = null

            viewModelScope.launch {
                try {
                    auth.signInWithEmailAndPassword(_signinEmail.value.trim(), _signinPassword.value)
                        .addOnCompleteListener { task ->
                            _signinIsLoading.value = false
                            if (task.isSuccessful) {
                                Log.d(LOG_TAG, "loginUserWithEmail:success. User: ${auth.currentUser?.uid}")
                                _signinEmail.value = ""
                                _signinPassword.value = ""
                                viewModelScope.launch {
                                    _signinSuccessEvent.emit(Unit)
                                }
                            } else {
                                Log.w(LOG_TAG, "loginUserWithEmail:failure", task.exception)
                                _signinError.value = "Your email or password is incorrect"
                            }
                        }
                } catch (e: Exception) {
                    _signinIsLoading.value = false
                    Log.e(LOG_TAG, "Exception during login process", e)
                    _signinError.value = "An unexpected error occurred: ${e.localizedMessage}"
                }
            }
    }

    fun clearSignupState() {
        _signinEmail.value = ""
        _signinPassword.value = ""
        _signinIsLoading.value = false
        _signinError.value = null
    }

    fun getCurrentEmail(): String {
        val email = auth.currentUser?.email
        if(email != null){
            return email
        }
        return "No user"
    }

    var trailData by mutableStateOf<List<Element>>(emptyList())
        private set

    var campsiteData by mutableStateOf<List<Element>>(emptyList())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadTrails(lat: Double, lon: Double) {
        val boundLat: Double = lat + 2.0
        val boundLon: Double = lon + 1.0
        val query = """
        [out:json][timeout:180];
        (
          way["highway"="path"]["sac_scale"="hiking"]($lat,$lon,$boundLat,$boundLon);
          node(w);
        );
        out body;
        >;
        out skel qt;
    """.trimIndent()

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.queryOverpass(query)
                if (response.isSuccessful) {
                    trailData = response.body()?.elements ?: emptyList()
                } else {
                    errorMessage = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = "Failed: ${e.message ?: "Unknown error"}"
            }
        }
    }

    fun loadCampsites(lat: Double, lon: Double) {
        val boundLat: Double = lat + 2.0
        val boundLon: Double = lon + 1.0

        val query = """
        [out:json][timeout:25];
        nwr["tourism"="camp_site"]($lat,$lon,$boundLat,$boundLon);
        out body;
        >;
        out skel qt;
        """.trimIndent()

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.queryOverpass(query)
                if (response.isSuccessful) {
                    campsiteData = response.body()?.elements ?: emptyList()
                } else {
                    errorMessage = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = "Failed: ${e.message ?: "Unknown error"}"
            }
        }
    }

}