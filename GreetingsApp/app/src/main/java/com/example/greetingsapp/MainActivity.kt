package com.example.greetingsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.greetingsapp.ui.theme.GreetingsAppTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreetingsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GreetingScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GreetingScreen(modifier: Modifier = Modifier) {
    // State to hold the user's input and UI visibility
    var name by remember { mutableStateOf("") }
    var greetingMessage by remember { mutableStateOf("") }
    var timeBasedGreeting by remember { mutableStateOf("") }
    var greeted by remember { mutableStateOf(false) } // Tracks whether the user has been greeted

    // Function to get the time-based greeting
    fun getTimeBasedGreeting(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> "Good morning!"
            in 12..16 -> "Good afternoon!"
            in 17..20 -> "Good evening!"
            in 21..23, in 0..4 -> "Good night!"
            else -> ""
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally // Align content horizontally in the center
    ) {
        // Check if user has been greeted; if not, show input and button
        if (!greeted) {
            // Input field for user's name
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Enter your name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Button to display greeting
            Button(onClick = {
                greetingMessage = "Hello $name!"
                timeBasedGreeting = getTimeBasedGreeting()
                greeted = true // User has been greeted, so update the state
            }) {
                Text("Greet Me")
            }
        } else {
            // Show the greeting message and time-based greeting
            Text(
                text = greetingMessage,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (timeBasedGreeting.isNotEmpty()) {
                Text(
                    text = timeBasedGreeting,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Return button to reset the greeting and show input again
            Button(onClick = {
                greeted = false
                name = ""
                greetingMessage = ""
                timeBasedGreeting = ""
            }) {
                Text("Return")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    GreetingsAppTheme {
        GreetingScreen()
    }
}
