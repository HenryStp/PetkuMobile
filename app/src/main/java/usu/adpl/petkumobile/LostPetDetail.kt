package usu.adpl.petkumobile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import usu.adpl.petkumobile.viewmodel.LostPetViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import usu.adpl.petkumobile.customFontFamily
import com.google.firebase.database.FirebaseDatabase
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun LostPetDetail(documentId: String, navController: NavHostController, viewModel: LostPetViewModel = viewModel()) {
    val lostPet = viewModel.lostPetData.collectAsState().value
    val isPetFounded = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.fetchLostPetData(documentId)
    }

    // Observasi data yang sudah diurutkan berdasarkan timestamp terbaru


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFC9A9E2)) // Latar belakang ungu muda
            .padding(16.dp)
    )
    {
        Spacer(modifier = Modifier.height(16.dp))
        if (lostPet != null) {
            // Bagian gambar dan ikon back
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                // Gambar hewan peliharaan
                Image(
                    painter = painterResource(
                        id = if (lostPet.petType.equals("cat", ignoreCase = true)) {
                            R.drawable.cat_default // Ganti dengan resource gambar kucing Anda
                        } else if (lostPet.petType.equals("dog", ignoreCase = true)) {
                            R.drawable.dog_default // Ganti dengan resource gambar anjing Anda
                        } else {
                            throw IllegalArgumentException("Invalid pet type")
                        }
                    ),
                    contentDescription = "Lost Pet",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                )


                // Ikon Back di atas gambar
                IconButton(
                    onClick = {navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.TopStart) // Menempatkan ikon di pojok kiri atas
                        .padding(16.dp) // Menambahkan padding dari tepi gambar
                        .zIndex(1f) // Menjamin ikon berada di atas gambar
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_black),
                        contentDescription = "Back",
                        tint = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Nama dan status
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "${lostPet.name.uppercase()}",
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isPetFounded.value) "FOUNDED" else "STILL MISSING",
                    color = if (isPetFounded.value) Color.Green else Color.Red,
                    fontSize = 14.sp,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isPetFounded.value) Color(0xFFB9F6CA) else Color(0xFFFFBFBF),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(vertical = 4.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Informasi detail hewan
                ProfileSection("Type:", lostPet.petType)
                ProfileSection("Breed:", lostPet.breed)
                ProfileSection("Age:", lostPet.age)
                ProfileSection("Weight:", lostPet.weight)
                ProfileSection("Height:", lostPet.height)
                ProfileSection("Gender:", lostPet.gender)
                ProfileSection("Color & Features:", lostPet.colorAndFeatures)
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Last Seen Information",
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                ProfileSection("Last Seen Location:", lostPet.lastSeenLocation)
                ProfileSection("Date & Time Lost:", lostPet.dateTimeLost)


                // Informasi kepribadian
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Additional Information:",
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = lostPet.additionalInfo,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )

                // Informasi kontak pemilik
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Owner's Contact",
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                ProfileSection("Name:", lostPet.ownerName)
                ProfileSection("Phone:", lostPet.ownerPhone)
                ProfileSection("Email:", lostPet.ownerEmail)
                ProfileSection("Social Media:", lostPet.ownerInstagram)


                // Informasi hadiah
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Reward",
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = lostPet.reward,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))




                @Composable
                fun LostPetDetail(navController: NavHostController) {
                    val lostPets = listOf("doc1", "doc2", "doc3") // Data dummy
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        lostPets.forEach { documentId ->
                            Button(
                                onClick = { navController.navigate("profileLostPet/$documentId") },
                                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                            ) {
                                Text(text = "View Report for $documentId")
                            }
                        }
                    }
                }

            }
        }
    }
}



/*@Preview(showBackground = true)
@Composable
fun PreviewProfileLostPet() {
    val navController = rememberNavController() // Dummy NavController untuk preview
    ProfileLostPet(
        documentId = "exampleDocumentId", // ID dokumen contoh untuk testing
        navController = navController // Berikan NavController ke ProfileLostPet
    )
}*/


