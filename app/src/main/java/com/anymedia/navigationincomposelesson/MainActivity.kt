package com.anymedia.navigationincomposelesson

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anymedia.navigationincomposelesson.ui.theme.NavigationInComposeLessonTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Clock

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
                    MainScreen()
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var counter by remember {
        mutableIntStateOf(0)
    }
    LaunchedEffect(key1 = true) {
        launch {
            delay(100)
            Log.i("recm","Effect block recomposed")
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "My top app bar") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                    }
                },
                actions = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            )
            /*
            LargeTopAppBar(
                title = { Text(text = "My Large top app bar") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                    }
                }
            )

             */
        }
    ) {

        DetailScreen(emailId = 34532)
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}
@Preview
@Composable
fun DetailScreenPreview() {
    DetailScreen(emailId = 3020)
}


@Composable
fun SetupNavHost(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        /*
        composable(Screen.MainScreen.route) {
            MainScreen() {
                navController.navigate("${Screen.DetailScreen.route}/1234") /* 1234 will be passed as a nav arg to detail screen */
            }
        }

         */
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