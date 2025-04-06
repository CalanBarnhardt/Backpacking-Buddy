package pangolin.backpackingbuddy.ui.navigation.specs

import android.content.Context
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.ui.createTrip.CreateNewTripDate
import pangolin.backpackingbuddy.ui.loginScreen.LoginScreen
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


data object CreateNewTripDateSpec : IScreenSpec{
    private const val LOG_TAG = "448.CreateNewTripDateSpec"

    override val route = "trip-date/{tripName}"
    override val title = R.string.app_name
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument("tripName") { defaultValue = ""; nullable = false }
    )
    override fun buildRoute(vararg args: String?) = "trip-date/${args.firstOrNull() ?: ""}"

    @Composable
    override fun Content(
        modifier: Modifier,
        backpackingBuddyViewModel: BackpackingBuddyViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        context: Context
    ) {

        val encodedTripName = navBackStackEntry.arguments?.getString("tripName") ?: ""
        val tripName = URLDecoder.decode(encodedTripName, StandardCharsets.UTF_8.toString())

        CreateNewTripDate(backpackingBuddyViewModel, tripName, onGetStarted = { navController.navigate(ProfileScreenSpec.route) })

//        val trip by backpackingBuddyViewModel.currentTripState.collectAsState()
//
//        trip?.let {
//            CreateNewTripDate(it, onGetStarted = { trip ->
//                navController.navigate(ProfileScreenSpec.route)
//                Toast.makeText(context, "Your trip has been added!", Toast.LENGTH_SHORT).show()
//            })
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