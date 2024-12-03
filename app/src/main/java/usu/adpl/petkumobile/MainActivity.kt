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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.navArgument
import androidx.navigation.NavType
import androidx.navigation.NavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
                            MaterialTheme {
                                Surface(color = MaterialTheme.colorScheme.background) {
                                    val navController = rememberNavController()
                                    NavHost(
                                        navController = navController,
                                        startDestination = "pet-care-service-activity-1"
                                    ) {
                                        composable("pet-care-service-activity-1") {
                                            ServiceScreen(navController = navController)
                                        }

                                        composable("pet-boarding") {
                                            PetBoardingScreen(navController = navController)
                                        }
                                        composable("pet-clinic") {
                                            PetClinicScreen(navController = navController)
                                        }
                                        composable("pet-shop") {
                                            PetShopScreen(navController = navController)
                                        }
                                        composable(
                                            "pet-clinic-profile/{nama}/{alamat}/{telepon}/{instagram}/{link}",
                                            arguments = listOf(
                                                navArgument("nama") { type = NavType.StringType },
                                                navArgument("alamat") { type = NavType.StringType },
                                                navArgument("telepon") {
                                                    type = NavType.StringType
                                                },
                                                navArgument("instagram") {
                                                    type = NavType.StringType
                                                },
                                                navArgument("link") { type = NavType.StringType },
                                            )
                                        ) { backStackEntry ->
                                            val nama =
                                                backStackEntry.arguments?.getString("nama") ?: ""
                                            val alamat =
                                                backStackEntry.arguments?.getString("alamat") ?: ""
                                            val telepon =
                                                backStackEntry.arguments?.getString("telepon") ?: ""
                                            val instagram =
                                                backStackEntry.arguments?.getString("instagram")
                                                    ?: ""
                                            val link =
                                                backStackEntry.arguments?.getString("link") ?: ""

                                            PetClinicProfileScreen(
                                                nama,
                                                alamat,
                                                telepon,
                                                instagram,
                                                link,
                                                navController = navController
                                            )
                                        }

                                        // Menambahkan destinasi untuk PetBoardingProfileScreen dengan argumen tambahan
                                        composable(
                                            "pet-boarding-profile/{nama}/{alamat}/{telepon}/{instagram}/{link}",
                                            arguments = listOf(
                                                navArgument("nama") { type = NavType.StringType },
                                                navArgument("alamat") { type = NavType.StringType },
                                                navArgument("telepon") {
                                                    type = NavType.StringType
                                                },
                                                navArgument("instagram") {
                                                    type = NavType.StringType
                                                },
                                                navArgument("link") { type = NavType.StringType },
                                            )
                                        ) { backStackEntry ->
                                            val nama =
                                                backStackEntry.arguments?.getString("nama") ?: ""
                                            val alamat =
                                                backStackEntry.arguments?.getString("alamat") ?: ""
                                            val telepon =
                                                backStackEntry.arguments?.getString("telepon") ?: ""
                                            val instagram =
                                                backStackEntry.arguments?.getString("instagram")
                                                    ?: ""
                                            val link =
                                                backStackEntry.arguments?.getString("link") ?: ""

                                            PetBoardingProfileScreen(
                                                nama,
                                                alamat,
                                                telepon,
                                                instagram,
                                                link,
                                                navController = navController
                                            )
                                        }

                                        // Menambahkan destinasi untuk PetShopProfileScreen dengan argumen tambahan
                                        composable(
                                            "pet-shop-profile/{nama}/{alamat}/{telepon}/{instagram}/{link}",
                                            arguments = listOf(
                                                navArgument("nama") { type = NavType.StringType },
                                                navArgument("alamat") { type = NavType.StringType },
                                                navArgument("telepon") {
                                                    type = NavType.StringType
                                                },
                                                navArgument("instagram") {
                                                    type = NavType.StringType
                                                },
                                                navArgument("link") { type = NavType.StringType },
                                            )
                                        ) { backStackEntry ->
                                            val nama =
                                                backStackEntry.arguments?.getString("nama") ?: ""
                                            val alamat =
                                                backStackEntry.arguments?.getString("alamat") ?: ""
                                            val telepon =
                                                backStackEntry.arguments?.getString("telepon") ?: ""
                                            val instagram =
                                                backStackEntry.arguments?.getString("instagram")
                                                    ?: ""
                                            val link =
                                                backStackEntry.arguments?.getString("link") ?: ""

                                            PetShopProfileScreen(
                                                nama,
                                                alamat,
                                                telepon,
                                                instagram,
                                                link,
                                                navController = navController
                                            )

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
           /* }
        }
    }
}*/