package pangolin.backpackingbuddy.ui.navigation.specs

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.ui.signupScreen.SignupScreen
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel

data object SignupScreenSpec : IScreenSpec{
    private const val LOG_TAG = "448.SignupScreenSpec"

    override val route = "signup"
    override val title = R.string.signup
    override val arguments: List<NamedNavArgument> = emptyList()
    override fun buildRoute(vararg args: String?) = route

    @Composable
    override fun Content(
        modifier: Modifier,
        backpackingBuddyViewModel: BackpackingBuddyViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        context: Context
    ) {
        val email by backpackingBuddyViewModel.signupEmail.collectAsState()
        val password by backpackingBuddyViewModel.signupPassword.collectAsState()
        val isLoading by backpackingBuddyViewModel.signupIsLoading.collectAsState()
        val errorMessage by backpackingBuddyViewModel.signupError.collectAsState()

        LaunchedEffect(key1 = Unit) {
            backpackingBuddyViewModel.signupSuccessEvent.collect {
                Log.d(LOG_TAG, "Signup successful event received. Navigating to profile.")
                val startRoute = ExploreScreenSpec.route
                navController.navigate(startRoute)
            }
        }

        SignupScreen(
            emailValue = email,
            passwordValue = password,
            isLoading = isLoading,
            errorMessage = errorMessage,
            onEmailChange = { backpackingBuddyViewModel.onSignupEmailChange(it) },
            onPasswordChange = { backpackingBuddyViewModel.onSignupPasswordChange(it) },
            onSignup = {
                Log.d(LOG_TAG, "Signup button clicked. Calling ViewModel performSignup.")
                backpackingBuddyViewModel.performSignup()
            },
            onLogin = {
                Log.d(LOG_TAG, "Login button clicked. Navigating to login.")
                backpackingBuddyViewModel.clearSignupState()
                val loginRoute = LoginScreenSpec.route
                navController.navigate(loginRoute)
            }
        )
    }

    @Composable
    override fun BottomAppBarActions(
        backpackingBuddyViewModel: BackpackingBuddyViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry?,
        context: Context
    ) {

    }
}