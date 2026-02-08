package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.myapplication.ui.components.InfoRow
import com.google.android.gms.location.*
import java.util.Locale

@Composable
fun LocationScreen(fusedLocationClient: FusedLocationProviderClient) {

    val context = LocalContext.current

    val latitude = remember { mutableStateOf("--") }
    val longitude = remember { mutableStateOf("--") }
    val address = remember { mutableStateOf("Waiting for location...") }
    val permissionDenied = remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission())
    { granted -> if (granted) {
                permissionDenied.value = false
                startLocationUpdates(context, fusedLocationClient) { lat, lon, addr ->
                    latitude.value = lat
                    longitude.value = lon
                    address.value = addr
                }
            } else {
                permissionDenied.value = true
            }
        }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Current Location",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    InfoRow("Latitude", latitude.value)
                    InfoRow("Longitude", longitude.value)
                    Text(address.value, modifier = Modifier.padding(top = 12.dp))
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !permissionDenied.value,
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("smsto:0123456789")
                        putExtra("sms_body", address.value)
                    }
                    context.startActivity(intent)
                }
            ) { Text("Open SMS") }

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !permissionDenied.value,
                onClick = {
                    val uri = Uri.parse("geo:${latitude.value},${longitude.value}?q=${latitude.value},${longitude.value}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.google.android.apps.maps")
                    context.startActivity(intent)
                }
            ) { Text("Open in Map") }
        }
    }

    if (permissionDenied.value) {
        LocationPermissionAlert(
            showDialog = true,
            onOpenSettings = {
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.packageName, null)
                )
                context.startActivity(intent)
            },
            onDismiss = { permissionDenied.value = false }
        )
    }
}

fun startLocationUpdates(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onResult: (String, String, String) -> Unit
) {
    val locationRequest = LocationRequest.Builder(0)
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .setMinUpdateIntervalMillis(5000)
        .build()

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val location = result.lastLocation ?: return

            val lat = location.latitude.toString()
            val lon = location.longitude.toString()

            val geocoder = Geocoder(context, Locale.getDefault())

            geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1,
                object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        val addressText = addresses.firstOrNull()?.getAddressLine(0) ?: "Address not found"
                        onResult(lat, lon, addressText)
                    }

                    override fun onError(errorMessage: String?) {
                    }
                }
            )


        }
    }

    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
}

