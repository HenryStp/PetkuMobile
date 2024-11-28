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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormLostPet(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState() // Mengingat status scroll
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
            IconButton(onClick = { /* Kembali ke halaman sebelumnya */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
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
        val selectedPetType = remember { mutableStateOf("Cat") }
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
        InputField(label = "Photo", isButton = true, modifier = Modifier.fillMaxWidth())
        InputField(label = "Name", modifier = Modifier.fillMaxWidth())
        InputField(label = "Breed", modifier = Modifier.fillMaxWidth())
        InputField(label = "Age", modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Gender", fontSize = 14.sp,
            fontFamily = customFontFamily,
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            modifier = Modifier.padding(start = 15.dp))

        val selectedGender = remember { mutableStateOf("Female") }
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


        InputField(label = "Weight", modifier = Modifier.fillMaxWidth())
        InputField(label = "Height", modifier = Modifier.fillMaxWidth())
        InputField(label = "Color & Features", modifier = Modifier.fillMaxWidth())

        // Input field for Last Seen Location with increased height
        InputField(label = "Last Seen Location", singleLine = false, modifier = Modifier
            .fillMaxWidth()
            .height(120.dp))
        InputField(label = "Date & Time Lost", modifier = Modifier.fillMaxWidth())
        // Input field for Additional Information with increased height
        InputField(label = "Additional Information", singleLine = false, modifier = Modifier
            .fillMaxWidth()
            .height(120.dp))

        Spacer(modifier = Modifier.height(16.dp))

        // Owner contact information
        Text(text = "Owner Contact:",fontFamily = customFontFamily, fontWeight = FontWeight.Bold)
        InputField(label = "Name", modifier = Modifier.fillMaxWidth())
        InputField(label = "Phone", modifier = Modifier.fillMaxWidth())
        InputField(label = "Email", modifier = Modifier.fillMaxWidth())
        InputField(label = "Instagram", modifier = Modifier.fillMaxWidth())
        InputField(label = "Reward (Optional)", modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        // Submit button
        Button(
            onClick = { /* Submit form data */ },
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
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
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
fun InputField(label: String, isButton: Boolean = false, singleLine: Boolean = true, modifier: Modifier = Modifier) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

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
                value = textState.value,
                onValueChange = { textState.value = it },
                modifier = modifier
                    .background(Color.Transparent),
                singleLine = singleLine,
                shape = RoundedCornerShape(30.dp), // Mengatur lengkungan outline menjadi 25 dp
                colors = TextFieldDefaults.outlinedTextFieldColors(
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
    FormLostPet(modifier = Modifier.padding(16.dp)) // Gunakan padding default untuk preview
}
