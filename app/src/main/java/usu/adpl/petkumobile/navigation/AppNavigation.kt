package usu.adpl.petkumobile

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "lostpet2") {
        composable("lostpet2") {
            LostPet2(
                onHomeClick = { /* Tidak perlu navigasi, tetap di halaman yang sama */ },
                onReportClick = { navController.navigate("lostpet1") } ,// Pergi ke LostPet1
                onViewDetailsClick = { navController.navigate("lostpetdetail") } // Navigasi ke LostPetDetail
            )
        }
        composable("lostpet1") {
            LostPet1(
                onHomeClick = { navController.navigate("lostpet2") },
                onReportClick = { /*no nav*/ },
                onAddReportClick = { navController.navigate("formlostpet") }
            )
        }
        composable("formlostpet") {
            FormLostPet(navController = navController) // Halaman FormLostPet
        }
        composable("lostpetdetail") {
            LostPetDetail(navController = navController) // Halaman LostPetDetail
        }
    }
}


