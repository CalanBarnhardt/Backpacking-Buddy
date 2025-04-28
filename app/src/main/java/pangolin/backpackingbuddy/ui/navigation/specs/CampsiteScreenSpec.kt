package pangolin.backpackingbuddy.ui.navigation.specs

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.ui.CampsiteScreen
import pangolin.backpackingbuddy.ui.TrailScreen
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel


object CampsiteScreenSpec : IScreenSpec {
    private const val LOG_TAG = "448.CampsiteScreenSpec"
    private const val ARG_LAT = "lat"
    private const val ARG_LON = "lon"
    private const val ARG_UUID_NAME = "uuid"

    override val route = "campsite_screen/{$ARG_UUID_NAME}/{$ARG_LAT}/{$ARG_LON}"
    override val title = R.string.app_name

    override val arguments = listOf(
        navArgument(ARG_UUID_NAME) { type = NavType.StringType },
        navArgument(ARG_LAT) { type = NavType.StringType },
        navArgument(ARG_LON) { type = NavType.StringType }
    )

    override fun buildRoute(vararg args: String?): String {
        val uuid = args.getOrNull(0) ?: "0"
        val lat = args.getOrNull(1) ?: "0.0"
        val lon = args.getOrNull(2) ?: "0.0"
        return "campsite_screen/$uuid/$lat/$lon"
    }

    @Composable
    override fun Content(
        modifier: Modifier,
        backpackingBuddyViewModel: BackpackingBuddyViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        context: Context
    ) {
        val lat = navBackStackEntry.arguments?.getString(ARG_LAT)?.toDoubleOrNull()
        val lon = navBackStackEntry.arguments?.getString(ARG_LON)?.toDoubleOrNull()

        if (lat == null || lon == null) {
            Log.e("CampsiteScreenSpec", "Missing coordinates!")
            return
        }

        CampsiteScreen(
            viewModel = backpackingBuddyViewModel,
            lat = lat,
            lon = lon
        )
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
        }    }
}