package pangolin.backpackingbuddy.ui.navigation.specs

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel

sealed interface IScreenSpec {
    companion object {
        private const val LOG_TAG = "448.IScreenSpec"

        val allScreens = IScreenSpec::class.sealedSubclasses.associate {
            Log.d(LOG_TAG, "allScreens: mapping route \"${it.objectInstance?.route ?: ""}\" to object \"${it.objectInstance}\"")
            it.objectInstance?.route to it.objectInstance
        }
        const val ROOT = "backpackingbuddy"
        val startDestination = "" // TODO
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
}