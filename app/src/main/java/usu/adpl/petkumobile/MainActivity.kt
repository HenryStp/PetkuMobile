package usu.adpl.petkumobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import usu.adpl.petkumobile.ui.theme.PetkuMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetkuMobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LostPet1(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


