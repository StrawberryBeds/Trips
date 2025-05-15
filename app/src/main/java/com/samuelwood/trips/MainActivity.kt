package com.samuelwood.trips

//import androidx.compose.material3.PasswordVisualTransformation


import android.os.Bundle
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
import com.google.firebase.ktx.Firebase
import com.samuelwood.trips.ui.theme.TripsTheme
import com.samuelwood.trips.views.LoginView


class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TripsTheme {
                LoginView(
                    onLoginSuccess = { /* TODO: Handle successful login */ },
                    onSignUpClick = { /* TODO: Navigate to Sign Up screen */ },
                    onForgotPasswordClick = { /* TODO: Navigate to Forgot Password screen */ }
                )
                // You'll likely want more sophisticated logic later to show
                // LoginView OR the main app content based on auth state.
            }
        }
        auth = Firebase.auth
    }
}

