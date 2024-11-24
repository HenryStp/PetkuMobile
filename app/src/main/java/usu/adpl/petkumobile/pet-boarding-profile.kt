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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController


@Composable
fun PetBoardingProfileScreen(navController: NavController? = null) {
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
                painter = painterResource(id = R.drawable.gambar_pet_boarding),
                contentDescription = "Pet Care Profile",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
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
            text = "Happy Paws Pet Hotel",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = CustomFontFamily
        )

        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Rating: 4.8",
                fontSize = 15.sp,
                fontFamily = CustomFontFamily
            )
            Spacer(modifier = Modifier.width(4.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_rating),
                contentDescription = "Star Icon",
                modifier = Modifier.size(15.dp)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Price: IDR 150,000/day",
            fontSize = 15.sp,
            fontFamily = CustomFontFamily
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Distance: 2.5 km",
            fontSize = 15.sp,
            fontFamily = CustomFontFamily
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Address: Jl. Mawar No. 10, Medan",
            fontSize = 15.sp,
            fontFamily = CustomFontFamily
        )

        Spacer(modifier = Modifier.height(16.dp))

//About Us
        Text(
            text = "About Us:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = CustomFontFamily
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = ("Happy Paws Pet Hotel is dedicated to providing a safe, comfortable, and loving environment for your furry friends. Our spacious rooms are designed to ensure your pet feels at home while you're away. We offer daily walks and plenty of playtime to keep your pets happy and engaged."),
            textAlign = TextAlign.Justify,
            lineHeight = 20.sp,
            fontSize = 15.sp,
            fontFamily = CustomFontFamily
        )

        Spacer(modifier = Modifier.height(16.dp))

//Special Features
        Text(
            text = "Special Features:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = CustomFontFamily
        )
        Spacer(modifier = Modifier.height(3.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            ListItemPetBoarding(text = "Senior Pet Care: Special attention and tailored services for older pets, ensuring they receive the care they need.")
            ListItemPetBoarding(text = "Professional Staff: Our trained and caring staff members are available 24/7 to ensure your pets are well looked after.")
            ListItemPetBoarding(text = "Enrichment Activities: Daily activities and play sessions designed to stimulate your pet mentally and physically.")
            ListItemPetBoarding(text = "Hygienic Facilities: Clean and well-maintained rooms, with regular sanitation protocols for your pet's safety.")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF1F1F1))
                .padding(16.dp)
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
                    text = "Owner: Sarah Johnson",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
                Text(
                    text = "Phone: +62 812-3456-7890",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
                Text(
                    text = "Email: sarah.johnson@happypawshotel.com",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
                Text(
                    text = "Website: www.happypawshotel.com",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
                Text(
                    text = "Social Media:",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
                Text(
                    text = "• Instagram: @happypawshotel",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
                Text(
                    text = "• Facebook: /happypawshotel",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Navigate to location */ },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFD7C2))
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

@Composable
fun ListItemPetBoarding(text: String) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "• ",
            fontSize = 15.sp,
            fontFamily = CustomFontFamily
        )
        Text(
            text = text,
            lineHeight = 20.sp,
            fontSize = 15.sp,
            fontFamily = CustomFontFamily
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPetBoardingProfileScreen() {
    PetBoardingProfileScreen()
}
