package pangolin.backpackingbuddy.ui.navigation.specs

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.ui.exisitngTripItinerary.ExisitngTripItinerary
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel
import java.util.UUID

data object TripItinerarySpec : IScreenSpec {
    override val arguments: List<NamedNavArgument> = emptyList()

    private const val ROUTE_BASE = "trip_itinerary"
    private const val ARG_UUID_NAME = "uuid"

    private fun buildFullRoute(argVal: String): String {
        var fullRoute = ROUTE_BASE
        if(argVal == ARG_UUID_NAME) {
            fullRoute += "/{$argVal}"
            //Log.d(LOG_TAG, "Built base route $fullRoute")
        } else {
            fullRoute += "/$argVal"
            //Log.d(LOG_TAG, "Built specific route $fullRoute")
        }
        return fullRoute
    }

    override val route = buildFullRoute(ARG_UUID_NAME)
    override val title = R.string.app_name

    override fun buildRoute(vararg args: String?): String = buildFullRoute(args[0] ?: "0")

    @Composable
    override fun Content(
        modifier: Modifier,
        backpackingBuddyViewModel: BackpackingBuddyViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        context: Context
    ) {
        val uuidString = navBackStackEntry.arguments?.getString(ARG_UUID_NAME)
        val tripId = uuidString?.let { UUID.fromString(it) }

        LaunchedEffect(tripId) {
            tripId?.let { backpackingBuddyViewModel.loadTripByUUID(it) }
        }

        val trip by backpackingBuddyViewModel.currentTripState.collectAsState()

        trip?.let {
            ExisitngTripItinerary(
                trip = it,
                onOverviewClick = { trip ->
                    navController.navigate((TripOverviewSpec.buildRoute(trip.id.toString()))) },
                onExploreClick = { trip ->
                    navController.navigate((TripExploreSpec.buildRoute(trip.id.toString()))) }
            )
        }
    }

    @Composable
    override fun BottomAppBarActions(
        backpackingBuddyViewModel: BackpackingBuddyViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry?,
        context: Context
    ) { /*not implemented*/ }

}
