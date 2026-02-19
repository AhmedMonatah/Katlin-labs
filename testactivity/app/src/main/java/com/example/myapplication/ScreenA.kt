package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun ScreenA(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    viewModel: CounterViewModel= viewModel()
){
    var localCounter by remember {mutableStateOf(0)}
    var saveableCounter by rememberSaveable {mutableStateOf(0)}
    val vmCounter by viewModel.count.collectAsState()
    val sharedCounter by sharedViewModel.SharedViewModel.collectAsState()

    Column (
       modifier = Modifier.fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Center
   ){
       Text("Local remember: $localCounter")
       Button(onClick = { localCounter++ }) { Text("Increment Local") }

       Text("rememberSaveable: $saveableCounter")
       Button(onClick = { saveableCounter++ }) { Text("Increment Saveable") }

       Text("ViewModel stateFlow: $vmCounter")
       Button(onClick = { viewModel.increment() }) { Text("Increment ViewModel") }

       Text("SharedViewModel: $sharedCounter")
       Button(onClick = { sharedViewModel.counter() }) { Text("Increment Shared") }

       Spacer(Modifier.height(20.dp))
       Button(onClick = { navController.navigate("screenB") }) { Text("Go to Screen B") }
   }
   }

