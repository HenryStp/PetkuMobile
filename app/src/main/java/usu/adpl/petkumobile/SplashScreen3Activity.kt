package usu.adpl.petkumobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import android.content.Intent
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.fontResource


class SplashScreen3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen3(onNavigateSkip = { navigateToCreateActivity() }, onNavigateNext = { navigateToSplashScreen4() })
        }
    }

    private fun navigateToCreateActivity() {
        val intent = Intent(this, CreateActivity::class.java)
        startActivity(intent)
        finish() // Optional, jika ingin menutup SplashScreen3Activity setelah navigasi
    }

    private fun navigateToSplashScreen4() {
        val intent = Intent(this, SplashScreen4Activity::class.java)
        startActivity(intent)
        finish() // Optional, jika ingin menutup SplashScreen3Activity setelah navigasi
    }
}

@Composable
@Preview(showBackground = true)
fun SplashScreen3(onNavigateSkip: () -> Unit = {}, onNavigateNext: () -> Unit = {}) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFFFFF),    // Putih
            Color(0xFFF4B9B8),    // #F4B9B8
            Color(0xFF703E98)     // #703E98
        ),
        startY = 900.0f,
        endY = 2500.0f
    )
    val CustomFontFamily = FontFamily(
        Font(R.font.sen_reguler, FontWeight.Normal),
        Font(R.font.sen_medium, FontWeight.Medium),
        Font(R.font.sen_medium, FontWeight.Normal),
        Font(R.font.sen_bold, FontWeight.Bold),
        Font(R.font.sen_extrabold, FontWeight.ExtraBold)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = buildAnnotatedString {
                    append("With Petku's ")
                    withStyle(style = SpanStyle(color = Color(0xFF6A0DAD))) {
                        append("reminder\n")
                    }
                    append(" feature, you'll never miss\n a shot")
                },
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFFF4B9B8),
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Bold
            )


            Spacer(modifier = Modifier.height(16.dp))

            // Indicator dots
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                DotIndicator2(isSelected = false)
                Spacer(modifier = Modifier.width(12.dp))
                DotIndicator2(isSelected = true)
                Spacer(modifier = Modifier.width(12.dp))
                DotIndicator2(isSelected = false)
            }

            Spacer(modifier = Modifier.weight(1f))

            // Skip and next button at the bottom
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onNavigateSkip) {
                    Text(text = "Skip", color = Color.DarkGray)
                }

                // Menggunakan FloatingActionButton untuk tombol Next berbentuk lingkaran hitam
                FloatingActionButton(
                    onClick = onNavigateNext,
                    containerColor = Color.Black,
                    shape = CircleShape, // Membuat latar belakang berbentuk lingkaran
                    modifier = Modifier.size(50.dp) // Mengatur ukuran lingkaran tombol
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Next",
                        tint = Color.White // Mengatur warna ikon menjadi putih
                    )
                }
            }

        }
    }
}

@Composable
fun DotIndicator2(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .size(if (isSelected) 7.dp else 5.dp)
            .background(
                color = if (isSelected) Color.Black else Color.Gray,
                shape = CircleShape
            )
    )
}