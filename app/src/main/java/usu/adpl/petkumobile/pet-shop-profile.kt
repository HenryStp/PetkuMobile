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
fun PetShopProfileScreen(navController: NavController? = null) {
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
                painter = painterResource(id = R.drawable.gambar_pet_shop),
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
            text = "Pet Paradise Store",
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
            text = "Distance: 2.5 km",
            fontSize = 15.sp,
            fontFamily = CustomFontFamily
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Address: Jl. Kucing No. 5, Medan",
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
            text = ("Pet Paradise Store is your one-stop shop for all things pet-related. " +
                    "We offer a wide selection of pet food, toys, accessories, grooming supplies, " +
                    "and more for your beloved furry friends. Whether you're looking for high-quality " +
                    "nutrition, fun toys, or essential supplies, we've got everything you need to keep " +
                    "your pets healthy and happy. Our friendly and knowledgeable staff are always on hand " +
                    "to offer advice and help you find the best products for your pet."),
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
            ListItemPetShop(text = "Wide Range of Products: From premium pet food brands to exciting toys and stylish accessories.")
            ListItemPetShop(text = "Delivery Service: Convenient home delivery options available for all your pet care needs.")
            ListItemPetShop(text = "Special Deals: Frequent discounts and promotions on a variety of pet supplies.")
            ListItemPetShop(text = "Personalized Recommendations: Our staff can recommend products tailored to your pet's unique needs.")
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
                    text = "Owner: Linda Setiawan",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
                Text(
                    text = "Phone: +62 812-9876-5432",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
                Text(
                    text = "Email: linda.setiawan@petparadisestore.com",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
                Text(
                    text = "Website: www.petparadisestore.com",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
                Text(
                    text = "Social Media:",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
                Text(
                    text = "• Instagram: @petparadisestore",
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily
                )
                Text(
                    text = "• Facebook: /petparadisestore",
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
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFC2E8D6))
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
fun ListItemPetShop(text: String) {
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
fun PreviewPetShopProfileScreen() {
    PetShopProfileScreen()
}
