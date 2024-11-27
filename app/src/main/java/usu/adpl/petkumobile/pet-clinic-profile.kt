package usu.adpl.petkumobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController


@Composable
fun PetClinicProfileScreen(navController: NavController? = null) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.gambar_pet_clinic),
                contentDescription = "Pet Care Profile",
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(5.dp, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                contentScale = ContentScale.Crop
            )
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { /* Handle back action */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.back_white),
                            contentDescription = "Back",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Furry Friends Animal Clinic",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = CustomFontFamily
        )

        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "Address: Jl. Merpati No. 12, Medan",
            fontSize = 15.sp,
            fontFamily = CustomFontFamily
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF1F1F1))
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Contact Us:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = CustomFontFamily
                )
                Text(
                    text = "Phone: +62 813-5678-1234",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Social Media:",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
                Text(
                    text = "• Instagram: @furryfriendsclinic",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
                Text(
                    text = "• Facebook: /furryfriendsclinic",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Navigate to location */ },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(250.dp)
                .height(50.dp)
                .shadow(5.dp, RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF8C2C2)),
            elevation = null
        ) {
            Text(
                text = "See Location",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFontFamily
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPetClinicProfileScreen() {
    PetClinicProfileScreen()
}
