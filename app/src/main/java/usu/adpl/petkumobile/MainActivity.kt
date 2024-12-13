package usu.adpl.petkumobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val isUserLoggedIn = sharedPreferences.getBoolean("isUserLoggedIn", false)
        val username = sharedPreferences.getString("username", null)
        val userId = sharedPreferences.getString("userId", null)

        if (isUserLoggedIn && username != null && userId != null) {
            // Jika user sudah login sebelumnya, langsung buka HomeActivity
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("username", username)
                putExtra("userId", userId)
            }
            startActivity(intent)
            finish()
        } else if (isFirstLaunch()) {
            // Jika aplikasi pertama kali dibuka, tampilkan SplashActivity
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // Jika belum login atau data login tidak tersedia, tampilkan LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Fungsi untuk mengecek apakah aplikasi pertama kali dibuka
    private fun isFirstLaunch(): Boolean {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

        if (isFirstLaunch) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("isFirstLaunch", false) // Update status menjadi false
            editor.apply()
        }
        return isFirstLaunch
    }

    // Fungsi untuk menyimpan data login ke SharedPreferences
    fun saveLoginData(context: Context, username: String, userId: String) {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isUserLoggedIn", true)
        editor.putString("username", username)
        editor.putString("userId", userId)
        editor.apply()
    }

    // Fungsi untuk menghapus data login dari SharedPreferences (logout)
    fun clearLoginData(context: Context) {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
