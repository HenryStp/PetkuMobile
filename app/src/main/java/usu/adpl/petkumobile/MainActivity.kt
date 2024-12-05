package usu.adpl.petkumobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.util.Log
import androidx.activity.compose.setContent
import usu.adpl.petkumobile.ui.theme.PetkuMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cek apakah aplikasi pertama kali dibuka
        if (isFirstLaunch(this)) {
            // Jika pertama kali, tampilkan SplashActivity
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // Jika bukan pertama kali, langsung menuju LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Fungsi untuk mengecek apakah aplikasi pertama kali dibuka
    private fun isFirstLaunch(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

        // Tambahkan log di sini untuk memeriksa nilai isFirstLaunch
        Log.d("MainActivity", "isFirstLaunch: $isFirstLaunch")

        // Jika ini pertama kali, set status menjadi false setelah pengecekan
        if (isFirstLaunch) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("isFirstLaunch", false)  // Update status menjadi false
            editor.apply()  // Jangan lupa simpan perubahan
        }
        return isFirstLaunch  // Mengembalikan status pertama kali aplikasi dibuka
    }
    // Fungsi untuk menyimpan userId ke session (SharedPreferences)
    /*fun saveUserIdToSession(context: Context, userId: String) {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userId", userId)  // Simpan userId
        editor.apply()  // Jangan lupa simpan perubahan
    }*/

    // Fungsi untuk mengambil userId dari session
    fun getUserIdFromSession(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userId", null)  // Mengambil userId jika ada, jika tidak null
    }
}
