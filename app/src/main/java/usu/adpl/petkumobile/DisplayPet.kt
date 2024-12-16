package usu.adpl.petkumobile

import android.app.Activity
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import android.content.Context
import androidx.compose.runtime.rememberUpdatedState

class DisplayPetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getStringExtra("userId") ?: "Unknown User"

        setContent {
            val navController = rememberNavController() // Inisialisasi NavController
            DisplayPet(navController = navController, userId = userId)
        }
    }
}

@Composable
fun DisplayPet(navController: NavController, userId: String) {
    val context = LocalContext.current
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

        onDispose {
            query.removeEventListener(valueEventListener)
        }
    }

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
            onClick = { (context as? ComponentActivity)?.onBackPressed() },
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

                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(50.dp)
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
    val context = LocalContext.current
    val avatarResId = pet.avatar?.let {
        context.resources.getIdentifier("avatar$it", "drawable", context.packageName)
    }

    var showEditDialog by remember { mutableStateOf(false) }
    var updatedPet by remember { mutableStateOf(pet.copy()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            avatarResId?.takeIf { it != 0 }?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.LightGray, RoundedCornerShape(50.dp))
                )
            } ?: run {
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
                text = updatedPet.name ?: "No name available",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )
            Text(text = updatedPet.breed ?: "No breed available", fontSize = 16.sp, color = Color.DarkGray)
            Text(text = updatedPet.age ?: "Age unknown", fontSize = 16.sp, color = Color.DarkGray)
            Text(text = updatedPet.gender, fontSize = 16.sp, color = Color.DarkGray)
            Text(text = "${updatedPet.weight} kg", fontSize = 16.sp, color = Color.DarkGray)
            Text(text = "${updatedPet.height} cm", fontSize = 16.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))

            // Informasi medis
            Text(
                text = "Medical Information: ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Text(text = updatedPet.medicalInfo ?: "No medical info available", fontSize = 12.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))

            // Informasi tambahan
            Text(
                text = "Additional Information: ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Text(text = updatedPet.additionalInfo ?: "No additional info available", fontSize = 12.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Edit dan Delete
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { showEditDialog = true }) {
                    Text(text = "Edit")
                }
                DeletePetButton(
                    pet = updatedPet,
                    onDeleteConfirmed = {
                        val database = FirebaseDatabase.getInstance()
                        val petsRef = database.getReference("pets")

                        // Ambil userId dari entri lama
                        val userId = updatedPet.userId.toString()

                        // Hapus semua data berdasarkan userId
                        petsRef.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                snapshot.children.forEach { petSnapshot ->
                                    petSnapshot.ref.removeValue()
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Log.d("DeletePet", "Pet data deleted successfully for userId: $userId")
                                            } else {
                                                Log.e("DeletePet", "Failed to delete pet data for userId: $userId")
                                            }
                                        }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.e("DeletePet", "Failed to fetch data for userId: $userId", error.toException())
                            }
                        })
                    }
                )


            }
        }
    }

    // Dialog Formulir Edit
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Edit Pet Details") },
            text = {
                Column {

                    OutlinedTextField(
                        value = updatedPet.age ?: "",
                        onValueChange = { updatedPet = updatedPet.copy(age = it) },
                        label = { Text("Age") }
                    )
                    OutlinedTextField(
                        value = updatedPet.weight?.toString() ?: "",
                        onValueChange = { updatedPet = updatedPet.copy(weight = it.toIntOrNull()) },
                        label = { Text("Weight") }
                    )
                    OutlinedTextField(
                        value = updatedPet.height?.toString() ?: "",
                        onValueChange = { updatedPet = updatedPet.copy(height = it.toIntOrNull()) },
                        label = { Text("Height") }
                    )
                    OutlinedTextField(
                        value = updatedPet.medicalInfo ?: "",
                        onValueChange = { updatedPet = updatedPet.copy(medicalInfo = it) },
                        label = { Text("Medical Information") }
                    )
                    OutlinedTextField(
                        value = updatedPet.additionalInfo ?: "",
                        onValueChange = { updatedPet = updatedPet.copy(additionalInfo = it) },
                        label = { Text("Additional Information") }
                    )
                }
            },
            confirmButton = {

                Button(
                    onClick = {
                        showEditDialog = false
                        val database = FirebaseDatabase.getInstance()
                        val petsRef = database.getReference("pets")

                        // Ambil userId dari sesi sebelumnya
                        val userId = updatedPet.userId.toString()

                        // Data lama tetap dipertahankan dengan userId
                        val updatedFields = mapOf(
                            "name" to updatedPet.name,
                            "breed" to updatedPet.breed,
                            "gender" to updatedPet.gender,
                            "age" to updatedPet.age,
                            "weight" to updatedPet.weight,
                            "height" to updatedPet.height,
                            "medicalInfo" to updatedPet.medicalInfo,
                            "additionalInfo" to updatedPet.additionalInfo
                        )

                        // Tambahkan entri baru dengan userId lama
                        petsRef.child(userId).setValue(updatedFields).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("EditPet", "Pet data updated successfully for userId: $userId")

                                // Hapus entri lama setelah pembaruan
                                petsRef.child(userId).removeValue().addOnCompleteListener { removeTask ->
                                    if (removeTask.isSuccessful) {
                                        Log.d("DeletePet", "Old pet data deleted successfully for userId: $userId")
                                    } else {
                                        Log.e("DeletePet", "Failed to delete old pet data for userId: $userId")
                                    }
                                }
                            } else {
                                Log.e("EditPet", "Failed to update pet data for userId: $userId")
                            }
                        }
                    }
                ) {
                    Text("Save")
                }
                Button(
                    onClick = { showEditDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun DeletePetButton(pet: PetData, onDeleteConfirmed: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Button(
        onClick = { showDialog = true },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
    ) {
        Text(text = "Delete")
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete Confirmation") },
            text = { Text("Are you sure you want to delete this pet profile?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onDeleteConfirmed() // Memanggil fungsi yang diberikan ketika konfirmasi
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("No")
                }
            }
        )
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

