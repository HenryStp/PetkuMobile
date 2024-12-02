package usu.adpl.petkumobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate

class CalendarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScheduleView()
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ScheduleView() {
    var title by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    val calendarState = rememberSheetState()
    val clockState = rememberSheetState()
    val context = LocalContext.current

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.MONTH,
            disabledDates = listOf(LocalDate.now().minusDays(3))
        ),
        selection =CalendarSelection.Date{date ->
            Log.d("Selected Date", "$date")
        } )

    ClockDialog(
        state = clockState,
        config = ClockConfig(is24HourFormat = false),
        selection = ClockSelection.HoursMinutes {hours,minutes ->
            Log.d("Selected Time", "$hours:$minutes")
        }
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp)
        .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 10.dp, bottom = 15.dp),


            ) {
            Image(
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        val intent = Intent(context,HomeActivity::class.java)
                        context.startActivity(intent)
                    },
                painter = painterResource(id = R.drawable.back_black),
                contentDescription = "Back Image",

                )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp),
                text = "Calendar",
                fontSize = 30.sp,
                textAlign = TextAlign.Center

            )
        }

        OutlinedCard(
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Let's Plan",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, bottom = 5.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold


            )
            Text(
                text = "Whiskers's Day!",
                modifier = Modifier
                    .padding(start = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Title Schedule",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, bottom = 5.dp),
            )
            OutlinedTextField(modifier = Modifier
                .padding(start = 16.dp, bottom = 15.dp, end = 16.dp)
                .fillMaxWidth(),
                value = title ,
                onValueChange = { newTitle ->
                    title = newTitle
                })

            Text(
                text = "Notes",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, bottom = 5.dp),
            )
            OutlinedTextField(modifier = Modifier
                .padding(start = 16.dp, bottom = 15.dp, end = 16.dp)
                .fillMaxWidth(),
                value = notes ,
                onValueChange = { newNotes ->
                    notes = newNotes
                })

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
            ) {
                OutlinedButton(modifier = Modifier
                    .padding(start = 25.dp, end = 30.dp),
                    onClick = {
                        calendarState.show()
                    }) { Text(text = "Date Picker") }

                OutlinedButton(onClick = {
                    clockState.show()
                }) { Text(text = "Time Picker", maxLines = 1)}
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { /* Aksi tombol */ },
                    modifier = Modifier
                ) {
                    Text(text = "Create New Schedule")
                }
            }
        }

    }
}