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
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import android.app.Activity

class PetShopProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Ambil data yang dikirimkan melalui Intent
        val nama = intent.getStringExtra("nama") ?: "Unknown"
        val alamat = intent.getStringExtra("alamat") ?: "Alamat tidak tersedia"
        val telepon = intent.getStringExtra("telepon") ?: "Telepon tidak tersedia"
        val instagram = intent.getStringExtra("instagram") ?: "Instagram tidak tersedia"
        val link = intent.getStringExtra("link") ?: ""

        setContent {
            val navController = rememberNavController() // NavController untuk navigasi lokal
            PetShopProfileScreen(
                nama = nama,
                alamat = alamat,
                telepon = telepon,
                instagram = instagram,
                link = link,
                navController = navController
            )
        }
    }
}

@Composable
fun PetShopProfileScreen(
    nama: String,
    alamat: String,
    telepon: String,
    instagram: String,
    link: String,
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val context = androidx.compose.ui.platform.LocalContext.current

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
                contentDescription = "Pet Shop Profile",
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
                    IconButton(onClick = { (context as? Activity)?.finish()  }) {
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
            text = nama.ifEmpty { "Unknown Pet Shop" },
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = CustomFontFamily,
            color = Color(0xFF4A4A4A)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 3.dp,
            shape = RoundedCornerShape(10.dp)

        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "Address Icon",
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF6A1B9A)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = alamat,
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily,
                    color = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 3.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_phone),
                    contentDescription = "Phone Icon",
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF388E3C)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = telepon,
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily,
                    color = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 3.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_instagram),
                    contentDescription = "Instagram Icon",
                    modifier = Modifier.size(24.dp),
                    contentScale = ContentScale.Fit // Menjaga proporsi asli gambar
                )

                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = instagram,
                    fontSize = 15.sp,
                    fontFamily = CustomFontFamily,
                    color = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = {
                if (link.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Link tidak tersedia", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(250.dp)
                .height(50.dp)
                .shadow(5.dp, RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFC2E8D6)),
            elevation = null
        ) {
            Text(
                text = "See Location",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFontFamily,
                color = Color.Black
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPetShopProfileScreen() {

}
