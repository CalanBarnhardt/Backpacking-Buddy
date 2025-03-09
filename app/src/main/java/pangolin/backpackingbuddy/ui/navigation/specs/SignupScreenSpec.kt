package pangolin.backpackingbuddy.ui.navigation.specs

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.ui.loginScreen.LoginScreen
import pangolin.backpackingbuddy.ui.profileScreen.ProfileScreen
import pangolin.backpackingbuddy.ui.signupScreen.SignupScreen
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel

data object SignupScreenSpec : IScreenSpec{
    private const val LOG_TAG = "448.ProfileScreenSpec"

    override val route = "signup"
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
        SignupScreen(
            onSignup = {
                Log.d("Navigation", "Navigating to profile...")

                navController.navigate("profile")},
            onLogin = {
                Log.d("Navigation", "Navigating to login...")
                navController.navigate("login")}
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