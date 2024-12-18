package usu.adpl.petkumobile

import android.content.Intent
import android.icu.util.TimeUnit
import android.os.Build
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.material.AlertDialog
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.time.Duration


class CalendarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getStringExtra("userId") // Jika userId adalah String

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }

        setContent {
            ScheduleView(userId = userId)
        }
    }
}


data class Schedule(
    val id: String? = null,
    val title: String = "",
    val notes: String = "",
    val date: String = "",
    val time: String = "",
    val userId: String? = null
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




val currentDate = LocalDate.now()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleView(userId: String?) {
    var title by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val schedules = remember { mutableStateOf<List<Schedule>>(emptyList()) }
    val calendarState = rememberSheetState()
    val clockState = rememberSheetState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scheduleViewModel: ScheduleViewModel = viewModel()

    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var scheduleToDelete by remember { mutableStateOf<Schedule?>(null) }


    LaunchedEffect(userId) {
        val database = FirebaseDatabase.getInstance()
        val scheduleRef = database.reference.child("schedule")

        val currentDate = LocalDate.now() // Hanya tanggal saat ini

        scheduleRef.orderByChild("userId").equalTo(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val fetchedSchedules = mutableListOf<Schedule>()
                    for (childSnapshot in snapshot.children) {
                        val schedule = childSnapshot.getValue(Schedule::class.java)
                        if (schedule != null) {
                            val scheduleDate = try {
                                // Ubah jadwal menjadi LocalDate
                                LocalDate.parse(schedule.date)
                            } catch (e: Exception) {
                                null
                            }

                            // Tambahkan hanya jika tanggal jadwal di masa mendatang
                            if (scheduleDate != null && scheduleDate.isAfter(currentDate)) {
                                fetchedSchedules.add(schedule)
                            }
                        }
                    }
                    // Urutkan jadwal berdasarkan tanggal
                    schedules.value = fetchedSchedules.sortedBy { LocalDate.parse(it.date) }
                }

                override fun onCancelled(error: DatabaseError) {
                    message = "Gagal mengambil data: ${error.message}"
                }
            })
    }


    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            snackbarHostState.showSnackbar(message)
        }
    }
    val currentDate = LocalDate.now()
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.MONTH,
            disabledDates = listOf(currentDate.minusDays(3)) // Nonaktifkan semua tanggal sebelumnya
        ),
        selection = CalendarSelection.Date { date ->
            selectedDate = date.toString() // Simpan tanggal yang dipilih
        }
    )

    ClockDialog(
        state = clockState,
        config = ClockConfig(is24HourFormat = false),
        selection = ClockSelection.HoursMinutes { hours, minutes ->
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
                    .clickable(
                        onClick = {
                            (context as? ComponentActivity)?.onBackPressedDispatcher?.onBackPressed()
                        }
                    ),
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
                text = "Your Pet's Day!",
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
                onValueChange = { newTitle -> title = newTitle }
            )

            Text(
                text = "Notes",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, bottom = 5.dp),
            )

            Spacer(modifier = Modifier.height(8.dp))  // Menambah jarak eksplisit antara Text dan OutlinedTextField

            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 15.dp, end = 16.dp)
                    .fillMaxWidth(),
                value = notes,
                onValueChange = { newNotes -> notes = newNotes }
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 40.dp),
                    onClick = { calendarState.show() }
                ) { Text(text = "Date Picker") }

                OutlinedButton(onClick = { clockState.show() }) { Text(text = "Time Picker", maxLines = 1) }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        if (title.isNotBlank() && notes.isNotBlank() && selectedDate.isNotBlank() && selectedTime.isNotBlank()) {
                            Log.d("Notes", "Entered notes: $notes")
                            val combinedDateTimeString = "$selectedDate $selectedTime"
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                            val selectedDateTime = LocalDateTime.parse(combinedDateTimeString, formatter)

                            val delay = scheduleViewModel.calculateDelay(LocalDateTime.now(), selectedDateTime)  // Hitung delay

                            // Panggil scheduleNotification dari ViewModel
                            scheduleViewModel.scheduleNotification(title, notes, delay)

                            val scheduleDate = try {
                                LocalDate.parse(selectedDate)
                            } catch (e: Exception) {
                                null
                            }

                            if (scheduleDate != null && !scheduleDate.isBefore(currentDate)) {
                                val schedule = Schedule(
                                    title = title,
                                    notes = notes,
                                    date = selectedDate,
                                    time = selectedTime,
                                    userId = userId
                                )
                                savedDataSchedule(schedule,
                                    onSucces = {
                                        val workManager = WorkManager.getInstance(context)

                                        val timeParts = selectedTime.split(":")
                                        val hours = timeParts[0].toInt()
                                        val minutes = timeParts[1].toInt()

                                        // Hitung delay waktu dalam milidetik
                                        val notificationTime = LocalDateTime.of(scheduleDate.year, scheduleDate.monthValue, scheduleDate.dayOfMonth, hours, minutes)
                                        val delay = java.time.Duration.between(LocalDateTime.now(), notificationTime).toMillis()

                                        if (delay > 0) {
                                            val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                                                .setInitialDelay(delay, java.util.concurrent.TimeUnit.MILLISECONDS)
                                                .setInputData(
                                                    workDataOf(
                                                        "title" to title,
                                                        "notes" to notes
                                                    )
                                                )
                                                .build()

                                            workManager.enqueue(workRequest)
                                        }
                                        showSuccessDialog = true
                                        title = ""
                                        notes = ""
                                        selectedDate = ""
                                        selectedTime = ""
                                    },
                                    onFailure = { exception -> message = "Error: ${exception.message}" })
                            } else {
                                message = "Please select a valid date and time (today or later)."
                            }
                        } else {
                            message = "Please fill all fields to create a schedule."
                        }
                    },
                    enabled = title.isNotBlank() && notes.isNotBlank() && selectedDate.isNotBlank() && selectedTime.isNotBlank(),
                    modifier = Modifier
                ) { Text(text = "Create New Schedule") }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(schedules.value) { schedule ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE1BEE7) // Warna ungu muda (hex: #E1BEE7)
                    ),
                    elevation = CardDefaults.elevatedCardElevation(4.dp)
                )  {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Judul: ${schedule.title}", fontWeight = FontWeight.Bold)
                            Text(text = "Catatan: ${schedule.notes}")
                            Text(text = "Tanggal: ${schedule.date}")
                            Text(text = "Waktu: ${schedule.time}")
                        }
                        Text(
                            text = "Hapus",
                            color = Color(0xFF6A1B9A),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clickable {
                                    scheduleToDelete = schedule
                                    showDeleteConfirmation = true
                                }
                                .padding(start = 4.dp)
                        )
                    }
                }
            }
        }


        if (showDeleteConfirmation) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text("Delete Confirmation") },
                text = { Text("Are you sure you want to delete this schedule?") },
                confirmButton = {
                    Button(
                        onClick = {
                            scheduleToDelete?.let { schedule ->
                                deleteSchedule(schedule,
                                    onSuccess = {
                                        message = "Schedule successfully deleted"
                                        showDeleteConfirmation = false
                                    },
                                    onFailure = { errorMessage ->
                                        message = errorMessage
                                        showDeleteConfirmation = false
                                    }
                                )
                            }
                        }
                    ) { Text("Yes") }
                },
                dismissButton = {
                    Button(
                        onClick = { showDeleteConfirmation = false }
                    ) { Text("No") }
                }
            )
        }
        SnackbarHost(hostState = snackbarHostState)

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                title = { Text(text = "Success") },
                text = { Text(text = "Your schedule has been created successfully.") },
                confirmButton = {
                    Button(
                        onClick = { showSuccessDialog = false }
                    ) { Text("OK") }
                }
            )
        }
    }
}



// Fungsi Delete Schedule (tidak ada perubahan)
fun deleteSchedule(schedule: Schedule, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    val database = FirebaseDatabase.getInstance()
    val scheduleRef = database.reference.child("schedule")

    scheduleRef.orderByChild("title").equalTo(schedule.title)
        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    child.ref.removeValue()
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener { exception ->
                            onFailure("Error: ${exception.message}")
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure("Error: ${error.message}")
            }
        })
}










