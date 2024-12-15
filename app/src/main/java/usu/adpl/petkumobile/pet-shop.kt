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
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.os.Bundle
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import android.app.Activity

data class PetShop(
    val nama: String = "",
    val alamat: String = "",
    val telepon: String = "",
    val instagram: String = "",
    val link: String = "",
    val gambar: String = ""
)

class PetShopActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetShopScreen()
        }
    }
}
@Composable
fun PetShopScreen(navController: NavController? = null) {
    val context = LocalContext.current
    val firestore = FirebaseFirestore.getInstance()
    val petShopList = remember { mutableStateListOf<PetShop>() }

    LaunchedEffect(Unit) {
        firestore.collection("pet_shop")
            .get()
            .addOnSuccessListener { documents ->
                petShopList.clear()
                for (document in documents) {
                    val name = document.getString("nama") ?: ""
                    val address = document.getString("alamat") ?: ""
                    val phone = document.getString("telepon") ?: ""
                    val instagram = document.getString("instagram") ?: ""
                    val link = document.getString("link") ?: ""
                    val gambarNama = document.getString("gambar") ?: ""

                    // Tambahkan data ke daftar
                    petShopList.add(
                        PetShop(
                            nama = name,
                            alamat = address,
                            telepon = phone,
                            instagram = instagram,
                            link = link,
                            gambar = gambarNama // Simpan nama gambar dari database
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
                    .clickable { (context as? Activity)?.onBackPressed()  }
            )
            Text(
                text = "Pet Shop",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                fontFamily = CustomFontFamily
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(petShopList) { petShop ->
                // Konversi nama gambar menjadi resource ID
                val imageResId = context.resources.getIdentifier(
                    petShop.gambar, // Nama gambar dari database
                    "drawable",
                    context.packageName
                ).takeIf { it != 0 } ?: R.drawable.shop // Gunakan gambar default jika tidak ditemukan

                PetShopItem(
                    imageId = imageResId, // Mengirimkan resource ID
                    title = petShop.nama,
                    location = petShop.alamat,
                    onItemClick = {
                        val intent = Intent(context, PetShopProfileActivity::class.java).apply {
                            putExtra("nama", petShop.nama)
                            putExtra("alamat", petShop.alamat)
                            putExtra("telepon", petShop.telepon)
                            putExtra("instagram", petShop.instagram)
                            putExtra("link", petShop.link)
                            putExtra("gambar", petShop.gambar) // Tetap kirim String untuk activity berikutnya
                        }
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun PetShopItem(
    imageId: Int, // Resource ID
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
            .background(Color(0xFFE5F2EC), shape = RoundedCornerShape(20.dp))
            .clickable { onItemClick() }
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "Pet Shop Image",
            modifier = Modifier
                .size(110.dp) // Sesuaikan ukuran gambar agar sesuai dengan kotak
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

/*
@Composable
fun PetShopItem(
    imageId: String, // Ubah ke String
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
            .background(Color(0xFFE5F2EC), shape = RoundedCornerShape(20.dp))
            .clickable { onItemClick() }
    ) {
        // Konversi kembali ke Int untuk resource ID
        val imageResId = imageId.toIntOrNull() ?: R.drawable.pet

        // Gambar tetap ditampilkan
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Pet Shop Image",
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
*/


@Preview(showBackground = true)
@Composable
fun PreviewPetShopScreen() {

}
