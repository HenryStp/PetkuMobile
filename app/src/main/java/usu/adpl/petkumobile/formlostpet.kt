@file:OptIn(ExperimentalMaterial3Api::class)
package usu.adpl.petkumobile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.border
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormLostPet() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
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
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Pet", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(start = 15.dp))
        // Pet type (Cat/Dog)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            ChoiceButton("Cat")
            ChoiceButton("Dog")
        }

        // Input fields for pet details
        InputField(label = "Photo", isButton = true, modifier = Modifier.fillMaxWidth())
        InputField(label = "Name", modifier = Modifier.fillMaxWidth())
        InputField(label = "Breed", modifier = Modifier.fillMaxWidth())
        InputField(label = "Age", modifier = Modifier.fillMaxWidth())

        Text(text = "Gender", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(start = 15.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            ChoiceButton("Female")
            ChoiceButton("Male")
        }

        InputField(label = "Weight", modifier = Modifier.fillMaxWidth())
        InputField(label = "Height", modifier = Modifier.fillMaxWidth())
        InputField(label = "Color & Features", modifier = Modifier.fillMaxWidth())

        // Input field for Last Seen Location with increased height
        InputField(label = "Last Seen Location", modifier = Modifier
            .fillMaxWidth()
            .height(100.dp))
        InputField(label = "Date & Time Lost", modifier = Modifier.fillMaxWidth())
        // Input field for Additional Information with increased height
        InputField(label = "Additional Information", singleLine = false, modifier = Modifier
            .fillMaxWidth()
            .height(120.dp))

        Spacer(modifier = Modifier.height(16.dp))

        // Owner contact information
        Text(text = "Owner Contact:", fontWeight = FontWeight.Bold)
        InputField(label = "Name", modifier = Modifier.fillMaxWidth())
        InputField(label = "Phone", modifier = Modifier.fillMaxWidth())
        InputField(label = "Email", modifier = Modifier.fillMaxWidth())
        InputField(label = "Instagram", modifier = Modifier.fillMaxWidth())
        InputField(label = "Reward (Optional)", modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        // Submit button
        Button(
            onClick = { /* Submit form data */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCDD2))
        ) {
            Text(
                text = "Submit",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ChoiceButton(text: String) {
    OutlinedButton(
        onClick = { /* Pilihan Cat atau Dog */ },
        border = BorderStroke(1.dp, Color.Black), // Border hitam
        shape = RoundedCornerShape(50), // Membuat sudut bundar
        modifier = Modifier.padding(4.dp) // Menambahkan sedikit padding jika diperlukan
    ) {
        Text(text = text, fontSize = 14.sp, color = Color.Black) // Warna teks hitam
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
            color = Color.Gray,
            modifier = Modifier.padding(start = 15.dp) // Tambahkan padding kiri
        )
        if (isButton) {
            Button(
                onClick = { /* Upload photo action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Upload Photo", color = Color.White)
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
    FormLostPet()
}
