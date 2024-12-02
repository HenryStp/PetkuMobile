package usu.adpl.petkumobile

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Composable
fun CatForm(navController: NavController) {
    var selectedButton by rememberSaveable { mutableStateOf("Dog") }
    var selectedAvatar by rememberSaveable { mutableStateOf<Int?>(null) }
    var name by rememberSaveable { mutableStateOf<String?>(null) }
    var breed by rememberSaveable { mutableStateOf<String?>(null) }
    var age by rememberSaveable { mutableStateOf<String?>(null) }
    var weight by rememberSaveable { mutableStateOf<Int?>(null) }
    var height by rememberSaveable { mutableStateOf<Int?>(null) }
    var medicalInfo by rememberSaveable { mutableStateOf<String?>(null) }
    var additionalInfo by rememberSaveable { mutableStateOf<String?>(null) }
    var gender by rememberSaveable { mutableStateOf("") } // Menambahkan variabel gender
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Menyimpan status dialog
    var showDialog by remember { mutableStateOf(false) }

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
                    color = Color(0xFFD2E8E1),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
        ) {
            IconButton(
                onClick = { navController.navigate("PetScreen") }, // Navigasi ke PetScreen
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
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
                PetTypeSelectionCat(
                    selectedButton = selectedButton,
                    onTypeSelected = { selectedType -> selectedButton = selectedType },
                    navController = navController
                )

                Spacer(modifier = Modifier.height(16.dp))

                AvatarSectionCat(selectedAvatar) { selectedAvatar = it }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        CustomTextFieldForCatForm("Name", name, onValueChange = { name = it })
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomTextFieldForCatForm("Breed", breed, onValueChange = { breed = it })
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomTextFieldForCatForm("Age", age, onValueChange = { age = it })
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        GenderSelectionCat(gender) { gender = it }
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomTextFieldForCatForm("Weight", weight, onValueChange = { weight = it })
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomTextFieldForCatForm("Height", height, onValueChange = { height = it })
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomTextFieldForCatForm(
                            "Medical Information", medicalInfo, onValueChange = { medicalInfo = it }, isMultiline = true
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomTextFieldForCatForm(
                            "Additional Information", additionalInfo, onValueChange = { additionalInfo = it }, isMultiline = true
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        if (errorMessage != null) {
                            Text(
                                text = errorMessage!!,
                                color = Color.Red,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        // Tombol Submit berada di bawah form
                        Spacer(modifier = Modifier.weight(1f)) // Mengisi ruang kosong agar tombol berada di bawah
                        Button(
                            onClick = {
                                if (validateInputsCat(
                                        name,
                                        breed,
                                        age,
                                        weight,
                                        height,
                                        medicalInfo,
                                        additionalInfo,
                                        selectedAvatar,
                                        gender
                                    )
                                ) {
                                    errorMessage = null
                                    // Simpan data ke Realtime Database
                                    saveToDatabaseCat(name, breed, age, weight, height, medicalInfo, additionalInfo, selectedAvatar, gender)
                                    // Menampilkan dialog saat form disubmit
                                    showDialog = true
                                } else {
                                    errorMessage = "Please fill all fields and select an avatar."
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp) // Padding horizontal untuk tombol
                                .border(
                                    width = 1.dp,
                                    color = Color.Black,
                                    shape = RoundedCornerShape(50.dp)
                                ),
                            shape = RoundedCornerShape(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            )
                        ) {
                            Text("Submit")
                        }
                    }
                }
            }
            // Menampilkan ProfileSaved di tengah layar
            if (showDialog) {
                Box(
                    modifier = Modifier
                        .offset(y = 50.dp) // Memindahkan ke bawah sedikit
                        .align(Alignment.Center)
                ) {
                    ProfileSaved(navController = navController)
                }
            }
        }
    }
}

@Composable
fun PetTypeSelectionCat(
    selectedButton: String,
    onTypeSelected: (String) -> Unit,
    navController: NavController
) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        listOf("Dog", "Cat").forEach { type ->
            Button(
                onClick = {
                    if (type == "Dog") {
                        navController.navigate("dogForm") // Navigasi ke halaman DogForm
                    } else {
                        onTypeSelected(type)
                    }
                },
                shape = RoundedCornerShape(50.dp),
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedButton == type) Color(0xFFFFC0CB) else Color.White
                )
            ) {
                Text(text = type, color = Color.Black)
            }
        }
    }
}

@Composable
fun AvatarSectionCat(selectedAvatar: Int?, onAvatarSelected: (Int) -> Unit) {
    val dogAvatars = listOf(
        R.drawable.avatar7,
        R.drawable.avatar8,
        R.drawable.avatar9,
        R.drawable.avatar10,
        R.drawable.avatar11,
        R.drawable.avatar12
    )

    Spacer(modifier = Modifier.height(16.dp))

    Box(
        modifier = Modifier
            .size(280.dp, 200.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                dogAvatars.take(3).forEach { avatar ->
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(
                                if (selectedAvatar == avatar) Color(0xFFFFC0CB) else Color.White,
                                shape = RoundedCornerShape(30.dp)
                            )
                            .clickable { onAvatarSelected(avatar) }
                            .border(1.dp, Color.Black, RoundedCornerShape(30.dp))
                            .padding(5.dp)
                    ) {
                        Image(
                            painter = painterResource(id = avatar),
                            contentDescription = "Cat Avatar",
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center)
                                .clip(RoundedCornerShape(30.dp))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                dogAvatars.drop(3).forEach { avatar ->
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(
                                if (selectedAvatar == avatar) Color(0xFFFFC0CB) else Color.White,
                                shape = RoundedCornerShape(30.dp)
                            )
                            .clickable { onAvatarSelected(avatar) }
                            .border(1.dp, Color.Black, RoundedCornerShape(30.dp))
                            .padding(5.dp)
                    ) {
                        Image(
                            painter = painterResource(id = avatar),
                            contentDescription = "Cat Avatar",
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center)
                                .clip(RoundedCornerShape(30.dp))
                        )
                    }
                }
            }
        }
    }
}

fun validateInputsCat(
    name: String?,
    breed: String?,
    age: String?,
    weight: Int?,
    height: Int?,
    medicalInfo: String?,
    additionalInfo: String?,
    selectedAvatar: Int?,
    gender: String // Menambahkan validasi gender
): Boolean {
    return name != null && breed!= null && age!= null && weight != null && height != null &&  medicalInfo!= null && additionalInfo!= null && selectedAvatar != null && gender.isNotBlank()
}


@Composable
fun CustomTextFieldForCatForm(
    label: String,
    value: String?, // Tipe data untuk nilai String?
    onValueChange: (String?) -> Unit, // Mengirim nilai String? setelah perubahan
    isMultiline: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))

        TextField(
            value = value.orEmpty(), // Jika null, akan menggantikan dengan string kosong
            onValueChange = { newText -> onValueChange(newText) },
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isMultiline) 100.dp else 50.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .border(1.dp, Color.Black, RoundedCornerShape(30.dp)),
            maxLines = if (isMultiline) 5 else 1,
            singleLine = !isMultiline,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

// Versi untuk menerima Integer? jika diperlukan
@Composable
fun CustomTextFieldForCatForm(
    label: String,
    value: Int?, // Menggunakan Int? untuk nilai angka
    onValueChange: (Int?) -> Unit, // Mengirim nilai Int? setelah perubahan
    isMultiline: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))

        TextField(
            value = value?.toString().orEmpty(), // Mengonversi Int? menjadi String untuk TextField
            onValueChange = { newText ->
                val newValue = newText.toIntOrNull() // Mengonversi input menjadi Int? atau null
                onValueChange(newValue) // Mengirimkan nilai baru
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isMultiline) 100.dp else 50.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .border(1.dp, Color.Black, RoundedCornerShape(30.dp)),
            maxLines = if (isMultiline) 5 else 1,
            singleLine = !isMultiline,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun GenderSelectionCat(
    selectedGender: String,
    onGenderSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { onGenderSelected("Female") },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(50.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedGender == "Female") Color(0xFFFFC0CB) else Color.White
            )
        ) {
            Text("Female", color = Color.Black)
        }

        Button(
            onClick = { onGenderSelected("Male") },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(50.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedGender == "Male") Color(0xFFFFC0CB) else Color.White
            )
        ) {
            Text("Male", color = Color.Black)
        }
    }
}

fun saveToDatabaseCat(
    name: String?,
    breed: String?,
    age: String?,
    weight: Int?,
    height: Int?,
    medicalInfo: String?,
    additionalInfo: String?,
    selectedAvatar: Int?,
    gender: String
) {
    val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("pets")

    // Membuat petId unik menggunakan push()
    val petId = database.push().key
    if (petId != null) {
        // Menyiapkan data yang akan disimpan
        val petData = mapOf(
            "name" to name,
            "breed" to breed,
            "age" to age,
            "weight" to weight,
            "height" to height,
            "medicalInfo" to medicalInfo,
            "additionalInfo" to additionalInfo,
            "avatar" to selectedAvatar,
            "gender" to gender
        )

        // Menyimpan data ke path petId yang unik
        database.child(petId).setValue(petData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "Data successfully saved!")
                } else {
                    Log.e("Firebase", "Failed to save data", task.exception)
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error saving data", e)
            }
    } else {
        Log.e("Firebase", "Failed to generate petId")
    }
}

@Preview
@Composable
fun CatFormPreview() {
    val navController = rememberNavController()
    CatForm(navController = navController)
}
