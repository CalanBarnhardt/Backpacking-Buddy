package pangolin.backpackingbuddy.ui.navigation.specs

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pangolin.backpackingbuddy.R
import pangolin.backpackingbuddy.ui.profileScreen.ProfileScreen
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel

data object ProfileScreenSpec : IScreenSpec{
    private const val LOG_TAG = "448.ProfileScreenSpec"

    override val route = "profile"
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
        ProfileScreen()
    }
}