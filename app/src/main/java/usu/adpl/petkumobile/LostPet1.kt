package usu.adpl.petkumobile

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.clickable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.google.firebase.database.FirebaseDatabase
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LostPet1(
    userId: String,
    navController: NavHostController,
    onHomeClick: () -> Unit,
    onReportClick: () -> Unit,
    onAddReportClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lostPets = remember { mutableStateOf<List<LostPet>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }

    // Fetch data from Firebase
    LaunchedEffect(Unit) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("lostPets")
        reference.get().addOnSuccessListener { snapshot ->
            val pets = snapshot.children.mapNotNull { data ->
                val pet = data.getValue(LostPet::class.java)
                pet?.copy(documentId = data.key ?: "")
            }.filter { it.userId == userId } // Filter berdasarkan userId
            lostPets.value = pets
            isLoading.value = false
        }.addOnFailureListener {
            println("Failed to fetch data: ${it.message}")
            isLoading.value = false
        }
    }

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC9A9E2)) // Warna ungu muda
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Tombol back
        IconButton(onClick = { (context as? Activity)?.finish()  }) {
            Icon(
                painter = painterResource(id = R.drawable.back_black),
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        // Header dengan teks dan gambar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Help Find",
                    fontSize = 25.sp,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Lost Pets!",
                    fontSize = 25.sp,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_pet), // Tambahkan gambar anjing dan kucing
                contentDescription = "Pets",
                modifier = Modifier.size(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Tombol Home dan Report
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Tombol Home
            Button(
                onClick = onHomeClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD3D3D3)),
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 200.dp, height = 40.dp)
                    .weight(1f),
                shape = RoundedCornerShape(32.dp)
            ) {
                Text(
                    text = "Home",
                    fontSize = 18.sp,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }

            // Tombol Report
            Button(
                onClick = onReportClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4B9B8)),
                modifier = Modifier
                    .padding(8.dp) // Memberikan jarak antar tombol
                    .clip(RoundedCornerShape(32.dp)) // Mengikuti bentuk tombol
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(32.dp) // Outline bulat sama dengan tombol
                    )
                    .height(40.dp) // Tinggi tombol
                    .width(200.dp) // Lebar tombol
                    .weight(1f)
            ) {
                Text(
                    text = "Report",
                    fontSize = 18.sp,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        // Layout utama putih untuk konten
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            if (isLoading.value) {
                // Show a loading indicator while fetching data
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                if (lostPets.value.isEmpty()) {
                    // Konten utama dengan tombol "Report a Lost Pet"
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(Color.White, shape = MaterialTheme.shapes.large)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                                .clickable(onClick = onAddReportClick),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add),
                                contentDescription = "Add",
                                tint = Color.Gray,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Report a Lost Pet",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    }
                } else {
                    // Display list of lost pets if data exists
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(lostPets.value) { pet ->
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .border(
                                        1.dp,
                                        Color.Gray,
                                        RoundedCornerShape(8.dp)
                                    ), // Border hitam
                                colors = CardDefaults.cardColors(containerColor = Color.White), // Warna putih untuk Card
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    // Gambar Hewan Peliharaan
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1f) // Membuat rasio persegi untuk gambar
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFFFFFFFF)) // Warna latar belakang gambar untuk memberi tepi
                                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        val imageResource = when (pet.petType) {
                                            "Cat" -> R.drawable.cat_default // Ganti dengan ID gambar default kucing
                                            "Dog" -> R.drawable.dog_default // Ganti dengan ID gambar default anjing
                                            else -> throw IllegalArgumentException("Invalid pet type")
                                        }

                                        Image(
                                            painter = painterResource(id = imageResource), // Gambar default berdasarkan jenis pet
                                            contentDescription = "Lost Pet",
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(8.dp) // Memberikan ruang tepi di sekitar gambar
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    // Detail Hewan Peliharaan
                                    Column {
                                        Text(
                                            text = "${pet.name.uppercase()} - ${pet.breed.uppercase()} ${pet.petType.uppercase()}",
                                            fontFamily = customFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.Black
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Text(
                                            text = "Last Seen: ${pet.lastSeenLocation}",
                                            fontFamily = customFontFamily,
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                        Text(
                                            text = "Description: ${pet.colorAndFeatures}",
                                            fontFamily = customFontFamily,
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                        Text(
                                            text = "Reward: ${pet.reward}",
                                            fontFamily = customFontFamily,
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))

                                        // Teks View Details
                                        Text(
                                            text = "[View Details]",
                                            fontFamily = customFontFamily,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 12.sp,
                                            color = Color(0xFF1E88E5),
                                            modifier = Modifier.clickable { navController.navigate("profileLostPet/${pet.documentId}") }
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = if (pet.status == "FOUNDED") Color(0xFFB9F6CA) else Color(0xFFFFBFBF),
                                            shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                                        )
                                        .padding(vertical = 4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = pet.status,
                                        fontFamily = customFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = if (pet.status == "FOUNDED") Color.Green else Color.Red,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        // Tambahkan tombol "Report a Lost Pet" sebagai item terakhir
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                IconButton(
                                    onClick = onAddReportClick,
                                    modifier = Modifier
                                        .size(56.dp) // Ukuran tombol (ikon melingkar)
                                        .clip(RoundedCornerShape(28.dp)) // Membuat tombol berbentuk lingkaran
                                        .background(Color.LightGray), // Warna latar belakang tombol
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_add), // Ganti dengan resource ikon add Anda
                                        contentDescription = "Add Report",
                                        tint = Color.White // Warna ikon
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    }
    }

/*
@Preview(showBackground = true)
@Composable
fun PreviewLostPet1() {
    // Inisialisasi navController
    val navController = rememberNavController()

    LostPet1(
        navController = navController, // Berikan navController
        onHomeClick = { */
/* Aksi tombol Home *//*
 },
        onReportClick = { */
/* Aksi tombol Report *//*
 },
        onAddReportClick = { */
/* Aksi tombol Add Report *//*
 }
    )
}
*/
