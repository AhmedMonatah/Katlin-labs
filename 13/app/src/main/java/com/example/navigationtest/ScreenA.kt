package com.example.navigationtest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController



@Composable
fun ScreenA(con: NavHostController) {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = name,
            onValueChange = {
                name = it
                errorMessage = ""
            },
            label = { Text("Name") },
            modifier = Modifier
                .width(300.dp)
                .padding(bottom = 16.dp)
        )

        TextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = ""
            },
            label = { Text("Password") },
            modifier = Modifier
                .width(300.dp)
                .padding(bottom = 16.dp)
        )

        TextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                errorMessage = ""
            },
            label = { Text("Confirm Password") },
            modifier = Modifier
                .width(300.dp)
                .padding(bottom = 24.dp)
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = androidx.compose.ui.graphics.Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = {
                when {
                    name.isEmpty() -> {
                        errorMessage = "name cannot be empty"
                    }
                    password.isEmpty() -> {
                        errorMessage = "password cannot be empty"
                    }
                    confirmPassword.isEmpty() -> {
                        errorMessage = "please confirm your password"
                    }
                    password != confirmPassword -> {
                        errorMessage = "passwords do not match"
                    }

                    else -> {
                        con.navigate(ScreenRoute.ScreenB(name = name, password = password))
                    }
                }
            },
            modifier = Modifier.width(300.dp)
        ) {
            Text("Signup")
        }
    }
}

