package pangolin.backpackingbuddy.ui.navigation.specs

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel
import androidx.compose.material3.BottomAppBar
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme


sealed interface IScreenSpec {
    companion object {
        private const val LOG_TAG = "448.IScreenSpec"

        val allScreens = IScreenSpec::class.sealedSubclasses.associate {
            Log.d(LOG_TAG, "allScreens: mapping route \"${it.objectInstance?.route ?: ""}\" to object \"${it.objectInstance}\"")
            it.objectInstance?.route to it.objectInstance
        }
        const val ROOT = "backpackingbuddy"
        val startDestination = LoginScreenSpec.route

        @Composable
        @JvmStatic
        fun BottomBar(backpackingBuddyViewModel: BackpackingBuddyViewModel, navController: NavHostController,
                   backStackEntry: NavBackStackEntry?, context: Context) {
            val route = backStackEntry?.destination?.route ?: ""
            allScreens[route]?.BottomAppBarContent(backpackingBuddyViewModel, navController, backStackEntry, context)
        }
    }

    val route: String
    val arguments: List<NamedNavArgument>
    fun buildRoute(vararg args: String?): String
    val title: Int

    @Composable
    fun Content(
        modifier: Modifier,
        backpackingBuddyViewModel: BackpackingBuddyViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        context: Context
    )

    @Composable
    private fun BottomAppBarContent(backpackingBuddyViewModel: BackpackingBuddyViewModel, navController: NavHostController,
                                 backStackEntry: NavBackStackEntry?, context: Context) {
        BottomAppBar(
            modifier = Modifier.height(80.dp).background(MaterialTheme.colorScheme.surface),
            actions = {
                BottomAppBarActions(
                    backpackingBuddyViewModel = backpackingBuddyViewModel,
                    navController = navController,
                    backStackEntry = backStackEntry,
                    context = context
                )
            },
            floatingActionButton = {}
        )

//        TopAppBar(
//            navigationIcon = if (navController.previousBackStackEntry != null) {
//                {
//                    IconButton(onClick = { navController.navigateUp() }) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "description"
//                        )
//                    }
//                }
//            } else {
//                { }
//            },
//            title = {
//                Text(text = stringResource(id = title))
//            },
//            actions = {
//                BottomAppBarActions(
//                    backpackingBuddyViewModel = backpackingBuddyViewModel,
//                    navController = navController,
//                    backStackEntry = backStackEntry,
//                    context = context
//                )
//            }
//        )
    }

    @Composable
    fun BottomAppBarActions(backpackingBuddyViewModel: BackpackingBuddyViewModel, navController: NavHostController,
                         backStackEntry: NavBackStackEntry?, context: Context)
}