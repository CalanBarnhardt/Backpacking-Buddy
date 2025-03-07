package pangolin.backpackingbuddy.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import pangolin.backpackingbuddy.ui.navigation.specs.IScreenSpec
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel

@Composable
fun BackpackingBuddyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    backpackingBuddyViewModel: BackpackingBuddyViewModel,
    context: Context
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = IScreenSpec.ROOT
    ) {
        navigation(
            route = IScreenSpec.ROOT,
            startDestination = IScreenSpec.startDestination
        ) {
            IScreenSpec.allScreens.forEach { (_, screen) ->
                if(screen != null) {
                    composable(
                        route = screen.route,
                        arguments = screen.arguments
                    ) { navBackStackEntry ->
                        screen.Content(
                            modifier = modifier,
                            navController = navController,
                            navBackStackEntry = navBackStackEntry,
                            backpackingBuddyViewModel = backpackingBuddyViewModel,
                            context = context
                        )
                    }
                }
            }
        }
    }
}