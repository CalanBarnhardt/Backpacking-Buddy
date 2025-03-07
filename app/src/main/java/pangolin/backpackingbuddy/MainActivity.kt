package pangolin.backpackingbuddy

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import pangolin.backpackingbuddy.data.TripsRepo
import pangolin.backpackingbuddy.ui.navigation.BackpackingBuddyBottomBar
import pangolin.backpackingbuddy.ui.navigation.BackpackingBuddyNavHost
import pangolin.backpackingbuddy.ui.theme.BackpackingBuddyTheme
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val backpackingBuddyViewModel = BackpackingBuddyViewModel(TripsRepo.trip)

        setContent {
            MainActivityContent(backpackingBuddyViewModel = backpackingBuddyViewModel)
        }
    }
}

@Composable
private fun MainActivityContent(
    backpackingBuddyViewModel: BackpackingBuddyViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    BackpackingBuddyTheme {
        // A surface container using the 'background' color from the theme
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            BackpackingBuddyNavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                backpackingBuddyViewModel = backpackingBuddyViewModel,
                context = context
            )
            BackpackingBuddyBottomBar(
                navController = navController,
                backpackingBuddyViewModel = backpackingBuddyViewModel,
                context = context
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    val viewModel = BackpackingBuddyViewModel(TripsRepo.trip)
    MainActivityContent(backpackingBuddyViewModel = viewModel)
}