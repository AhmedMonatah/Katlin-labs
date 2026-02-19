package com.example.navigationtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.navigationtest.ui.theme.NavigationTestTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationTestTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val con = rememberNavController()

    NavHost(
        navController = con,
        startDestination = ScreenRoute.ScreenA
    ) {
        composable<ScreenRoute.ScreenA> {
            ScreenA(con)
        }
        composable<ScreenRoute.ScreenB> { backStackEntry ->
            val screen = backStackEntry.toRoute<ScreenRoute.ScreenB>()
            ScreenB(screen.name, screen.password, con)
        }
        composable<ScreenRoute.ScreenC> { backStackEntry ->
            val screen = backStackEntry.toRoute<ScreenRoute.ScreenC>()
            ScreenC(screen.name, con)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MyAppPrev(){
    MyApp()
}


@Serializable
sealed class ScreenRoute {
    @Serializable
    object ScreenA : ScreenRoute()
    @Serializable
    data class ScreenB(val name: String, val password: String) : ScreenRoute()
    @Serializable
    data class ScreenC(val name: String) : ScreenRoute()
}
