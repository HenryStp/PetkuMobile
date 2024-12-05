package usu.adpl.petkumobile

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

class LostPet2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation(
            )
        }
    }
}


@Composable
fun LostPet2(
    onHomeClick: () -> Unit,
    onReportClick: () -> Unit,
    onViewDetailsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC9A9E2)) // Background ungu muda
            .padding(16.dp)
    )

    {
        Spacer(modifier = Modifier.height(16.dp))
        // Tombol Back
        IconButton(onClick = { /* Kembali ke halaman sebelumnya */ }) {
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
                onClick = onReportClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD3D3D3)),
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 200.dp, height = 40.dp)
                    .weight(1f),
                shape = RoundedCornerShape(32.dp)
            ){
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
                items(3) { // Ubah sesuai jumlah data yang ada
                    LostPetCard(
                        onViewDetailsClick = onViewDetailsClick // Diteruskan dari LostPet2
                    )
                }
            }
        }
    }
}

@Composable
fun LostPetCard(onViewDetailsClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)), // Menambahkan border hitam
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
                    .background(Color(0xFFFFFFFF))// Warna latar belakang gambar untuk memberi tepi
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sample_pet_image), // Gambar hewan
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
                    text = "LUNA - SIAMESE CAT",
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Location: Jl. Mawar No. 23, Jakarta (0.8 km from you)\n" +
                            "Last Seen: October 3, 2024, at 6:30 PM\n" +
                            "Description: Medium, light brown with dark face, no collar\n" +
                            "• Reward Available",
                    fontFamily = customFontFamily,
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "[View Details]",
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Color(0xFF1E88E5),
                    modifier = Modifier.clickable { onViewDetailsClick() }
                )
            }
        }

        // Label "STILL MISSING" di bagian bawah kartu
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFBFBF), RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                .padding(vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "STILL MISSING",
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLostPet2() {
    // Memberikan nilai untuk onHomeClick dan onReportClick di preview
    LostPet2(
        onHomeClick = { /* Tambahkan aksi untuk tombol Home di preview */ },
        onReportClick = { /* Tambahkan aksi untuk tombol Report di preview */ },
        onViewDetailsClick = {}
    )
}
