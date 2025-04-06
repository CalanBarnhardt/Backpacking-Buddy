package pangolin.backpackingbuddy.ui.navigation.specs

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.ui.tripItinerary.ExisitngTripItinerary
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel
import java.util.UUID

data object TripItinerarySpec : IScreenSpec {
    override val arguments: List<NamedNavArgument> = emptyList()

    private const val ROUTE_BASE = "trip_iti nerary"
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

        if (tripId != null) {
            ExisitngTripItinerary(
                backpackingBuddyViewModel,
                tripId,
                onOverviewClick = {
                    navController.navigate((TripOverviewSpec.buildRoute(tripId.toString()))) },
                onExploreClick = {
                    navController.navigate((TripExploreSpec.buildRoute(tripId.toString()))) }
            )
        }

        //TODO:
//        LaunchedEffect(tripId) {
//            tripId?.let { backpackingBuddyViewModel.loadTripByUUID(it) }
//        }
//
//        val trip by backpackingBuddyViewModel.currentTripState.collectAsState()
//
//        trip?.let {
//            ExisitngTripItinerary(
//                trip = it,
//                onOverviewClick = { trip ->
//                    navController.navigate((TripOverviewSpec.buildRoute(trip.id.toString()))) },
//                onExploreClick = { trip ->
//                    navController.navigate((TripExploreSpec.buildRoute(trip.id.toString()))) }
//            )
//        }
    }

    @Composable
    override fun BottomAppBarActions(
        backpackingBuddyViewModel: BackpackingBuddyViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry?,
        context: Context
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = {
                navController.navigate(ProfileScreenSpec.route)
            }) {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "navigating"
                )
            }

            Spacer(Modifier.padding(40.dp))

            IconButton(onClick = {
                navController.navigate(ExploreScreenSpec.route)
            }) {
                Image(
                    painter = painterResource(id = R.drawable.compass_icon),
                    contentDescription = "Compass Icon"
                )
            }
        }
    }

}
