package usu.adpl.petkumobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import usu.adpl.petkumobile.ui.theme.PetkuMobileTheme


val CustomFontFamily = FontFamily(
    Font(R.font.sen_reguler, FontWeight.Normal),
    Font(R.font.sen_medium, FontWeight.Medium),
    Font(R.font.sen_medium, FontWeight.Normal),
    Font(R.font.sen_bold, FontWeight.Bold),
    Font(R.font.sen_extrabold, FontWeight.ExtraBold)
)
class PetServiceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetkuMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
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


@Composable
fun ServiceScreen(navController: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_black),
                contentDescription = "Back",
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterStart)
                    .clickable {
                        navController?.popBackStack() }
            )

            Text(
                text = "Service",
                fontSize = 25.sp,
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }

        ServiceOption(
            title = "Pet\nBoarding",
            backgroundColor = Color(0xFFFFD7C2),
            imageId = R.drawable.pet_boarding,
            imageSize = 150,
            imageOffsetX = 10,
            imageOffsetY = -10,
            padding = PaddingValues(bottom = 8.dp),
            onClick = { navController?.navigate("pet-boarding") }
        )

        ServiceOption(
            title = "Pet Clinic/\nGrooming",
            backgroundColor = Color(0xFFF8C2C2),
            imageId = R.drawable.pet_clinic,
            imageSize = 125,
            imageOffsetX = 0,
            imageOffsetY = -8,
            padding = PaddingValues(bottom = 25.dp),
            onClick = { navController?.navigate("pet-clinic") }
        )

        ServiceOption(
            title = "Pet Shop",
            backgroundColor = Color(0xFFC2E8D6),
            imageId = R.drawable.pet_shop,
            imageSize = 120,
            imageOffsetX = -15,
            imageOffsetY = -7,
            onClick = { navController?.navigate("pet-shop") }
        )
    }
}

@Composable
fun ServiceOption(
    title: String,
    backgroundColor: Color,
    imageId: Int,
    fontSize: TextUnit = 23.sp,
    fontFamily: FontFamily = CustomFontFamily,
    fontWeight: FontWeight = FontWeight.ExtraBold,
    imageSize: Int = 100,
    imageOffsetX: Int = -10,
    imageOffsetY: Int = -20,
    padding: PaddingValues = PaddingValues(0.dp),
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(padding)
            .padding(1.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(293.dp)
                .height(109.dp)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 40.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 40.dp
                    )
                )
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = fontSize,
                fontFamily = fontFamily,
                fontWeight = fontWeight,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
        Image(
            painter = painterResource(id = imageId),
            contentDescription = title,
            modifier = Modifier
                .size(imageSize.dp)
                .align(Alignment.TopEnd)
                .offset(x = imageOffsetX.dp, y = imageOffsetY.dp)
                .zIndex(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewServiceScreen() {
    ServiceScreen()
}