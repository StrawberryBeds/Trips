package com.samuelwood.trips.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
fun LoginView(
    onLoginSuccess: () -> Unit, // Add a callback for successful login
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf("") }  //To show the error

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
            Text("Login", style = MaterialTheme.typography.headlineMedium)

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
            if (loginError.isNotEmpty()) {
                Text(loginError, color = Color.Red)
            }

            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                onClick = {
//                    coroutineScope.launch {
//                        signIn(email, password, onLoginSuccess = {onLoginSuccess()}, onError = {loginError = it})
//                    }
                },
                enabled = email.isNotEmpty() && password.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = onSignUpClick, modifier = Modifier.fillMaxWidth()) {
                Text("Sign Up")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = onForgotPasswordClick, modifier = Modifier.fillMaxWidth()) {
                Text("Forgot Password")
            }
        }
    }
}


//@Composable
//fun SignUpView(onSignUpSuccess: () -> Unit,  onLoginClick: () -> Unit) {
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var confirmPassword by remember { mutableStateOf("") }
//    var signUpError by remember { mutableStateOf("") }
//    val coroutineScope = rememberCoroutineScope()
//
//
//    Scaffold { paddingValues ->
//        Column(
//            Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text("Sign Up", style = MaterialTheme.typography.headlineMedium)
//
//            TextField(
//                value = email,
//                onValueChange = { email = it },
//                label = { Text("Email") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            )
//            TextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text("Password") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp),
//                visualTransformation = PasswordVisualTransformation()
//            )
//            TextField(
//                value = confirmPassword,
//                onValueChange = { confirmPassword = it },
//                label = { Text("Confirm Password") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp),
//                visualTransformation = PasswordVisualTransformation()
//            )
//
//            if (signUpError.isNotEmpty()) {
//                Text(signUpError, color = Color.Red)
//            }
//
//            Spacer(modifier = Modifier.padding(8.dp))
//            Button(
//                onClick = {
//                    coroutineScope.launch {
//                        if (password == confirmPassword) {
//                            signUp(email, password, onSignUpSuccess = {onSignUpSuccess()}, onError = {signUpError = it})
//                        }
//                        else{
//                            signUpError = "Passwords do not match"
//                        }
//                    }
//                },
//                enabled = email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty(),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Sign Up")
//            }
//            Spacer(modifier = Modifier.padding(8.dp))
//            Button(onClick = onLoginClick, modifier = Modifier.fillMaxWidth()) {
//                Text("Login")
//            }
//        }
//    }
//}

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
//
//
//fun signIn(email: String, password: String, onLoginSuccess: () -> Unit, onError: (String) -> Unit) {
//    auth.signInWithEmailAndPassword(email, password)
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                // Sign-in success
//                val user = auth.currentUser
//                Log.d("Firebase", "Sign-in successful. User: ${user?.email}")
//                onLoginSuccess()
//                //  Update UI, navigate to main screen, etc.
//            } else {
//                // Sign-in failed
//                val errorMessage = task.exception?.message ?: "Unknown sign-in error"
//                Log.e("Firebase", "Sign-in error: $errorMessage")
//                onError(errorMessage)
//            }
//        }
//}
//
//@Composable
//fun ForgotPasswordView(onForgotPasswordSuccess: () -> Unit, onLoginClick: () -> Unit) {
//    var email by remember { mutableStateOf("") }
//    var resetPasswordError by remember { mutableStateOf("") }
//    val coroutineScope = rememberCoroutineScope()
//
//    Scaffold { paddingValues ->
//        Column(
//            Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text("Forgot Password", style = MaterialTheme.typography.headlineMedium)
//
//            TextField(
//                value = email,
//                onValueChange = { email = it },
//                label = { Text("Email") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            )
//
//            if (resetPasswordError.isNotEmpty()) {
//                Text(resetPasswordError, color = Color.Red)
//            }
//
//            Spacer(modifier = Modifier.padding(8.dp))
//            Button(
//                onClick = {
//                    coroutineScope.launch {
//                        resetPassword(email, onForgotPasswordSuccess = {onForgotPasswordSuccess()}, onError = {resetPasswordError = it})
//                    }
//                },
//                enabled = email.isNotEmpty(),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Reset Password")
//            }
//            Spacer(modifier = Modifier.padding(8.dp))
//            Button(onClick = onLoginClick, modifier = Modifier.fillMaxWidth()) {
//                Text("Back to Login")
//            }
//        }
//    }
//}
//
//fun resetPassword(email: String, onForgotPasswordSuccess: () -> Unit, onError: (String) -> Unit) {
//    auth.sendPasswordResetEmail(email)
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Log.d("Firebase", "Email sent.")
//                onForgotPasswordSuccess()
//                // Optionally show a message to the user that the email has been sent.
//            } else {
//                val errorMessage = task.exception?.message ?: "Failed to send reset email."
//                Log.e("Firebase", "Error sending password reset email: $errorMessage")
//                onError(errorMessage)
//            }
//        }
//}
