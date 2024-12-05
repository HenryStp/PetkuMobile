package usu.adpl.petkumobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.firebase.database.FirebaseDatabase


data class LostPet(
    val documentId: String = "",
    val name: String = "",
    val petType: String = "",
    val breed: String = "",
    val age: String = "",
    val weight: String = "",
    val height: String = "",
    val gender: String = "",
    val colorAndFeatures: String = "",
    val lastSeenLocation: String = "",
    val dateTimeLost: String = "",
    val additionalInfo: String = "",
    val ownerName: String = "",
    val ownerPhone: String = "",
    val ownerEmail: String = "",
    val ownerInstagram: String = "",
    val reward: String = "",
)

class LostPetViewModel : ViewModel() {
    private val _lostPetData = MutableStateFlow<LostPet?>(null)
    val lostPetData: StateFlow<LostPet?> = _lostPetData.asStateFlow()

    fun fetchLostPetData(documentId: String) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("lostPets").child(documentId)

        reference.get().addOnSuccessListener { snapshot ->
            snapshot?.let {
                _lostPetData.value = snapshot.getValue(LostPet::class.java)
            }
        }.addOnFailureListener {
            // Tangani error jika ada
            _lostPetData.value = null
        }
    }
}


