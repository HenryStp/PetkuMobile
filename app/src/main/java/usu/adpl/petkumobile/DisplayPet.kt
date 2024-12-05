package usu.adpl.petkumobile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class DisplayPetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getStringExtra("user_id") ?: "Unknown User"
        setContent {
            val navController = rememberNavController() // Inisialisasi NavController
            DisplayPet(navController = navController, userId = userId)
        }
    }
}

@Composable
fun DisplayPet(navController: NavController, userId: String) {
    // State untuk menyimpan data dari Firebase
    // State untuk menyimpan data dari Firebase
    var pets by remember { mutableStateOf<List<PetData>>(emptyList()) }

    DisposableEffect(Unit) {
        val database = FirebaseDatabase.getInstance()
        val petsRef = database.getReference("pets")

        // Query untuk mengambil data berdasarkan userId
        val query = petsRef.orderByChild("userId").equalTo(userId)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val loadedPets =
                        snapshot.children.mapNotNull { it.getValue(PetData::class.java) }
                    pets = loadedPets
                    Log.d("FirebaseData", "Pets loaded for user $userId: $pets")
                } catch (e: Exception) {
                    Log.e("FirebaseError", "Error parsing data: ${e.message}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Query cancelled: ${error.message}")
            }
        }

        query.addValueEventListener(valueEventListener)

        // Cleanup listener saat `DisplayPet` tidak lagi digunakan
        onDispose {
            query.removeEventListener(valueEventListener)
        }
    }


    // Tampilan utama
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Box hijau dengan rounded corners
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 115.dp)
                .background(
                    color = Color(0xFFD2E8E1),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
        )

        // Tombol back
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .padding(top = 115.dp)
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.size(25.dp)
            )
        }

        // Konten di dalam Box hijau
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(40.dp)
            ) {
                if (pets.isNotEmpty()) {
                    items(pets.size) { index ->
                        val pet = pets[index]
                        PetCard(pet)
                    }
                } else {
                    item {
                        Text(
                            text = "No pets available for this user",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

    @Composable
fun PetCard(pet: PetData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            pet.avatar?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.LightGray, RoundedCornerShape(50.dp))
                )
            } ?: run {
                // Jika avatar null, tampilkan gambar default
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Gray, RoundedCornerShape(50.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No Image", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Informasi dasar
            Text(
                text = pet.name ?: "No name available",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )
            Text(text = pet.breed ?: "No breed available", fontSize = 16.sp, color = Color.DarkGray)
            Text(text = pet.age ?: "Age unknown", fontSize = 16.sp, color = Color.DarkGray)
            Text(text = pet.gender, fontSize = 16.sp, color = Color.DarkGray) // Menampilkan gender
            Text(text = "${pet.weight} kg", fontSize = 16.sp, color = Color.DarkGray)
            Text(text = "${pet.height} cm", fontSize = 16.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))

            // Informasi medis
            Text(
                text = "Medical Information",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Text(text = pet.medicalInfo ?: "No medical info available", fontSize = 14.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))

            // Informasi tambahan
            Text(
                text = "Additional Information",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Text(text = pet.additionalInfo ?: "No additional info available", fontSize = 14.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Edit dan Delete
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { /* TODO: Edit logic */ }) {
                    Text(text = "Edit")
                }
                Button(
                    onClick = { /* TODO: Delete logic */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(text = "Delete")
                }
            }
        }
    }
}
@Composable
fun getAvatarDrawable(avatarIndex: Int): Int {
    return when (avatarIndex) {
        1 -> R.drawable.avatar1
        2 -> R.drawable.avatar2
        3 -> R.drawable.avatar3
        4 -> R.drawable.avatar4
        5 -> R.drawable.avatar5
        6 -> R.drawable.avatar6
        7 -> R.drawable.avatar7
        8 -> R.drawable.avatar8
        9 -> R.drawable.avatar9
        10 -> R.drawable.avatar10
        11 -> R.drawable.avatar11
        12 -> R.drawable.avatar12
        else -> throw IllegalArgumentException("Invalid avatar index: $avatarIndex") // Lemparkan exception jika avatarIndex tidak valid
    }
}

@Preview
@Composable
fun DisplayPetPreview() {
    val navController = rememberNavController()
    //DisplayPet(navController = navController)
}

