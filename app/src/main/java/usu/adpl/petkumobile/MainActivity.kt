package usu.adpl.petkumobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import usu.adpl.petkumobile.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

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

        // Jika tidak ada data di SavedStateHandle, ambil dari SharedPreferences
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
