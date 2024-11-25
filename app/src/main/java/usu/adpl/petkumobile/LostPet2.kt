package usu.adpl.petkumobile

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

@Composable
fun LostPet2(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3C0E8)) // Background ungu muda
            .padding(16.dp)
    )

    {
        Spacer(modifier = Modifier.height(16.dp))
        // Tombol Back
        IconButton(onClick = { /* Kembali ke halaman sebelumnya */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
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
                    fontSize = 24.sp,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Lost Pets!",
                    fontSize = 24.sp,
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

        Spacer(modifier = Modifier.height(24.dp))

        // Tombol Home dan Report
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Tombol Home
            Button(
                onClick = { /* Navigasi ke halaman Home */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCDD2)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Home",
                    fontSize = 16.sp,
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }

            // Tombol Report
            Button(
                onClick = { /* Navigasi ke halaman Report */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Report",
                    fontSize = 16.sp,
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
                        onViewDetailsClick = { /* Navigasi ke halaman detail */ }
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
            .padding(8.dp),
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
                    .background(Color(0xFFFFF3E0)), // Warna latar belakang gambar untuk memberi tepi
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
    LostPet2()
}
