package com.example.serviceslab1

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceslab1.ui.theme.ServicesLab1Theme

class MainActivity : ComponentActivity() {
    private var myService=mutableStateOf<BoundService?>(null)
    private var isBound=mutableStateOf(false)
    private val serviceConnecion=object : ServiceConnection {
        override fun onServiceConnected(
            p0: ComponentName?,
            p1: IBinder?
        ) {
            val binder=p1 as BoundService.MyLocalBinder
            myService.value=binder.getService()
            isBound.value=true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
          isBound.value=false
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent= Intent(this, BoundService::class.java)
        bindService(intent,serviceConnecion,BIND_AUTO_CREATE)

        setContent {
            ServicesLab1Theme {
                LocalBountScreen(myService=myService.value)
            }
        }
    }
}

@Composable
fun LocalBountScreen(myService: BoundService?) {

    val currentTime= remember{
        mutableStateOf("Press the button to get time")
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(text = currentTime.value, fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        Button(onClick = {
         myService?.let {
             currentTime.value=it.getCurrentTime()
         }
        })
        {
            Text(text = "Get Time")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ServicesLab1Theme {
        LocalBountScreen(myService = null)
    }
}