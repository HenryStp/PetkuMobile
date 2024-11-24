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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavController

@Composable
fun PetClinicScreen(navController: NavController? = null) {
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
            items(6) {
                PetClinicItem(
                    imageId = R.drawable.gambar_pet_clinic,
                    title = "Furry Friends\nAnimal Clinic",
                    rating = "4.9",
                    consultation = "Consultation IDR 250.000",
                    location = "Jl. Merpati No. 12, Medan",
                    onClick = {
                        navController?.navigate("pet-clinic-profile")
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
    rating: String,
    consultation: String,
    location: String,
    fontFamily: FontFamily = CustomFontFamily,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .width(299.dp)
            .height(108.dp)
            .background(Color(0xFFFFE8D8), shape = RoundedCornerShape(20.dp))

    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "Pet Boarding Image",
            modifier = Modifier
                .size(108.dp)
                .clip(RoundedCornerShape(20.dp))
                .offset(x = (2).dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Text information
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 13.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Title
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                fontFamily = fontFamily,
            )

            // Rating and Price
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Rating Icon and Text
                Image(
                    painter = painterResource(id = R.drawable.ic_rating),
                    contentDescription = "Rating Icon",
                    modifier = Modifier.size(9.dp)
                )
                Text(
                    text = rating,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontFamily = fontFamily

                )
                Text(
                    text = "|",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontFamily = fontFamily
                )
                Text(
                    text = consultation,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontFamily = fontFamily
                )
            }

            // Location
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

