package usu.adpl.petkumobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import android.content.Intent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.LaunchedEffect
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController







data class PetShopData(val nama: String = "")
data class PetClinicData(val nama: String = "")

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("username") ?: "Unknown User"
        setContent {
            HomeScreen(username, navController = rememberNavController())
        }
    }
}

@Composable
fun SectionHeader(title: String, onSeeAllClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "see all",
            color = Color.Gray,
            fontSize = 10.sp,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { onSeeAllClick() } // Tambahkan aksi klik
        )
    }
}

@Composable
fun HomeScreen(username: String, navController: NavController) {
    val petShops = remember { mutableStateListOf<PetShopData>() }
    val petClinics = remember { mutableStateListOf<PetClinicData>() }

    LaunchedEffect(true) {
        // Ambil data Pet Shop dan Pet Clinic dari Firestore
        val db = FirebaseFirestore.getInstance()

        db.collection("pet_shop").get().addOnSuccessListener { result ->
            petShops.clear()
            for (document in result) {
                val petShop = document.toObject<PetShopData>()
                petShops.add(petShop)
            }
        }

        db.collection("pet_clinic").get().addOnSuccessListener { result ->
            petClinics.clear()
            for (document in result) {
                val petClinic = document.toObject<PetClinicData>()
                petClinics.add(petClinic)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // Menambahkan scrolling
    ) {
        GreetingSection(username)
        AddPetSection()
        ReminderSection()
        CategoriesSection()
        LostPetSection()
        PetShopSection(petShops = petShops)
        PetClinicSection(petClinics = petClinics)
    }
}

@Composable
fun GreetingSection(username: String) {
    Text(
        text = "Heloo, $username! 👋",
        fontSize = 20.sp,
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun AddPetSection() {
    val context = LocalContext.current // Mendapatkan konteks di dalam fungsi
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp) // Set height as needed
            .padding(vertical = 8.dp)
            .background(Color(0xFFD0B3EF), RoundedCornerShape(16.dp))
            .clickable { // Menambahkan event klik
                // Navigasi ke AddPetActivity
                val intent = Intent(context, ProfilScreen::class.java)
                context.startActivity(intent)
            }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add), // Replace with your actual icon resource name
                contentDescription = "Add Icon",
                tint = Color.White,
                modifier = Modifier.size(48.dp) // Adjust icon size as needed
            )
            Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
            Text(
                text = " Add Your Pet",
                fontSize = 15.sp,
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

@Composable
fun ReminderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Today Reminder",
            fontSize = 15.sp,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .border(5.dp, Color(0xFFEFEFEF), RoundedCornerShape(8.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No schedule yet!", color = Color.Gray, fontSize = 15.sp,fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Medium)
        }
    }
}


@Composable
fun CategoriesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Text( // Tambahkan judul "Category"
            text = "Category",
            fontSize = 15.sp,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CategoryItem(name = "Calendar", iconResId = R.drawable.calendar,destinationActivity = CalendarActivity::class.java)
            CategoryItem(name = "Service", iconResId = R.drawable.service,destinationActivity = MainActivity::class.java )
            CategoryItem(name = "Lost Pet", iconResId = R.drawable.lost_pet,destinationActivity = CalendarActivity::class.java)
        }
    }
}

@Composable
fun CategoryItem(name: String, iconResId: Int,destinationActivity : Class<*>) {
    val context = LocalContext.current

    Row( // Menggunakan Row agar ikon berada di samping teks
        verticalAlignment = Alignment.CenterVertically, // Menyelaraskan ikon dan teks secara vertikal
        modifier = Modifier
            .background(Color(0x703E9880), RoundedCornerShape(36.dp))
            .padding(10.dp)
            .clickable {
                val intent = Intent(context, destinationActivity) // Menggunakan parameter destinationActivity
                context.startActivity(intent)
            }
    ) {
        // Box untuk latar belakang putih pada ikon
        Box(
            modifier = Modifier
                .size(36.dp) // Sesuaikan ukuran latar belakang ikon
                .background(Color.White, shape = RoundedCornerShape(20.dp)), // Warna putih dengan bentuk bulat
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "$name Icon",
                modifier = Modifier.size(24.dp) // Sesuaikan ukuran ikon jika diperlukan
            )
        }
        Spacer(modifier = Modifier.width(3.dp)) // Memberi jarak antara ikon dan teks
        Text(text = name, color = Color.Black, fontSize = 13.sp, fontFamily = CustomFontFamily,
            fontWeight = FontWeight.SemiBold,)
    }
}

@Composable
fun LostPetSection() {
    SectionHeader(title = "Lost Pet")
    LazyRow {
        items(5) { // Dummy data count
            PetCard(name = "Luna - Siamese Cat")
        }
    }
}

@Composable
fun PetShopSection(petShops: List<PetShopData>) {
    val context = LocalContext.current // Mendapatkan konteks

    SectionHeader(
        title = "Pet Shop",
        onSeeAllClick = {
            val intent = Intent(context, PetShopActivity::class.java)
            context.startActivity(intent)
        }
    )
    LazyRow {
        items(petShops.size) { index ->
            ShopCard(name = petShops[index].nama,
                onClick = {
                val intent = Intent(context, PetClinicActivity::class.java)
                context.startActivity(intent)  // Arahkan ke PetClinicActivity
            })
        }
    }
}



@Composable
fun PetClinicSection(petClinics: List<PetClinicData>) {
    val context = LocalContext.current // Mendapatkan konteks

    SectionHeader(
        title = "Pet Clinic",
        onSeeAllClick = {
            val intent = Intent(context, PetClinicActivity::class.java)
            context.startActivity(intent)
        }
    )
    LazyRow {
        items(petClinics.size) { index -> // Pass the size of the list instead of the list itself
            val clinic = petClinics[index] // Use the item at this index
            ClinicCard(name = clinic.nama,
                onClick = {
                    val intent = Intent(context, PetClinicActivity::class.java)
                    context.startActivity(intent)  // Arahkan ke PetClinicActivity
                }
            ) // Pass the clinic name to the ClinicCard
        }
    }
}



@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 15.sp, fontFamily = CustomFontFamily,
            fontWeight = FontWeight.Medium)
        Text(text = "see all", color = Color.Gray, fontSize = 10.sp, fontFamily = CustomFontFamily,
            fontWeight = FontWeight.Medium)
    }
}

@Composable
fun PetCard(name: String) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .padding(8.dp)
            .background(Color(0xFFF3F1F5), RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Replace with actual image resource
            Image(painter = painterResource(id = R.drawable.pet), contentDescription = null, modifier = Modifier.size(60.dp))
            Text(text = name, fontSize = 8.sp, fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Medium, textAlign = TextAlign.Center, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun ShopCard(name: String, onClick: () -> Unit ) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .padding(4.dp)
            .background(Color(0x703E9880), RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize() // Column menyesuaikan dengan ukuran Box
        ) {
            Image(
                painter = painterResource(id = R.drawable.shop),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth() // Gambar memenuhi lebar kolom
                    .weight(1f) // Gambar mengambil ruang yang tersedia dalam kolom
                    .clip(RoundedCornerShape(16.dp)), // Tambahkan clipping agar sesuai dengan Box
                contentScale = ContentScale.Crop // Skala gambar
            )
            Text(
                text = name,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp) // Tambahkan jarak dari gambar ke teks
            )
        }
    }
}


@Composable
fun ClinicCard(name: String, onClick: () -> Unit ) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .padding(4.dp)
            .background(Color(0x703E9880), RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize() // Column menyesuaikan dengan ukuran Box
        ) {
            Image(
                painter = painterResource(id = R.drawable.clinic),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth() // Gambar memenuhi lebar kolom
                    .weight(1f) // Gambar mengambil ruang yang tersedia dalam kolom
                    .clip(RoundedCornerShape(16.dp)), // Tambahkan clipping agar sesuai dengan Box
                contentScale = ContentScale.Crop // Skala gambar
            )
            Text(
                text = name,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp) // Tambahkan jarak dari gambar ke teks
            )
        }
    }
}
@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    //HomeScreen(username = "User") // Memberikan nilai default "User" untuk preview
}