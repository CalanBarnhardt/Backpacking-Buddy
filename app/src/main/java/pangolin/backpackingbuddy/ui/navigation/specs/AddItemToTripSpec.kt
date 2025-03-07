package pangolin.backpackingbuddy.ui.navigation.specs

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.data.Trail
import pangolin.backpackingbuddy.data.Trip
import pangolin.backpackingbuddy.ui.exploreScreen.AddItemToTrip
import pangolin.backpackingbuddy.ui.loginScreen.LoginScreen
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel
import java.util.UUID


data object AddItemToTripSpec : IScreenSpec{
    private const val LOG_TAG = "448.AddItemToTripSpec"

    override val route = "add-item"
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
        AddItemToTrip(Trip(
            tripNameId = "",
            trails = emptyList(),
            campsites = emptyList(),
        ), Trail(
            name = "",
            location = "",
            photo = 1,
            description = ""
        ))
    }

    @Composable
    override fun BottomAppBarActions(
        backpackingBuddyViewModel: BackpackingBuddyViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry?,
        context: Context
    ) {}
}