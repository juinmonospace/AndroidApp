package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.theme.Flighter

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Welcome.route,

    )
    {
        composable(route = Screen.WELCOME.name){
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.STOPWATCH.name){
            StopwatchScreen(navController = navController)
        }
        composable(route = Screen.WATCHRUNNING.name){
            WatchRunningScreen(navController = navController)
        }
        composable(route = Screen.TIMER.name){
            TimerScreen(navController = navController)
        }

        composable(
            route = "timerIsRunning/{totalTimeInMillis}",
            arguments = listOf(navArgument("totalTimeInMillis") { type = NavType.LongType })
        ) { backStackEntry ->
            val totalTimeInMillis = backStackEntry.arguments?.getLong("totalTimeInMillis") ?: 0L
            TimerIsRunningScreen(navController = navController, setTimerDuration = totalTimeInMillis)
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavController){
    Column(
        modifier = Modifier.padding(28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Welcome!", fontFamily = Flighter, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(130.dp))
        Button(onClick = {navController.navigate(NavigationItem.Stopwatch.route)})
            { Text("Stopwatch", fontFamily = Flighter,fontSize = 23.sp) }
        Button(onClick = {navController.navigate(NavigationItem.Timer.route)})
            { Text("Timer", fontFamily = Flighter, fontSize = 23.sp) }
    }
}

@Preview
@Composable
fun Preview(){
    val navController : NavController = rememberNavController()
    //WatchRunningScreen(navController)
    //WelcomeScreen(navController)
    TimerScreen(navController)
    //TimerIsRunningScreen(navController = navController)
    //StartTimeScreen(navController = navController)
}



