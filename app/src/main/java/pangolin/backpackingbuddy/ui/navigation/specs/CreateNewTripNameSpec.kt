package pangolin.backpackingbuddy.ui.navigation.specs

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.ui.createTrip.CreateNewTripName
import pangolin.backpackingbuddy.ui.loginScreen.LoginScreen
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel


data object CreateNewTripNameSpec : IScreenSpec{
    private const val LOG_TAG = "448.CreateNewTripNameSpec"

    override val route = "trip-name"
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
        CreateNewTripName(onClick = {navController.navigate("trip-date")})
    }

    @Composable
    override fun BottomAppBarActions(
        backpackingBuddyViewModel: BackpackingBuddyViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry?,
        context: Context
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                navController.navigate(ProfileScreenSpec.route)
            }) {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "navigating"
                )
            }

            Spacer(Modifier.padding(65.dp))

            IconButton(onClick = {
                navController.navigate(ExploreScreenSpec.route)
            }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "navigating"
                )
            }
        }
    }
}