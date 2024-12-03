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
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

import com.google.firebase.database.FirebaseDatabase
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


data class Schedule(
    val id: String? = null,
    val title: String = "",
    val notes: String = "",
    val date: String = "",
    val time: String = ""
)

fun savedDataSchedule(
    schedule: Schedule,
    onSucces : () -> Unit,
    onFailure : (Exception) -> Unit
){
    val database = FirebaseDatabase.getInstance()
    val scheduleRef = database.getReference("schedule")

    val scheduleId = scheduleRef.push().key ?: return

    scheduleRef.child(scheduleId).setValue(schedule)
        .addOnSuccessListener {
            onSucces()
        }
        .addOnFailureListener{ exception ->
            onFailure(exception)
        }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleView() {
    var title by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val calendarState = rememberSheetState()
    val clockState = rememberSheetState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            snackbarHostState.showSnackbar(message)
        }
    }

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.MONTH,
            disabledDates = listOf(LocalDate.now().minusDays(3))
        ),

        selection =CalendarSelection.Date{date ->
            selectedDate = date.toString()

        } )


    ClockDialog(
        state = clockState,
        config = ClockConfig(is24HourFormat = false),
        selection = ClockSelection.HoursMinutes {hours,minutes ->
            selectedTime = "$hours:$minutes"


        }
    )

    Column(
        modifier = Modifier
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
                        val intent = Intent(context, HomeActivity::class.java)
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
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 15.dp, end = 16.dp)
                    .fillMaxWidth(),
                value = title,
                onValueChange = { newTitle ->
                    title = newTitle
                }
            )

            Text(
                text = "Notes",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, bottom = 5.dp),
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 15.dp, end = 16.dp)
                    .fillMaxWidth(),
                value = notes,
                onValueChange = { newNotes ->
                    notes = newNotes
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .padding(start = 25.dp, end = 30.dp),
                    onClick = {
                        calendarState.show()
                    }
                ) { Text(text = "Date Picker") }

                OutlinedButton(onClick = {
                    clockState.show()
                }) { Text(text = "Time Picker", maxLines = 1) }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(

                    onClick = {
                        val schedule = Schedule(
                            title = title,
                            notes = notes,
                            date = selectedDate,
                            time = selectedTime

                        )
                        savedDataSchedule(schedule,
                            onSucces = {
                                message = "Schedule Create Succesfully !"

                                title = ""
                                notes = ""
                                selectedDate = ""
                                selectedTime = ""
                            },
                            onFailure = { exception ->
                                message = "Error: ${exception.message}"
                            })
                    },

                    modifier = Modifier
                ) {
                    Text(text = "Create New Schedule")
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Agar agenda mingguan berada di bagian bawah
        WeeklySchedule() // Tambahkan tampilan agenda mingguan di sini
    }
}

@Composable
fun WeeklySchedule() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White)
    ) {
        ScheduleItem(day = "Wed", date = "2", title = "Training Session", subtitle = "Let's learn something new!", backgroundColor = Color(0xFFD3EED8))
        ScheduleItem(day = "Sat", date = "5", title = "Walk Time", subtitle = "Let's get moving!", backgroundColor = Color(0xFFF9E0D8))
        ScheduleItem(day = "Sun", date = "6", title = "Teeth Cleaning", subtitle = "Keep that smile Bright!", backgroundColor = Color(0xFFF6D8DA))
    }
    SnackbarHost(hostState = snackbarHostState)
}

@Composable
fun ScheduleItem(
    day: String,
    date: String,
    title: String,
    subtitle: String,
    backgroundColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Text(text = day, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)
            Text(text = date, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
        }

        Column {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Text(text = subtitle, fontSize = 14.sp, color = Color.DarkGray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScheduleView() {
    ScheduleView()
}