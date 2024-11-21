import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.ui.tooling.preview.Preview


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendarScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainCalendarScreen() {
    val date = LocalDate.of(2020, 12, 7)
    val formattedDate = date.format(DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy"))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDFF1EB)) // Warna latar belakang sesuai gambar
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formattedDate,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Item Walk Time
        CalendarItem(
            time = "09.00 AM - 10.00 AM",
            title = "Walk Time",
            description = "Let's Get Moving!"
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Item Bath Time
        CalendarItem(
            time = "10.30 AM - 11.00 AM",
            title = "Bath Time",
            description = "Keep them clean and happy!"
        )
    }
}

@Composable
fun MainCalendarItem(time: String, title: String, description: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = time,
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Start
            )
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
