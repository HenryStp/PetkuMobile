package usu.adpl.petkumobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class UserViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    val username: LiveData<String> = savedStateHandle.getLiveData("username")
    val userId: LiveData<String> = savedStateHandle.getLiveData("userId")

    fun saveUserData(username: String, userId: String) {
        savedStateHandle.set("username", username)
        savedStateHandle.set("userId", userId)
    }
}




