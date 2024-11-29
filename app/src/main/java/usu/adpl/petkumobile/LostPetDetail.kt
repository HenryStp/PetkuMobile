package usu.adpl.petkumobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController

@Composable
fun LostPetDetail(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFC9A9E2)) // Latar belakang ungu muda
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
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
                onClick = { navController.popBackStack() },
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
                .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "MAX - SPHYNX CAT",
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
            ProfileSection("Type:", "Cat")
            ProfileSection("Breed:", "Siamese")
            ProfileSection("Age:", "2 years old")
            ProfileSection("Size:", "Medium")
            ProfileSection("Gender:", "Female")
            ProfileSection(
                "Distinctive Features:",
                "Light brown body with dark brown face, paws, and tail. Blue eyes, no collar."
            )

            // Informasi lokasi terakhir
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Last Seen Information:",
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            ProfileSection("Last Seen Location:", "Jl. Mawar No. 23, Jakarta")
            ProfileSection("Date Lost:", "October 3, 2024, at 6:30 PM")
            ProfileSection("Possible Sighting Locations:", "Jl. Cempaka (near a park) on October 4, 2024.")
            ProfileSection("Reported by:", "Aisyah (+62 812-9876-5432).")

            // Informasi kepribadian
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Personality & Behavior:",
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Luna is a very shy and quiet cat. She tends to hide in small spaces and avoid loud noises. She’s not likely to approach strangers and may run if someone tries to come too close.",
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp
            )

            // Informasi kontak pemilik
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Owner's Contact:",
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            ProfileSection("Owner:", "Michael Davis")
            ProfileSection("Phone:", "+62 813-5678-1234")
            ProfileSection("Email:", "michael.davis@email.com")
            ProfileSection("Social Media:", "Instagram @michael.davis")

            // Informasi hadiah
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Reward Information:",
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "A reward of IDR 500,000 is offered for Luna’s safe return. Please contact Michael if you see her or have any information.",
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLostPetDetail() {
    LostPetDetail()
}

fun LostPetDetail() {
    TODO("Not yet implemented")
}

