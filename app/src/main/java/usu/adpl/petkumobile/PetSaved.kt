package usu.adpl.petkumobile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ProfileSaved(navController: NavController) {
    // Gunakan Box untuk memusatkan konten di layar
    Box(
        modifier = Modifier
            .wrapContentSize() // Membuat ukuran Box sesuai dengan ukuran konten, bukan layar penuh
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp), // Padding untuk Card
            shape = RoundedCornerShape(30.dp),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            // Kolom dengan latar belakang putih diterapkan hanya pada card
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .background(Color.White) // Latar belakang putih diterapkan hanya pada konten dalam Card
                    .padding(32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.saved), // Pastikan 'saved.png' ada di 'res/drawable'
                    contentDescription = "Saved Icon",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Profile already saved!",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { navController.navigate("displayPet") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4B9B8)), // Warna tombol
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    // Warna teks pada tombol diatur menjadi hitam
                    Text(
                        text = "Go to Profile",
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileSaved() {
    val navController = rememberNavController()
    ProfileSaved(navController = navController)
}

