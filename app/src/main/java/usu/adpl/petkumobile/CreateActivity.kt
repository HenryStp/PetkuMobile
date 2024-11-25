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
import java.security.MessageDigest



class CreateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateAccountScreen()
        }
    }
}
val CustomFontFamily = FontFamily(
    Font(R.font.sen_reguler, FontWeight.Normal),
    Font(R.font.sen_medium, FontWeight.Medium),
    Font(R.font.sen_medium, FontWeight.Normal),
    Font(R.font.sen_bold, FontWeight.Bold),
    Font(R.font.sen_extrabold, FontWeight.ExtraBold)
)



@Composable
@Preview(showBackground = true)
fun CreateAccountScreen() {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFFFFF),    // Putih
            Color(0xFFF4B9B8),    // #F4B9B8
            Color(0xFF703E98)     // #703E98
        ),
        startY = 900.0f,
        endY = 2500.0f
    )

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    // Cek apakah semua field sudah terisi
    val isFormValid = username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create\nNew Account",
            fontSize = 30.sp,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(34.dp))

        // Username Field
        CustomTextField(
            value = username,
            onValueChange = { username = it },
            label = "Username"
        )

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

        // Password Confirmation Field
        CustomTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Password Confirmation",
            isPassword = true,
            passwordVisible = confirmPasswordVisible,
            onVisibilityChange = { confirmPasswordVisible = !confirmPasswordVisible }
        )

        Spacer(modifier = Modifier.height(24.dp))



        // Button for registration
        Button(
            onClick = {
                // Input validation
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                } else if (password != confirmPassword) {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                } else {
                    // Create user in Firebase Authentication
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Get the Firebase user ID
                                val userId = task.result.user?.uid ?: ""

                                // Save data in Firestore
                                val userData = hashMapOf(
                                    "username" to username,
                                    "email" to email

                                )

                                firestore.collection("users")
                                    .document(userId) // Use user ID as document ID
                                    .set(userData)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Account created successfully", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(context, HomeActivity::class.java).apply {
                                            putExtra("username", username) // Pass the username to HomeActivity
                                        }
                                        context.startActivity(intent)
                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(context, "Failed to save user data: ${exception.message}", Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                // Display error if registration fails
                                Toast.makeText(context, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White // Warna tombol diubah menjadi putih tanpa kondisi
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Get started",
                fontSize = 16.sp,
                color = Color.Black, // Teks tetap hitam untuk kontras dengan latar putih
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Bold
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Login Text
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Have an account? ",
                fontSize = 14.sp,
                color = Color.Black,
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            ClickableText(
                text = AnnotatedString("Login"),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(0xFF703E98),
                    fontFamily = CustomFontFamily,
                    fontWeight = FontWeight.Bold
                ),
                onClick = {
                    // Navigasi ke LoginActivity
                    context.startActivity(Intent(context, LoginActivity::class.java))
                }
            )
        }
    }
}


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onVisibilityChange: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val annotatedString = buildAnnotatedString {
            append(label) // Menambahkan teks label
            withStyle(style = SpanStyle(color = Color.Red)) {
                append(" *") // Menambahkan tanda * dengan warna merah
            }
        }
        Text(
            text = annotatedString,
            fontSize = 14.sp,
            color = Color.Gray,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.Bold
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(vertical = 8.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp)) // Warna latar belakang dan radius
                .border(1.dp, Color.Black, shape = RoundedCornerShape(16.dp)) // Border hitam dengan radius
                .padding(horizontal = 16.dp),
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(Modifier.weight(1f)) {
                        innerTextField()
                    }
                    if (isPassword) {
                        IconButton(onClick = onVisibilityChange) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        )
    }
}

