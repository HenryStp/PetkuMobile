package usu.adpl.petkumobile

import android.os.Bundle
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.buildAnnotatedString
import com.google.firebase.auth.FirebaseAuth
import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider



class LoginActivity : ComponentActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private val GOOGLE_SIGN_IN_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginActivityScreen()
        }

        // Google Sign-In Configuration
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    @Composable
    @Preview(showBackground = true)
    fun LoginActivityScreen() {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var confirmPasswordVisible by remember { mutableStateOf(false) }

        val gradient = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFFFFFF), // Putih
                Color(0xFFF4B9B8), // #F4B9B8
                Color(0xFF703E98)  // #703E98
            ),
            startY = 900.0f,
            endY = 2500.0f
        )

        val context = LocalContext.current
        val firebaseAuth = FirebaseAuth.getInstance()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hello,\nWelcome Back!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(34.dp))

            // Email Field
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email"
            )

            // Password Field
            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                isPassword = true,
                passwordVisible = passwordVisible,
                onVisibilityChange = { passwordVisible = !passwordVisible }
            )

            // Confirm Password Field
            CustomTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Konfirmasi Password",
                isPassword = true,
                passwordVisible = confirmPasswordVisible,
                onVisibilityChange = { confirmPasswordVisible = !confirmPasswordVisible }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                        if (password == confirmPassword) {
                            firebaseAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val user = firebaseAuth.currentUser
                                        val userId = user?.uid
                                        val firestore = FirebaseFirestore.getInstance()

                                        if (userId != null) {
                                            firestore.collection("users").document(userId).get()
                                                .addOnSuccessListener { document ->
                                                    if (document != null && document.exists()) {
                                                        val username = document.getString("username") ?: "Unknown User"

                                                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                                                        saveLoginData(context, username, userId)
                                                        val intent = Intent(context, HomeActivity::class.java)
                                                        intent.putExtra("username", username)
                                                        intent.putExtra("userId", userId)
                                                        context.startActivity(intent)
                                                    } else {
                                                        Toast.makeText(context, "User data not found", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                                .addOnFailureListener {
                                                    Toast.makeText(context, "Failed to fetch user data", Toast.LENGTH_SHORT).show()
                                                }
                                        } else {
                                            Toast.makeText(context, "User UID not found", Toast.LENGTH_SHORT).show()
                                        }

                                    } else {
                                        Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Toast.makeText(context, "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show()
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Login",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Create Account Text
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? ",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                ClickableText(
                    text = AnnotatedString("Create account"),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color(0xFF703E98),
                        fontWeight = FontWeight.Bold
                    ),
                    onClick = {
                        context.startActivity(Intent(context, CreateActivity::class.java))
                    }
                )
            }
        }
    }



    fun saveLoginData(context: Context, username: String, userId: String) {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isUserLoggedIn", true)
        editor.putString("username", username)
        editor.putString("userId", userId)
        editor.apply()
    }
}






