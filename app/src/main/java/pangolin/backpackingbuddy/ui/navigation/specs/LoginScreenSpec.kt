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
import pangolin.backpackingbuddy.ui.loginScreen.LoginScreen
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel

data object LoginScreenSpec : IScreenSpec{
    private const val LOG_TAG = "448.LoginScreenSpec"

    override val route = "login"
    override val title = R.string.app_name
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
        val email by backpackingBuddyViewModel.signinEmail.collectAsState()
        val password by backpackingBuddyViewModel.signinPassword.collectAsState()
        val isLoading by backpackingBuddyViewModel.signinIsLoading.collectAsState()
        val errorMessage by backpackingBuddyViewModel.signinError.collectAsState()

        LaunchedEffect(key1 = Unit) {
            backpackingBuddyViewModel.signinSuccessEvent.collect {
                Log.d(LOG_TAG, "Signup successful event received. Navigating to profile.")
                val startRoute = ExploreScreenSpec.route
                navController.navigate(startRoute)
            }
        }

        LoginScreen(
            emailValue = email,
            passwordValue = password,
            isLoading = isLoading,
            errorMessage = errorMessage,
            onEmailChange = { backpackingBuddyViewModel.onSigninEmailChange(it) },
            onPasswordChange = { backpackingBuddyViewModel.onSigninPasswordChange(it) },
            onSignup = {
                Log.d(LOG_TAG, "Signup button clicked. Navigating to login.")
                backpackingBuddyViewModel.clearSignupState()
                navController.navigate(SignupScreenSpec.route)
            },
            onLogin = {
                Log.d(LOG_TAG, "Login button clicked. Trying to login.")
                backpackingBuddyViewModel.performLogin()
            }
        )
    }

    @Composable
    override fun BottomAppBarActions(
        backpackingBuddyViewModel: BackpackingBuddyViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry?,
        context: Context
    ) {}
}