package com.samuelwood.trips

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.samuelwood.trips.ui.theme.TripsTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TripsTheme {
                // --- This is simplified to show the idea ---
                // You would typically use Compose Navigation here
                // and have a NavController manage which screen is shown.

                // For now, let's pretend we have a simple state
                // to switch between screens for demonstration
                var currentScreen by remember { mutableStateOf<Screen>(Screen.SignUp) }

                when (currentScreen) {
                    is Screen.SignUp -> {
                        SignUpView(
                            onSignUpSuccess = {
                                // When sign up is successful, go to the main app screen
                                currentScreen = Screen.MainApp // Or login screen, depending on flow
                            },
                            onLoginClick = {
                                // When login button is clicked in SignUpView, go to Login screen
                                currentScreen = Screen.Login
                            }
                            // You don't need onForgotPasswordClick here as it's not in SignUpView's signature
                        )
                    }
                    is Screen.Login -> {
                        // You would call your LoginView here
                        // LoginView(
                        //     onLoginSuccess = { currentScreen = Screen.MainApp },
                        //     onSignUpClick = { currentScreen = Screen.SignUp },
                        //     onForgotPasswordClick = { currentScreen = Screen.ForgotPassword }
                        // )
                    }
                    is Screen.MainApp -> {
                        // Show your main app content (e.g., the Greeting or a list of trips)
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Greeting(
                                name = "Android", // Or user's name from auth.currentUser
//                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                    // Add other screens like ForgotPassword
                    // is Screen.ForgotPassword -> { /* Forgot Password Composable */ }
                }
                // --- End simplified demonstration ---
            }
        }
        auth = Firebase.auth // Keep auth initialization
    }
}

// A sealed class or enum to represent your screens
sealed class Screen {
    object SignUp : Screen()
    object Login : Screen()
    object MainApp : Screen()
    // object ForgotPassword : Screen()
}



@Composable
fun SignUpView(onSignUpSuccess: () -> Unit,  onLoginClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var signUpError by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()


    Scaffold { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Sign Up", style = MaterialTheme.typography.headlineMedium)

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            if (signUpError.isNotEmpty()) {
                Text(signUpError, color = Color.Red)
            }

            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
//                        if (password == confirmPassword) {
//                            signUp(email, password, onSignUpSuccess = {onSignUpSuccess()}, onError = {signUpError = it})
//                        }
//                        else{
//                            signUpError = "Passwords do not match"
//                        }
                    }
                },
                enabled = email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Up")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = onLoginClick, modifier = Modifier.fillMaxWidth()) {
                Text("Login")
            }
        }
    }
}

//fun signUp(email: String, password: String, onSignUpSuccess: () -> Unit, onError: (String) -> Unit) {
//    val db = FirebaseFirestore.getInstance()
//    auth.createUserWithEmailAndPassword(email, password)
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                // Sign-up success, get the user
//                val user = auth.currentUser
//                if (user != null) {
//                    // Add additional user data to Firestore (optional, recommended)
//                    val userMap = hashMapOf(
//                        "email" to email,
//                        "uid" to user.uid // Store the user's UID
//                        // Add other user details here (e.g., name, profile info)
//                    )
//                    db.collection("users").document(user.uid)  // Use the UID as the document ID
//                        .set(userMap)
//                        .addOnSuccessListener {
//                            Log.d("Firestore", "User data added to Firestore")
//                            onSignUpSuccess() //callback
//                        }
//                        .addOnFailureListener { e ->
//                            Log.e("Firestore", "Error adding user data to Firestore", e)
//                            onError("Failed to add user data")
//                        }
//                }
//                else{
//                    onError("User is null")
//                }
//
//            } else {
//                // Sign-up failed, handle the error
//                val errorMessage = task.exception?.message ?: "Unknown sign-up error"
//                Log.e("Firebase", "Sign-up error: $errorMessage")
//                onError(errorMessage) //callback
//            }
//        }
//}

@Composable
fun Greeting (name: String) {
    Text("Hello $name !")
}

