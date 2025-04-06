package pangolin.backpackingbuddy

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import pangolin.backpackingbuddy.ui.navigation.BackpackingBuddyNavHost
import pangolin.backpackingbuddy.ui.navigation.BottomAppBar
import pangolin.backpackingbuddy.ui.theme.BackpackingBuddyTheme
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModel
import pangolin.backpackingbuddy.viewmodel.BackpackingBuddyViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var backpackingBuddyViewModel: BackpackingBuddyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val factory = BackpackingBuddyViewModelFactory(this, lifecycleScope)
        backpackingBuddyViewModel = ViewModelProvider(this, factory)[factory.getViewModelClass()]

        auth = Firebase.auth

        setContent {
            MainActivityContent(backpackingBuddyViewModel = backpackingBuddyViewModel)
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            currentUser.reload()
            backpackingBuddyViewModel.triggerNavigateToHome()
        } else {
            Log.d("MainActivity", "User is not logged in onStart.")
        }
    }
}

@Composable
private fun MainActivityContent(
    backpackingBuddyViewModel: BackpackingBuddyViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        backpackingBuddyViewModel.navigateToHomeEvent.collect {
            Log.d("MainActivityContent", "Collected navigation event. Navigating to home.")
            navController.navigate("trip_explore") {
            }
        }
    }



    BackpackingBuddyTheme {
        // A surface container using the 'background' color from the theme
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomAppBar(
                    navController = navController,
                    backpackingBuddyViewModel = backpackingBuddyViewModel,
                    context = context
                )
            }
        ) { innerPadding ->
            BackpackingBuddyNavHost(
                modifier = Modifier.padding(innerPadding),
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
    //val viewModel = BackpackingBuddyViewModel(TripsRepo.trip)
    //MainActivityContent(backpackingBuddyViewModel = viewModel)
}