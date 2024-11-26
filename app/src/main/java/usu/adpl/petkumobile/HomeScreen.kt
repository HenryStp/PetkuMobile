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

@Composable
fun HomeScreen(onLostPetClick: () -> Unit) {
    // Latar belakang dan tata letak tombol
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3C0E8)), // Warna latar belakang ungu muda
        contentAlignment = Alignment.Center // Konten berada di tengah layar
    ) {
        Button(
            onClick = onLostPetClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)), // Warna ungu
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .size(width = 200.dp, height = 60.dp) // Ukuran tombol
        ) {
            Text(
                text = "Lost Pet",
                color = Color.White,
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(onLostPetClick = { /* Navigasi ke halaman Lost Pet */ })
}
