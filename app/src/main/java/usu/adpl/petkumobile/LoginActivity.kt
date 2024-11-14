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

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginActivityScreen()
        }
    }
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
    val firestore = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello,\n" +
                    "Welcome Back!",
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
                        firestore.collection("users")
                            .whereEqualTo("email", email)
                            .get()
                            .addOnSuccessListener { documents ->
                                if (!documents.isEmpty) {
                                    val document = documents.first()
                                    val storedPassword = document.getString("password")
                                    val username = document.getString("username") // Ambil username dari database


                                    if (storedPassword == password) {
                                        val userId = document.id // Ambil user id dari dokumen

                                        // Login successful
                                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()

                                        // Buat Intent untuk membuka HomeActivity
                                        val intent = Intent(context, HomeActivity::class.java).apply {
                                            putExtra("USER_ID", userId) // Tambahkan user id ke intent
                                            putExtra("USERNAME", username) // Tambahkan username ke intent
                                        }

                                        context.startActivity(intent)
                                    } else {
                                        Toast.makeText(context, "Incorrect password", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    Toast.makeText(context, "Email not found", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
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
