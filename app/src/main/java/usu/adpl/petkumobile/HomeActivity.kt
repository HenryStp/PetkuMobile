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
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.shape.CircleShape
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import androidx.compose.foundation.lazy.items
import com.google.firebase.auth.FirebaseAuth

data class PetShopData(
    val nama: String = "",
    val alamat: String = "",
    val telepon: String = "",
    val instagram: String = "",
    val link: String = "",
    val gambar: String = ""
)
data class PetClinicData(
    val nama: String = "",
    val alamat: String = "",
    val telepon: String = "",
    val instagram: String = "",
    val link: String = "",
    val gambar: String = ""
)


class HomeActivity : ComponentActivity() {

    private var backPressedTime: Long = 0 // Waktu saat tombol back pertama kali ditekan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId: String = intent.getStringExtra("userId") ?: "Unknown User"
        val username = intent.getStringExtra("username") ?: "Unknown User"
        setContent {
            HomeScreen(username, userId, navController = rememberNavController())
        }
    }
    override fun onBackPressed() {
        // Cek apakah tombol back sudah ditekan dua kali dalam waktu 2 detik
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()  // Panggil super untuk menyelesaikan proses default
            finishAffinity() // Menutup aplikasi dan semua activity        } else {
        } else {
            // Jika belum, tampilkan toast untuk memberi tahu pengguna untuk menekan sekali lagi untuk keluar
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis() // Simpan waktu saat tombol back pertama kali ditekan
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
fun HomeScreen(username: String, userId: String, navController: NavController) {
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
        GreetingSection(username, userId)
        AddPetSection(userId)
        ReminderSection()
        CategoriesSection(userId)
        LostPetSection(userId)
        PetShopSection(petShops = petShops)
        PetClinicSection(petClinics = petClinics)
    }
}

@Composable
fun GreetingSection(username: String, userId: String) {
    Text(
        text = "Heloo, $username! 👋",
        fontSize = 20.sp,
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun AddPetSection(userId: String) {
    val db = FirebaseDatabase.getInstance()
    val petsRef = db.reference.child("pets")
    val pets = remember { mutableStateListOf<Map<String, Any>>() }
    val context = LocalContext.current // Mendapatkan konteks yang benar dalam Compose

    // Fetch pets from Firebase Realtime Database
    LaunchedEffect(userId) {
        petsRef.orderByChild("userId").equalTo(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    pets.clear()
                    for (child in snapshot.children) {
                        val pet = child.value as? Map<String, Any> ?: continue
                        pets.add(pet)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("AddPetSection", "Error fetching pets: ${error.message}")
                }
            })
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp) // Adjust height if needed
                .padding(vertical = 4.dp)
                .background(Color(0xFFD0B3EF), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (pets.isEmpty()) {
                // If no pet exists, show Add Pet button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(context, ProfilScreen::class.java).apply {
                                putExtra("userId", userId)
                            }
                            context.startActivity(intent)
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add), // Replace with your icon resource
                        contentDescription = "Add Icon",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = " Add Your Pet",
                        fontSize = 15.sp,
                        fontFamily = CustomFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            } else {
                // If pets exist, display pet's avatar and name
                val pet = pets.first() // Assuming user can have only one pet
                val petName = pet["name"] as? String ?: "Unknown"
                val petAvatar = (pet["avatar"] as? Number)?.toInt() ?: 0
                val avatarName = "avatar$petAvatar"

                // Get avatar resource ID
                val avatarResourceId = getDrawableResourceId(context, avatarName)

                // Box wrapping both avatar and pet name
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(12.dp)) // Warna putih dengan transparansi 80%
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                )
                {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(60.dp)

                        ) {
                            if (avatarResourceId != 0) {
                                Image(
                                    painter = painterResource(id = avatarResourceId),
                                    contentDescription = "Pet Avatar",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {
                                            // Membuat Intent untuk membuka DisplayPet
                                            val intent = Intent(context, DisplayPetActivity::class.java).apply {
                                                putExtra("userId", userId)
                                            }
                                            context.startActivity(intent) // Menjalankan aktivitas DisplayPet
                                        }
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_instagram), // Replace with placeholder
                                    contentDescription = "Placeholder Avatar",
                                    modifier = Modifier.fillMaxSize(),
                                    tint = Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        // Pet Name
                        Text(
                            text = petName,
                            fontSize = 18.sp,
                            fontFamily = CustomFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

// Helper function to retrieve drawable resource ID
private fun getDrawableResourceId(context: android.content.Context, name: String): Int {
    return try {
        context.resources.getIdentifier(name, "drawable", context.packageName)
    } catch (e: Exception) {
        Log.e("AddPetSection", "Error loading avatar image: ${e.message}")
        0
    }
}

@Preview
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
fun CategoriesSection(userId: String) {
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
            CategoryItem(name = "Schedule", iconResId = R.drawable.calendar,destinationActivity = CalendarActivity::class.java, userId = userId)
            CategoryItem(name = "Service", iconResId = R.drawable.service,destinationActivity = PetServiceActivity::class.java, userId = userId )
            CategoryItem(name = "Lost Pet", iconResId = R.drawable.lost_pet,destinationActivity = LostPet2Activity::class.java, userId = userId)
        }
    }
}

@Composable
fun CategoryItem(name: String, iconResId: Int,destinationActivity : Class<*>, userId: String) {
    val context = LocalContext.current

    Row( // Menggunakan Row agar ikon berada di samping teks
        verticalAlignment = Alignment.CenterVertically, // Menyelaraskan ikon dan teks secara vertikal
        modifier = Modifier
            .background(Color(0x703E9880), RoundedCornerShape(36.dp))
            .padding(10.dp)
            .clickable {
                val intent = Intent(context, destinationActivity) // Menggunakan parameter destinationActivity
                intent.putExtra("userId", userId)
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
fun LostPetSection(userId: String) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val currentUserId = auth.currentUser?.uid
    val lostPets = remember { mutableStateListOf<LostPet>() }
    val isLoading = remember { mutableStateOf(true) }

    // Fetch data from Firebase
    LaunchedEffect(Unit) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("lostPets")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lostPets.clear()
                for (data in snapshot.children) {
                    val pet = data.getValue(LostPet::class.java)
                    if (pet != null && pet.userId != currentUserId) {
                        lostPets.add(pet.copy(documentId = data.key ?: ""))
                    }
                }
                isLoading.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("LostPetSection", "Failed to fetch data: ${error.message}")
                isLoading.value = false
            }
        })
    }

    Column {
        SectionHeader(
            title = "Lost Pet",
            onSeeAllClick = {
                // Dari LostPet
                val intent = Intent(context, LostPet2Activity::class.java).apply {
                    putExtra("userId", userId) // Kirim userId
                }
                context.startActivity(intent)

            }
        )

        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (lostPets.isEmpty()) {
            Text("No lost pets found.", modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyRow {
                items(lostPets.take(5)) { pet -> // Batasi hanya 5 PetCard
                    PetCard(
                        name = pet.name ?: "Unknown Pet",
                        petType = pet.petType,
                        status = pet.status,
                        onClick = {
                            val intent = Intent(context, LostPetDetailActivity::class.java).apply {
                                putExtra("documentId", pet.documentId)
                            }
                            context.startActivity(intent)
                        }
                    )
                }
            }
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
        items(petShops.take(5).size) { index ->
            val shop = petShops[index]
            // Konversi nama gambar menjadi resource ID
            val imageResId = context.resources.getIdentifier(
                shop.gambar, // Nama gambar dari database
                "drawable",
                context.packageName
            ).takeIf { it != 0 } ?: R.drawable.shop

            ShopCard(
                name = shop.nama,
                imageResId = imageResId, // Parameter yang sesuai
                onClick = {
                    val intent = Intent(context, PetShopProfileActivity::class.java)
                    intent.putExtra("nama", shop.nama)
                    intent.putExtra("alamat", shop.alamat)
                    intent.putExtra("telepon", shop.telepon)
                    intent.putExtra("instagram", shop.instagram)
                    intent.putExtra("link", shop.link)
                    intent.putExtra("gambar", shop.gambar)
                    context.startActivity(intent)
                }
            )

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
        items(petClinics.take(5).size) { index -> // Pass the size of the list instead of the list itself
            val clinic = petClinics[index] // Use the item at this index

            val imageResId = context.resources.getIdentifier(
                clinic.gambar, // Nama gambar dari database
                "drawable",
                context.packageName
            ).takeIf { it != 0 } ?: R.drawable.clinic

            ClinicCard(
                name = clinic.nama,
                imageResId = imageResId,
                onClick = {
                    val intent = Intent(context, PetClinicProfileActivity::class.java)
                    intent.putExtra("nama", clinic.nama)
                    intent.putExtra("alamat", clinic.alamat)
                    intent.putExtra("telepon", clinic.telepon)
                    intent.putExtra("instagram", clinic.instagram)
                    intent.putExtra("link", clinic.link)
                    intent.putExtra("gambar", clinic.gambar)
                    context.startActivity(intent)
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
fun PetCard(name: String, petType: String, status: String, onClick: () -> Unit) {
    val backgroundColor = when {
        status.equals("FOUNDED", ignoreCase = true) -> Color(0xFFB9F6CA) // Hijau muda jika statusnya "founded"
        else -> Color(0xFFFFBFBF) // Merah muda jika bukan keduanya
    }

    Box(
        modifier = Modifier
            .fillMaxWidth() // Sesuaikan dengan ukuran layar atau parent container
            .padding(4.dp)
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize() // Column menyesuaikan ukuran Box
        ) {
            val petImage = when (petType.toLowerCase()) {
                "cat" -> R.drawable.cat_default // Gambar default kucing
                "dog" -> R.drawable.dog_default // Gambar default anjing
                else -> R.drawable.pet // Gambar default lainnya
            }
            Image(
                painter = painterResource(id = petImage),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp) // Sesuaikan ukuran gambar agar lebih kecil
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop // Skala gambar
            )
            Text(
                text = name,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(1.dp) // Tambahkan padding yang cukup
            )
            Text(
                text = status,
                fontSize = 8.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = when {
                    status.equals("FOUNDED", ignoreCase = true) -> Color.Green
                    status.equals("STILL MISSING", ignoreCase = true) -> Color.Red
                    else -> Color.Black // Default warna teks jika status lain
                },
                modifier = Modifier.padding(2.dp) // Padding tambahan untuk teks status
            )
        }
    }
}

@Composable
fun ShopCard(name: String, imageResId: Int, onClick: () -> Unit ) {
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
                painter = painterResource(id = imageResId), // Menggunakan imageResId
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
fun ClinicCard(name: String, imageResId: Int, onClick: () -> Unit ) {
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
                painter = painterResource(id = imageResId), // Menggunakan imageResId
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