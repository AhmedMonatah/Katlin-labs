package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ScreenB(
    navController: NavController,
    sharedViewModel: SharedViewModel,
){
    val sharedCounter by sharedViewModel.SharedViewModel.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("SharedViewModel counter: $sharedCounter")
        Button(onClick = { sharedViewModel.counter() }) { Text("Increment Shared") }

        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) { Text("Back to Screen A") }


    }

}