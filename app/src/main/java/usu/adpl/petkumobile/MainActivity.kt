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
import usu.adpl.petkumobile.ui.theme.PetkuMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Membuat NavController
            val navController = rememberNavController()

            // Menggunakan NavHost untuk mengelola navigasi
            PetkuMobileTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // Set navigasi di sini
                    NavHost(navController = navController, startDestination = "petScreen") {
                        composable("petScreen") {
                            PetScreen(navController = navController) // Memanggil PetScreen
                        }
                        composable("dogForm") {
                            DogForm(navController = navController) // Memanggil dogForm
                        }
                    }
                }
            }
        }
    }
}
