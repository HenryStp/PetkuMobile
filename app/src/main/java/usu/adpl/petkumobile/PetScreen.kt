package usu.adpl.petkumobile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun PetScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Background putih di luar kotak hijau
    ) {
        // Kotak hijau pastel
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 115.dp)
                .background(
                    color = Color(0xFFD2E8E1), // Warna hijau pastel
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
        ) {
            IconButton(
                onClick = { navController.navigateUp() }, // Navigasi ke halaman sebelumnya
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Back",
                    modifier = Modifier.size(25.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // InteractionSource untuk menangkap hover
                    val dogInteractionSource = remember { MutableInteractionSource() }
                    val catInteractionSource = remember { MutableInteractionSource() }
                    val isHoveredDog by dogInteractionSource.collectIsHoveredAsState()
                    val isHoveredCat by catInteractionSource.collectIsHoveredAsState()

                    // Tombol Dog
                    Button(
                        onClick = { navController.navigate("dogForm") },
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isHoveredDog) Color(0xFFFFC0CB) else Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Black),
                        interactionSource = dogInteractionSource // Tambahkan InteractionSource
                    ) {
                        Text(text = "Dog", color = Color.Black)
                    }

                    // Tombol Cat
                    Button(
                        onClick = { navController.navigate("catForm") },
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isHoveredCat) Color(0xFFFFC0CB) else Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Black),
                        interactionSource = catInteractionSource // Tambahkan InteractionSource
                    ) {
                        Text(text = "Cat", color = Color.Black)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PetScreenPreview() {
    PetScreen(navController = rememberNavController())
}
