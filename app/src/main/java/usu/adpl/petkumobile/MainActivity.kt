package usu.adpl.petkumobile

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import usu.adpl.petkumobile.viewmodel.UserViewModel
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {


    private lateinit var viewModel: UserViewModel
    private val scheduleViewModel: ScheduleViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val scheduleViewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)

// Input dari EditText atau sumber lainnya
        val title = "Reminder" // Gantilah dengan input dari EditText jika diperlukan
        val message = "This is your reminder!"
        val selectedDate = "2024-12-25" // Gantilah dengan input dari EditText
        val selectedTime = "10:00" // Gantilah dengan input dari EditText

// Mengonversi selectedDate dan selectedTime menjadi LocalDateTime
        val selectedDateTime = scheduleViewModel.parseScheduleDate(selectedDate, selectedTime)

// Menghitung delay (waktu tunggu dalam milidetik)
        val delayInMillis = scheduleViewModel.calculateDelay(LocalDateTime.now(), selectedDateTime)

// Menjadwalkan notifikasi menggunakan ViewModel
        scheduleViewModel.scheduleNotification(title, message, delayInMillis)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }

        viewModel.username.observe(this, { username ->
            viewModel.userId.observe(this, { userId ->
                if (username != null && userId != null) {
                    val intent = Intent(this, HomeActivity::class.java).apply {
                        putExtra("username", username)
                        putExtra("userId", userId)
                    }
                    startActivity(intent)
                    finish()
                }
            })
        })

        // Memeriksa data pengguna dari SharedPreferences jika tidak ada di ViewModel
        if (viewModel.username.value == null || viewModel.userId.value == null) {
            val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username", null)
            val userId = sharedPreferences.getString("userId", null)

            if (username != null && userId != null) {
                viewModel.saveUserData(username, userId)
            } else if (isFirstLaunch()) {
                val intent = Intent(this, SplashActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin diberikan, lakukan tindakan yang memerlukan izin ini
            } else {
                // Izin ditolak, beri tahu pengguna atau tangani sesuai kebutuhan
            }
        }
    }

    private fun isFirstLaunch(): Boolean {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

        if (isFirstLaunch) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("isFirstLaunch", false)
            editor.apply()
        }
        return isFirstLaunch
    }
}
