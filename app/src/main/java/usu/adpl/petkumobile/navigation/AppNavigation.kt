package usu.adpl.petkumobile

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation(userId: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "LostPet2/$userId") {
        composable("LostPet2/{userId}") { backStackEntry ->
            val currentUserId = backStackEntry.arguments?.getString("userId") ?: ""
            LostPet2(
                userId = currentUserId,
                navController = navController,
                onHomeClick = { /* Tetap di halaman yang sama */ },
                onReportClick = { userId ->
                    navController.navigate("LostPet1/$userId")
                },
                onViewDetailsClick = { documentId ->
                    navController.navigate("LostPetDetail/$documentId")
                }
            )
        }

        composable(
            route = "LostPet1/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            LostPet1(
                userId = userId,
                navController = navController,
                onHomeClick = { navController.navigate("LostPet2/$userId")  },
                onReportClick = { /* Tetap di halaman yang sama */ },
                onAddReportClick = { navController.navigate("formlostpet/$userId")  }
            )
        }

        composable("formlostpet/{userId}") { backStackEntry ->
            val currentUserId = backStackEntry.arguments?.getString("userId") ?: ""
            FormLostPet(
                navController = navController,
                onSubmitClick = { documentId ->
                    navController.navigate("profileLostPet/$documentId") {
                        popUpTo("lostpet1/$currentUserId") { inclusive = false }
                    }
                },
                userId = currentUserId // Menyertakan userId ke dalam FormLostPet
            )
        }

        composable("LostPetDetail/{documentId}") { navBackStackEntry ->
            val documentId = navBackStackEntry.arguments?.getString("documentId")?:""
            LostPetDetail(navController = navController, documentId = documentId)

        }

        composable("lostPetList") {
            LostPetListScreen(navController = navController)
        }
        composable("profileLostPet/{documentId}") { backStackEntry ->
            val documentId = backStackEntry.arguments?.getString("documentId") ?: ""
            ProfileLostPet(documentId = documentId, navController = navController)
        }
    }
}
