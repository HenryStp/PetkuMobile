package usu.adpl.petkumobile

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

data class LostPet(
    val type: String,
    val breed: String,
    val age: String,
    val size: String,
    val gender: String,
    val distinctiveFeatures: String,
    val lastSeenLocation: String,
    val dateLost: String,
    val additionalInfo: String,
    val ownerName: String,
    val ownerPhone: String,
    val ownerEmail: String,
    val ownerSocialMedia: String,
    val reward: String
)

class LostPetViewModel : ViewModel() {
    var lostPet by mutableStateOf<LostPet?>(null)

    fun saveLostPet(data: LostPet) {
        lostPet = data
    }
}
