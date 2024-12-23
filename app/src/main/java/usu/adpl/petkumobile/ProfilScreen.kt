package usu.adpl.petkumobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.FirebaseApp
import usu.adpl.petkumobile.ui.theme.PetkuMobileTheme

class ProfilScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi Firebase
        FirebaseApp.initializeApp(this)
        // Inisialisasi Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()
        database.reference

        setContent {
            // Membuat NavController
            val userId = intent.getStringExtra("user_id") ?: "Unknown User"
            val navController = rememberNavController()

            // Menggunakan NavHost untuk mengelola navigasi
            PetkuMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Set navigasi di sini
                    NavHost(navController = navController, startDestination = "PetScreen") {
                        composable("PetScreen") {
                            PetScreen(navController = navController)
                        }
                        composable("dogForm/{userId}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId") ?: "Unknown User"
                            DogForm(navController = navController, userId = userId)
                        }
                        composable("catForm/{userId}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId") ?: "Unknown User"
                            CatForm(navController = navController, userId = userId)
                        }
                        composable("petSaved/{userId}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId") ?: "Unknown User"
                            CatForm(navController = navController, userId = userId)
                        }
                        composable("displayPet/{userId}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId") ?: "Unknown User"
                            DisplayPet(navController = navController, userId = userId)
                        }
                    }
                }
            }
        }
    }
}