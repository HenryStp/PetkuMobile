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
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


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

fun getDayName(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(dateString, formatter)
    return date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()) // Contoh: "Wed"
}

fun savedDataSchedule(
    schedule: Schedule,
    onSucces: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val database = FirebaseDatabase.getInstance()
    val scheduleRef = database.getReference("schedule")

    val scheduleId = scheduleRef.push().key ?: return

    val scheduleWithId = schedule.copy(id = scheduleId)

    scheduleRef.child(scheduleId).setValue(scheduleWithId)
        .addOnSuccessListener {
            onSucces()
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
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
                                message = "Schedule Created Successfully!"

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
    SnackbarHost(hostState = snackbarHostState)
}

fun fetchSchedulesFromFirebase(
    onSuccess: (List<Schedule>) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val database = FirebaseDatabase.getInstance()
    val scheduleRef = database.getReference("schedule")

    scheduleRef.get().addOnSuccessListener { dataSnapshot ->
        val schedules = mutableListOf<Schedule>()
        dataSnapshot.children.forEach { snapshot ->
            val schedule = snapshot.getValue(Schedule::class.java)
            if (schedule != null) {
                schedules.add(schedule)
            }
        }
        onSuccess(schedules)
    }.addOnFailureListener { exception ->
        onFailure(exception)
    }
}


@Composable
fun ScheduleItem(
    day: String,
    date: String,
    title: String,
    subtitle: String,
    backgroundColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
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
}

fun deleteScheduleFromFirebase(
    scheduleId: String,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val database = FirebaseDatabase.getInstance()
    val scheduleRef = database.getReference("schedule").child(scheduleId)

    scheduleRef.removeValue()
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { exception -> onFailure(exception) }
}

fun updateScheduleInFirebase(
    scheduleId: String,
    updatedSchedule: Schedule,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val database = FirebaseDatabase.getInstance()
    val scheduleRef = database.getReference("schedule").child(scheduleId)

    scheduleRef.setValue(updatedSchedule)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { exception -> onFailure(exception) }
}




@Composable
fun WeeklySchedule() {
    var schedules by remember { mutableStateOf<List<Schedule>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isEditing by remember { mutableStateOf(false) }
    var editingSchedule by remember { mutableStateOf<Schedule?>(null) }

    // Ambil data dari Firebase saat komponen diluncurkan
    LaunchedEffect(Unit) {
        fetchSchedulesFromFirebase(
            onSuccess = { fetchedSchedules ->
                schedules = fetchedSchedules
            },
            onFailure = { exception ->
                errorMessage = exception.message
            }
        )
    }

    if (errorMessage != null) {
        Text(
            text = "Error: $errorMessage",
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )
    } else if (schedules.isEmpty()) {
        Text(
            text = "No schedules available",
            modifier = Modifier.padding(16.dp),
            color = Color.Gray
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            schedules.forEachIndexed { index, schedule ->
                val backgroundColor = when (index % 3) {
                    0 -> Color(0xFFD3EED8)
                    1 -> Color(0xFFFFD7D7)
                    else -> Color(0xFFFFE4C7)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = backgroundColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = getDayName(schedule.date),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                            Text(
                                text = schedule.date.split("-").last(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                            Text(
                                text = schedule.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Text(
                                text = schedule.notes,
                                fontSize = 14.sp,
                                color = Color.DarkGray
                            )
                        }

                        // Tombol Edit
                        IconButton(onClick = {
                            editingSchedule = schedule
                            isEditing = true
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_edit), // Gunakan ikon edit yang sesuai
                                contentDescription = "Edit",
                                tint = Color.Blue
                            )
                        }

                        // Tombol Delete
                        IconButton(onClick = {
                            deleteScheduleFromFirebase(
                                schedule.id ?: "",
                                onSuccess = {
                                    schedules = schedules.filter { it.id != schedule.id }
                                },
                                onFailure = { exception ->
                                    errorMessage = exception.message
                                }
                            )
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_delete), // Gunakan ikon delete yang sesuai
                                contentDescription = "Delete",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }

    // Dialog Edit
    if (isEditing) {
        EditScheduleDialog(
            schedule = editingSchedule,
            onDismiss = { isEditing = false },
            onSave = { updatedSchedule ->
                if (updatedSchedule.id != null) {
                    updateScheduleInFirebase(
                        updatedSchedule.id,
                        updatedSchedule,
                        onSuccess = {
                            schedules = schedules.map {
                                if (it.id == updatedSchedule.id) updatedSchedule else it
                            }
                            isEditing = false
                        },
                        onFailure = { exception ->
                            errorMessage = exception.message
                        }
                    )
                }
            }
        )
    }
}
@Composable
fun EditScheduleDialog(
    schedule: Schedule?,
    onDismiss: () -> Unit,
    onSave: (Schedule) -> Unit
) {
    if (schedule == null) return

    var title by remember { mutableStateOf(schedule.title) }
    var notes by remember { mutableStateOf(schedule.notes) }
    var date by remember { mutableStateOf(schedule.date) }
    var time by remember { mutableStateOf(schedule.time) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Edit Schedule") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes") }
                )
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date") }
                )
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(schedule.copy(title = title, notes = notes, date = date, time = time))
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}
