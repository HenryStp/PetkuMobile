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
import usu.adpl.petkumobile.LostPet
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ProfileLostPet(documentId: String, viewModel: LostPetViewModel = viewModel()) {
//    val database = FirebaseDatabase.getInstance()
//    val reference = database.getReference("lostPets").child(documentId)
//    val lostPetState = remember { mutableStateOf<LostPet?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchLostPetData(documentId)
    }

    // Observasi data
    val lostPet = viewModel.lostPetData.collectAsState().value

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
                    painter = painterResource(id = R.drawable.sample_pet_image), // Gambar hewan
                    contentDescription = "Lost Pet",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                )

                // Ikon Back di atas gambar
                IconButton(
                    onClick = { /* Kembali ke halaman sebelumnya */ },
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
                    text = "${lostPet.name}",
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "STILL MISSING",
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFBFBF), RoundedCornerShape(8.dp))
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


                // Tombol Edit dan Found dalam satu baris
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp) // Spasi antar tombol
                ) {
                    // Tombol Edit
                    Button(
                        onClick = { /* Logic untuk Edit */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF)),
                        shape = RoundedCornerShape(32.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(32.dp))
                            .border(
                                width = 2.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(32.dp) // Outline bulat sama dengan tombol
                            )
                            .height(40.dp) // Tinggi tombol
                            .width(200.dp) // Lebar tombol
                    ) {
                        Text( text = "EDIT", color = Color.Black, fontFamily = customFontFamily,
                            fontWeight = FontWeight.Bold,)
                    }

                    // Tombol Found
                    Button(
                        onClick = { /* Logic untuk Found */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784)),
                        shape = RoundedCornerShape(32.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(32.dp))
                            .border(
                                width = 2.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(32.dp) // Outline bulat sama dengan tombol
                            )
                            .height(40.dp) // Tinggi tombol
                            .width(200.dp) // Lebar tombol
                    ) {
                        Text( text = "FOUNDED", color = Color.Black, fontFamily = customFontFamily,
                            fontWeight = FontWeight.Bold,)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

    @Composable
    fun ProfileSection(label: String, value: String) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            Text(
                text = label,
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }

@Composable
fun LostPetListScreen(navController: NavHostController) {
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



    @Preview(showBackground = true)
    @Composable
    fun PreviewProfileLostPet() {
        ProfileLostPet(
            documentId = "exampleDocumentId", // Atur ID sesuai kebutuhan untuk testing

        )
    }

