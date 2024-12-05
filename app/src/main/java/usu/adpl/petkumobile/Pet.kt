package usu.adpl.petkumobile

//import android.os.Parcelable
//
//@Parcelize
data class PetData(
    val name: String?,
    val breed: String?,
    val age: String?,
    val weight: Int?,
    val height: Int?,
    val medicalInfo: String?,
    val additionalInfo: String?,
    val avatar: Int?,
    val gender: String, // Menambahkan gender ke data model
    val userId: String?
)