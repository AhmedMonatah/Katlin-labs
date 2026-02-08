package com.example.myapplication


import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun LocationPermissionAlert(
    showDialog: Boolean,
    onOpenSettings: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Location Permission Required") },
            text = {
                Text(
                    "This app needs location permission to get your current address. " +
                            "Please enable it from settings."
                )
            },
            confirmButton = {
                Button(onClick = onOpenSettings) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}
