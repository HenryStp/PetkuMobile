package usu.adpl.petkumobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.foundation.lazy.items
import android.net.Uri

data class PetClinic(
    val nama: String = "",
    val alamat: String = "",
    val telepon: String = "",
    val instagram: String = "",
    val link: String = ""
)


@Composable
fun PetClinicScreen(navController: NavController? = null) {
        val firestore = FirebaseFirestore.getInstance()
        val petClinicList = remember { mutableStateListOf<PetBoarding>() }

        // Fetch data from Firestore
        LaunchedEffect(Unit) {
            firestore.collection("pet_clinic")
                .get()
                .addOnSuccessListener { documents ->
                    petClinicList.clear()
                    for (document in documents) {
                        val name = document.getString("nama") ?: ""
                        val address = document.getString("alamat") ?: ""
                        val phone = document.getString("telepon") ?: ""
                        val instagram = document.getString("instagram") ?: ""
                        val link = document.getString("link") ?: ""

                        // Add PetBoarding item with complete data
                        petClinicList.add(
                            PetBoarding(
                                nama = name,
                                alamat = address,
                                telepon = phone,
                                instagram = instagram,
                                link = link
                            )
                        )
                    }
                }
                .addOnFailureListener {
                    // Handle error
                }
        }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_black),
                contentDescription = "Back",
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterStart)
                    .clickable { navController?.popBackStack() }
            )
            Text(
                text = "Pet Clinic",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                fontFamily = CustomFontFamily
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        // List of pet boarding options
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(petClinicList) { petClinic ->
                PetClinicItem(
                    imageId = R.drawable.gambar_pet_clinic,
                    title = petClinic.nama,
                    location = petClinic.alamat,
                    onItemClick = {
                        // Navigasi ke halaman detail pet boarding
                        navController?.navigate(
                            "pet-clinic-profile/${
                                Uri.encode(petClinic.nama)
                            }/${
                                Uri.encode(petClinic.alamat)
                            }/${
                                Uri.encode(petClinic.telepon)
                            }/${
                                Uri.encode(petClinic.instagram)
                            }/${
                                Uri.encode(petClinic.link)
                            }"

                        )
                    }
                )
            }
        }
    }
}

@Composable
fun PetClinicItem(
    imageId: Int,
    title: String,
    location: String,
    fontFamily: FontFamily = CustomFontFamily,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .width(299.dp)
            .height(108.dp)
            .shadow(5.dp, RoundedCornerShape(16.dp))
            .background(Color(0xFFF8C2C1), shape = RoundedCornerShape(20.dp))
            .clickable { onItemClick() }
    ) {
        // Gambar tetap ditampilkan
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "Pet Boarding Image",
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 13.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                fontFamily = fontFamily,
            )

            Spacer(modifier = Modifier.width(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "Location Icon",
                    modifier = Modifier.size(10.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = location,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontFamily = fontFamily
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPetClinicScreen() {
    PetClinicScreen()
}

