package usu.adpl.petkumobile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
//import usu.adpl.petkumobile.R

@Composable
fun DisplayPet(navController: NavController) {
    // State untuk menyimpan apakah form sudah disimpan
    val isSaved by remember { mutableStateOf(false) }

    if (isSaved) {
        // Tampilan setelah form disimpan
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD2E8E1)), // Warna hijau pastel
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Menampilkan gambar
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Saved Icon",
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Data Saved Successfully!",
                    color = Color.Black,
                    fontSize = 20.sp
                )
            }
        }
    } else {
        // Formulir untuk input data
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 115.dp)
                    .background(
                        color = Color(0xFFD2E8E1), // Warna hijau pastel
                        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                    )
            ) {
                IconButton(
                    onClick = { navController.navigateUp() }, // Navigasi ke halaman sebelumnya
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                ) {
                    val arrowIcon = painterResource(id = R.drawable.arrow)
                    Icon(
                        painter = arrowIcon,
                        contentDescription = "Back",
                        modifier = Modifier.size(25.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        item {
                            // Tombol Edit dan Delete
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                            ) {
                                // Tombol Edit
                                HoverableButton(
                                    onClick = { /* Aksi Edit */ },
                                    text = "Edit"
                                )

                                // Tombol Delete
                                HoverableButton(
                                    onClick = { /* Aksi Delete */ },
                                    text = "Delete"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HoverableButton(onClick: () -> Unit, text: String) {
    var isHovered by remember { mutableStateOf(false) }

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isHovered) Color(0xFFFFC0CB) else Color.White
        ),
        modifier = Modifier
            .padding(8.dp)
            .pointerInput(Unit) {
                // Detect pointer events: press and release
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val press = event.changes.firstOrNull()?.pressed
                        isHovered = press == true
                    }
                }
            }
    ) {
        Text(text = text, color = Color.Black)
    }
}

@Preview
@Composable
fun DisplayPetPreview() {
    val navController = rememberNavController()
    DisplayPet(navController = navController)
}
