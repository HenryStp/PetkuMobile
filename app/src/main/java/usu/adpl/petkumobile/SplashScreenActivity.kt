package usu.adpl.petkumobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.tooling.preview.Preview


class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen()
        }

        // Redirect to SplashScreen2Activity after a delay
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000L) // Splash screen delay in milliseconds
            startActivity(Intent(this@SplashActivity, SplashScreen2Activity::class.java))
            finish()
        }
    }
}


@Composable
@Preview(showBackground = true)
fun SplashScreen() {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFFFFF),    // Putih
            Color(0xFFF4B9B8),    // #F4B9B8
            Color(0xFF703E98)     // #703E98
        ),
        startY = 800.0f,
        endY = 2400.0f
    )
    val CustomFontFamily = FontFamily(
        Font(R.font.sono_regular, FontWeight.Normal),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo), // Add your logo drawable here
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "PetKu",
                fontSize = 50.sp,
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF8E5E9E)
            )
        }
    }
}
