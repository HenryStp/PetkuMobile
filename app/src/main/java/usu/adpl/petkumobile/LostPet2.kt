package usu.adpl.petkumobile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.border
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import androidx.navigation.NavController
import androidx.navigation.NavHostController


class LostPet2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getStringExtra("userId") ?: ""
        setContent {
            AppNavigation(
                userId = userId
            )
        }
    }
}

@Composable
fun LostPet2(
    userId: String,
    navController: NavController,  // Tambahkan parameter ini
    onHomeClick: () -> Unit,
    onReportClick: (String) -> Unit,
    onViewDetailsClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Referensi ke Realtime Database Firebase
    val database = FirebaseDatabase.getInstance()
    val lostPetsRef = database.getReference("lostPets")

    val lostPets = remember { mutableStateListOf<LostPet>() }


    LaunchedEffect(Unit) {
        lostPetsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lostPets.clear()
                for (postSnapshot in snapshot.children) {
                    val lostPet = postSnapshot.getValue(LostPet::class.java)
                    if (lostPet != null && lostPet.userId != userId) { // Filter: userId harus berbeda
                        lostPets.add(lostPet)
                    }
                }
                // Debugging log
                /*Log.d("LostPet2", "Filtered LostPets: $lostPets")*/
            }

            override fun onCancelled(error: DatabaseError) {
                // Tangani kesalahan jika diperlukan
            }
        })
    }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC9A9E2)) // Background ungu muda
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        // Tombol back
        IconButton(onClick =
        { (context as? Activity)?.finish()  }) {
            Icon(
                painter = painterResource(id = R.drawable.back_black),
                contentDescription = "Back",
                tint = Color.Black
            )
        }


        // Header dengan teks
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
                painter = painterResource(id = R.drawable.ic_pet), // Gambar anjing dan kucing
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
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4B9B8)),
                shape = RoundedCornerShape(32.dp),
                contentPadding = PaddingValues(0.dp), // Menghilangkan padding default
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
                    text = "Home",
                    fontSize = 18.sp,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            // Tombol Report
            Button(
                onClick = { onReportClick(userId) }, // Kirim userId
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD3D3D3)),
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 200.dp, height = 40.dp)
                    .weight(1f),
                shape = RoundedCornerShape(32.dp)
            ) {
                Text(
                    text = "Report",
                    fontSize = 18.sp,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
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
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(lostPets.size) { index ->
                    LostPetCard(
                        documentId = lostPets[index].documentId,
                        lostPet = lostPets[index],
                        onViewDetailsClick = { documentId ->
                            navController.navigate("lostpetdetail/$documentId")
                        },
                        navController = navController  // Pastikan ini disediakan sebagai parameter
                    )
                }
            }
        }
    }
}

@Composable
fun LostPetCard(
    documentId: String,
    lostPet: LostPet,
    onViewDetailsClick: (String) -> Unit,
    navController: NavController
) {

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(2.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                val imageResource = when (lostPet.petType) {
                    "Cat" -> R.drawable.cat_default // Ganti dengan ID gambar default kucing
                    "Dog" -> R.drawable.dog_default // Ganti dengan ID gambar default anjing
                    else -> throw IllegalArgumentException("Invalid pet type")
                }

                Image(
                    painter = painterResource(id = imageResource), // Gambar default berdasarkan jenis pet
                    contentDescription = "Lost Pet",
                    modifier = Modifier
                        .fillMaxSize() // Mengatur ukuran maksimal gambar di dalam box
                        .aspectRatio(1f)

                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                Text(
                    text = "${lostPet.name} - ${lostPet.breed}",
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Location: ${lostPet.lastSeenLocation}\n" +
                            "Description: ${lostPet.colorAndFeatures}\n" +
                            "• Reward Available",
                    fontFamily = customFontFamily,
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "[View Details]",
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Color(0xFF1E88E5),
                    modifier = Modifier.clickable { navController.navigate("lostPetDetail/${documentId}") }
                )

            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = if (lostPet.status == "FOUNDED") Color(0xFFB9F6CA) else Color(0xFFFFBFBF),
                    shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                )
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = lostPet.status,
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = if (lostPet.status == "FOUNDED") Color.Green else Color.Red,
                textAlign = TextAlign.Center
            )
        }

    }
}



/*
@Preview(showBackground = true)
@Composable
fun PreviewLostPet2() {
    // Memberikan nilai untuk onHomeClick dan onReportClick di preview
    LostPet2(
        onHomeClick = { *//* Tambahkan aksi untuk tombol Home di preview *//* },
        onReportClick = { *//* Tambahkan aksi untuk tombol Report di preview *//* },
        onViewDetailsClick = {}
    )
}*/
