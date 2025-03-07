package pangolin.backpackingbuddy.ui.navigation.specs

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.ui.createTrip.CreateNewTripDate
import pangolin.backpackingbuddy.ui.loginScreen.LoginScreen
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel


data object CreateNewTripDateSpec : IScreenSpec{
    private const val LOG_TAG = "448.CreateNewTripDateSpec"

    override val route = "trip-date"
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
        CreateNewTripDate()
    }
}