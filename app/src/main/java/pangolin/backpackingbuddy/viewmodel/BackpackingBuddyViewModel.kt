package pangolin.backpackingbuddy.viewmodel

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pangolin.backpackingbuddy.data.Trip
import java.util.UUID

class BackpackingBuddyViewModel(private val mTrips : List<Trip>): ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    companion object {
        private const val LOG_TAG = "448.BackpackingBuddyViewModel"
    }
//    private val mTrips = trips.toMutableStateList()

    /**
     * holds list of all characters stored within the view model
     */
    val trips: List<Trip>
        get() = mTrips

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
    private val _signupEmail = MutableStateFlow("")
    val signupEmail: StateFlow<String> = _signupEmail.asStateFlow()

    private val _signupPassword = MutableStateFlow("")
    val signupPassword: StateFlow<String> = _signupPassword.asStateFlow()

    private val _signupIsLoading = MutableStateFlow(false)
    val signupIsLoading: StateFlow<Boolean> = _signupIsLoading.asStateFlow()

    private val _signupError = MutableStateFlow<String?>(null) // Holds potential error messages
    val signupError: StateFlow<String?> = _signupError.asStateFlow()

    private val _signupSuccessEvent = MutableSharedFlow<Unit>()
    val signupSuccessEvent = _signupSuccessEvent.asSharedFlow()

    fun onSignupEmailChange(newEmail: String) {
        _signupEmail.value = newEmail
        if (_signupError.value != null) {
            _signupError.value = null
        }
    }

    fun onSignupPasswordChange(newPassword: String) {
        _signupPassword.value = newPassword
        if (_signupError.value != null) {
            _signupError.value = null
        }
    }

    fun performSignup() {
        if (_signupEmail.value.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_signupEmail.value).matches()) {
            _signupError.value = "Please enter a valid email address."
            return
        }
        if (_signupPassword.value.length < 6) {
            _signupError.value = "Password must be at least 6 characters."
            return
        }

        _signupIsLoading.value = true
        _signupError.value = null

        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(_signupEmail.value.trim(), _signupPassword.value)
                    .addOnCompleteListener { task ->
                        _signupIsLoading.value = false
                        if (task.isSuccessful) {
                            Log.d(LOG_TAG, "createUserWithEmail:success. User: ${auth.currentUser?.uid}")
                            _signupEmail.value = ""
                            _signupPassword.value = ""
                            viewModelScope.launch {
                                _signupSuccessEvent.emit(Unit)
                            }
                        } else {
                            Log.w(LOG_TAG, "createUserWithEmail:failure", task.exception)
                            _signupError.value = task.exception?.message ?: "Signup failed. Please try again."
                        }
                    }
            } catch (e: Exception) {
                _signupIsLoading.value = false
                Log.e(LOG_TAG, "Exception during signup process", e)
                _signupError.value = "An unexpected error occurred: ${e.localizedMessage}"
            }
        }
    }

    fun clearSignupState() {
        _signupEmail.value = ""
        _signupPassword.value = ""
        _signupIsLoading.value = false
        _signupError.value = null
    }

}