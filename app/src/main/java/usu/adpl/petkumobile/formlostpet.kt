@file:OptIn(ExperimentalMaterial3Api::class)
package usu.adpl.petkumobile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.FirebaseDatabase
import androidx.compose.material3.OutlinedTextField


data class LostPet(
    val petType: String = "",
    val photo: String = "",
    val name: String = "",
    val breed: String = "",
    val age: String = "",
    val gender: String = "",
    val weight: String = "",
    val height: String = "",
    val colorAndFeatures: String = "",
    val lastSeenLocation: String = "",
    val dateTimeLost: String = "",
    val additionalInfo: String = "",
    val ownerName: String = "",
    val ownerPhone: String = "",
    val ownerEmail: String = "",
    val ownerInstagram: String = "",
    val reward: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormLostPet(navController: NavHostController, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState() // Mengingat status scroll
    val showDialog = remember { mutableStateOf(false) }

// State untuk setiap input field
    val nameState = remember { mutableStateOf("") }
    val breedState = remember { mutableStateOf("") }
    val ageState = remember { mutableStateOf("") }
    val weightState = remember { mutableStateOf("") }
    val heightState = remember { mutableStateOf("") }
    val colorFeaturesState = remember { mutableStateOf("") }
    val lastSeenLocationState = remember { mutableStateOf("") }
    val dateTimeLostState = remember { mutableStateOf("") }
    val additionalInfoState = remember { mutableStateOf("") }
    val ownerNameState = remember { mutableStateOf("") }
    val ownerPhoneState = remember { mutableStateOf("") }
    val ownerEmailState = remember { mutableStateOf("") }
    val ownerInstagramState = remember { mutableStateOf("") }
    val rewardState = remember { mutableStateOf("") }
    val selectedPetType = remember { mutableStateOf("Cat") }
    val selectedGender = remember { mutableStateOf("Female") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState) // Menambahkan kemampuan scroll
            .imePadding() // Menambahkan padding untuk menghindari keyboard
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        // Row untuk tombol back dan teks "Report a Lost Pet" di tengah atas
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.back_black),
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.weight(0.6f))
            Text(
                text = "Report a Lost Pet",
                fontSize = 20.sp,
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Pet", fontSize = 14.sp,
            fontFamily = customFontFamily,
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            modifier = Modifier.padding(start = 15.dp))
        // Pet type (Cat/Dog)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            ChoiceButton(
                text = "Cat",
                isSelected = selectedPetType.value == "Cat",
                onClick = { selectedPetType.value = "Cat" }
            )
            ChoiceButton(
                text = "Dog",
                isSelected = selectedPetType.value == "Dog",
                onClick = { selectedPetType.value = "Dog" }
            )
        }


        // Input fields for pet details
        //InputField(label = "Photo", isButton = true, modifier = Modifier.fillMaxWidth())
        InputField( value = nameState.value,
            onValueChange = { nameState.value = it },
            label = "Name", modifier = Modifier.fillMaxWidth())
        InputField(
            value = breedState.value,
            onValueChange = { breedState.value = it },
            label = "Breed", modifier = Modifier.fillMaxWidth())
        InputField(
            value = ageState.value,
            onValueChange = { ageState.value = it },
            label = "Age", modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Gender", fontSize = 14.sp,
            fontFamily = customFontFamily,
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            modifier = Modifier.padding(start = 15.dp))


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            ChoiceButton(
                text = "Female",
                isSelected = selectedGender.value == "Female",
                onClick = { selectedGender.value = "Female" }
            )
            ChoiceButton(
                text = "Male",
                isSelected = selectedGender.value == "Male",
                onClick = { selectedGender.value = "Male" }
            )
        }


        InputField(
            value = weightState.value,
            onValueChange = { weightState.value = it },
            label = "Weight", modifier = Modifier.fillMaxWidth())
        InputField(
            value = heightState.value,
            onValueChange = { heightState.value = it },
            label = "Height", modifier = Modifier.fillMaxWidth())
        InputField(
            value = colorFeaturesState.value,
            onValueChange = { colorFeaturesState.value = it },
            label = "Color & Features", modifier = Modifier.fillMaxWidth())

        // Input field for Last Seen Location with increased height
        InputField(
            value = lastSeenLocationState.value,
            onValueChange = { lastSeenLocationState.value = it },
            label = "Last Seen Location", singleLine = false, modifier = Modifier
                .fillMaxWidth()
                .height(120.dp))

        InputField(
            value = dateTimeLostState.value,
            onValueChange = { dateTimeLostState.value = it },
            label = "Date & Time Lost", modifier = Modifier.fillMaxWidth())

        // Input field for Additional Information with increased height
        InputField(
            value = additionalInfoState.value,
            onValueChange = { additionalInfoState.value = it },
            label = "Additional Information", singleLine = false, modifier = Modifier
                .fillMaxWidth()
                .height(120.dp))

        Spacer(modifier = Modifier.height(16.dp))

        // Owner contact information
        Text(text = "Owner Contact:", fontFamily = customFontFamily, fontWeight = FontWeight.Bold)
        InputField(
            value = ownerNameState.value,
            onValueChange = { ownerNameState.value = it },
            label = "Name", modifier = Modifier.fillMaxWidth())
        InputField(
            value = ownerPhoneState.value,
            onValueChange = { ownerPhoneState.value = it },
            label = "Phone", modifier = Modifier.fillMaxWidth())
        InputField(
            value = ownerEmailState.value,
            onValueChange = { ownerEmailState.value = it },
            label = "Email", modifier = Modifier.fillMaxWidth())
        InputField(
            value = ownerInstagramState.value,
            onValueChange = { ownerInstagramState.value = it },
            label = "Instagram", modifier = Modifier.fillMaxWidth())
        InputField(
            value = rewardState.value,
            onValueChange = { rewardState.value = it },
            label = "Reward (Optional)", modifier = Modifier.fillMaxWidth())


        Spacer(modifier = Modifier.height(16.dp))

        // Submit button
        Button(
            onClick = {
                // Kumpulkan data dari input
                val lostPet = LostPet(
                    petType = selectedPetType.value,
                    photo = "Photo URL if available", // Implementasikan upload foto
                    name = nameState.value, // Menggunakan nilai langsung karena state sekarang adalah String
                    breed = breedState.value,
                    age = ageState.value,
                    gender = selectedGender.value,
                    weight = weightState.value,
                    height = heightState.value,
                    colorAndFeatures = colorFeaturesState.value,
                    lastSeenLocation = lastSeenLocationState.value,
                    dateTimeLost = dateTimeLostState.value,
                    additionalInfo = additionalInfoState.value,
                    ownerName = ownerNameState.value,
                    ownerPhone = ownerPhoneState.value,
                    ownerEmail = ownerEmailState.value,
                    ownerInstagram = ownerInstagramState.value,
                    reward = rewardState.value
                )


                // Simpan data ke Firebase
                submitLostPetData(lostPet)

                // Tampilkan dialog sukses
                showDialog.value = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4B9B8)),
            shape = RoundedCornerShape(32.dp),
            contentPadding = PaddingValues(0.dp), // Menghilangkan padding default
            modifier = Modifier
                .padding(8.dp) // Memberikan jarak antar tombol
                .clip(RoundedCornerShape(32.dp)) // Mengikuti bentuk tombol
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(32.dp) // Outline bulat sama dengan tombol
                )
                .height(40.dp) // Tinggi tombol
                .width(400.dp) // Lebar tombol
        ) {
            Text(
                text = "Submit",
                fontSize = 16.sp,
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color.Red
            )
        }
        // Dialog pop-up
        if (showDialog.value) {
            BasicAlertDialog(onDismissRequest = { showDialog.value = false }) {
                // Konten Dialog
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Ikon centang
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check_circle), // Ganti dengan ikon yang sesuai
                            contentDescription = "Success",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(64.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Teks berhasil
                        Text(
                            text = "Successfully Submitted!",
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Your report has been shared in the Lost Pet Info!",
                            fontFamily = customFontFamily,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Tombol View Report
                        Button(
                            onClick = {
                                // Navigasi ke halaman report
                                showDialog.value = false // Tutup dialog
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4B9B8))
                        ) {
                            Text(text = "View Report", fontFamily = customFontFamily, color = Color.White)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
fun submitLostPetData(lostPet: LostPet) {
    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("lostPets")

    // Push data ke database
    reference.push().setValue(lostPet)
        .addOnSuccessListener {
            // Data berhasil disimpan
            println("Data berhasil disimpan ke Firebase")
        }
        .addOnFailureListener {
            // Gagal menyimpan data
            println("Gagal menyimpan data ke Firebase: ${it.message}")
        }
}

@Composable
fun ChoiceButton(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(1.dp, if (isSelected) Color.Black else Color.Gray), // Border hitam jika dipilih
        shape = RoundedCornerShape(30.dp), // Sudut tombol bulat
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = customFontFamily,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.Black else Color.Gray // Teks hitam jika dipilih
        )
    }
}



@Composable
fun InputField(
    value: String, // Menambahkan value sebagai parameter
    onValueChange: (String) -> Unit, // Menambahkan onValueChange untuk update state
    modifier: Modifier = Modifier,
    label: String,
    isButton: Boolean = false,
    singleLine: Boolean = true
){

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        // Padding kiri untuk teks label
        Text(
            text = label,
            fontSize = 14.sp,
            fontFamily = customFontFamily,
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            modifier = Modifier.padding(start = 15.dp) // Tambahkan padding kiri
        )
        if (isButton) {
            Button(
                onClick = { /* Upload photo action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Upload Photo", color = Color.White, fontFamily = customFontFamily, fontWeight = FontWeight.Normal)
            }
        } else {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = modifier
                    .background(Color.Transparent),
                singleLine = singleLine,
                shape = RoundedCornerShape(30.dp), // Mengatur lengkungan outline menjadi 25 dp
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black
                )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewFormLostPet() {
    val mockNavController = rememberNavController()
    FormLostPet(navController = mockNavController, modifier = Modifier.padding(16.dp))
}