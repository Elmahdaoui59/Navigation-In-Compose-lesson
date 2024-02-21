package com.anymedia.navigationincomposelesson

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anymedia.navigationincomposelesson.ui.theme.NavigationInComposeLessonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationInComposeLessonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SetupNavHost(navController = navController)
                }
            }
        }
    }
}

@Composable
fun SetupNavHost(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(Screen.MainScreen.route) {
            MainScreen() {
                navController.navigate("${Screen.DetailScreen.route}/1234") /* 1234 will be passed as a nav arg to detail screen */
            }
        }
        composable(Screen.AccountsScreen.route) {
            AccountsScreen() {
                navController.popBackStack()
            }
        }
        composable(
            "${Screen.DetailScreen.route}/{emailId}",
            arguments = listOf(navArgument("emailId") { type = NavType.IntType })
        ) { backStackEntry: NavBackStackEntry ->
            val emailId = backStackEntry.arguments?.getInt("emailId")
            DetailScreen(emailId = emailId!!)
        }
    }
}

@Composable
fun DetailScreen(
    emailId: Int
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = emailId.toString())
    }
}


@Composable
fun MainScreen(
    onNavigate: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Main Screen")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { onNavigate() }) {
            Text(text = "Go to detail screen")
        }
    }
}

@Composable
fun AccountsScreen(
    onGoBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Accounts Screen")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { onGoBack() }) {
            Text(text = "Go Back")
        }
    }
}

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object AccountsScreen : Screen("accounts_screen")
    object DetailScreen : Screen("detail_screen")
}