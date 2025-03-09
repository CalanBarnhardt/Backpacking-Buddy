package pangolin.backpackingbuddy.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import pangolin.backpackingbuddy.ui.navigation.specs.IScreenSpec
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel

@Composable
fun BottomAppBar(navController: NavHostController, backpackingBuddyViewModel: BackpackingBuddyViewModel, context: Context) {
    val navBackStackEntryState = navController.currentBackStackEntryAsState()
    IScreenSpec.BottomBar(
        backpackingBuddyViewModel = backpackingBuddyViewModel,
        navController = navController,
        backStackEntry = navBackStackEntryState.value,
        context = context
    )
}