package pangolin.backpackingbuddy.ui.navigation.specs

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image


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
        val trip by backpackingBuddyViewModel.currentTripState.collectAsState()

        trip?.let { AddItemToTrip(it,
            Trail("Haviland Lake Trail",
                "Durango",
                R.drawable.havilandlake,
                "3.8 miles, Moderate"),
            onAddNewItemClick = { trip ->
                Toast.makeText(context, "'Haviland Lake Trail' has been added to your trip!", Toast.LENGTH_SHORT).show()
                navController.navigate((TripOverviewSpec.buildRoute(trip.id.toString())))
            }) }

    }

    @Composable
    override fun BottomAppBarActions(
        backpackingBuddyViewModel: BackpackingBuddyViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry?,
        context: Context
    ) {}
}