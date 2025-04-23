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
import androidx.navigation.NavType
import androidx.navigation.navArgument
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.ui.tripExplore.ExistingTripExploreScreen
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel
import java.util.UUID


data object TripExploreSpec : IScreenSpec {
    private const val ARG_UUID_NAME = "uuid"
    private const val ROUTE_BASE = "trip_explore"

    override val arguments = listOf(
        navArgument(ARG_UUID_NAME) { type = NavType.StringType }
    )

    override val route = "$ROUTE_BASE/{$ARG_UUID_NAME}"

    override val title = R.string.app_name

    override fun buildRoute(vararg args: String?): String {
        val uuid = args.getOrNull(0) ?: "0"
        return "$ROUTE_BASE/$uuid"
    }

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
            ExistingTripExploreScreen(
                backpackingBuddyViewModel,
                tripId,
                onOverviewClick = {
                    navController.navigate((TripOverviewSpec.buildRoute(tripId.toString())))
                },
                onItineraryClick = {
                    navController.navigate((TripItinerarySpec.buildRoute(tripId.toString())))
                },
                onAddButtonClick = {
                    navController.navigate(AddItemToTripSpec.route)
                },
                onHitSearch = { lat, lon ->
                    navController.navigate(
                        TrailScreenSpec.buildRoute(
                            tripId.toString(),
                            lat.toString(),
                            lon.toString()
                        )
                    )
                }
            )
        }

        //TODO :
//        LaunchedEffect(tripId) {
//            tripId?.let { backpackingBuddyViewModel.loadTripByUUID(it) }
//        }

       // val trip by backpackingBuddyViewModel.currentTripState.collectAsState()

//        trip?.let {
//            ExistingTripExploreScreen(
//                trip = it,
//                onOverviewClick = { trip ->
//                    navController.navigate((TripOverviewSpec.buildRoute(trip.id.toString())))
//                },
//                onItineraryClick = { trip ->
//                    navController.navigate((TripItinerarySpec.buildRoute(trip.id.toString())))
//                },
//                onAddButtonClick = {
//                    navController.navigate(AddItemToTripSpec.route)
//                }
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
