package usu.adpl.petkumobile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    // Fungsi untuk mengonversi tanggal dan waktu yang dipilih menjadi LocalDateTime
    fun parseScheduleDate(selectedDate: String, selectedTime: String): LocalDateTime {
        val combinedDateTimeString = "$selectedDate $selectedTime"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        return LocalDateTime.parse(combinedDateTimeString, formatter)
    }

    // Fungsi untuk menghitung delay (waktu tunggu) dalam milidetik
    fun calculateDelay(currentDateTime: LocalDateTime, selectedDateTime: LocalDateTime): Long {
        return Duration.between(currentDateTime, selectedDateTime).toMillis() // Menghitung delay dalam milidetik
    }

    // Fungsi untuk menjadwalkan notifikasi
    fun scheduleNotification(title: String, message: String, delayInMillis: Long) {

        // Membuat data untuk dikirim ke NotificationWorker
        val inputData = workDataOf(
            "title" to title,
            "message" to message
        )

        // Membuat WorkRequest untuk menunda notifikasi
        val notificationRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(inputData)
            .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
            .addTag("schedule_notification")
            .build()

        // Menambahkan WorkRequest ke WorkManager
        WorkManager.getInstance(getApplication()).enqueue(notificationRequest)
    }


}
