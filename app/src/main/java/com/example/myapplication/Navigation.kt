package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        //startDestination = NavigationItem.Home.route
        startDestination = NavigationItem.Welcome.route
    )
    {
        composable(route = Screen.WELCOME.name){
            WelcomeScreen(navController = navController)
        }

        composable(route = Screen.STARTTIME.name){
            StartTimeScreen(navController = navController)
        }
        composable(route = Screen.WATCHRUNNING.name){
            WatchRunningScreen(navController = navController)
        }
        /*
        composable(
            route = "${Screen.STOPPEDTIME.name}/{elapsedTime}",
            arguments = listOf(navArgument("elapsedTime") { type = NavType.LongType })
        ) {
            val elapsedTime = it.arguments?.getLong("elapsedTime") ?: 0L
            StoppedTimeScreen(navController = navController, passedTime = passedTime)
        }

         */

        composable(
            route = "${NavigationItem.Notes.route}/{name}", // will crash if nothing passed

            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                    defaultValue = "Judith"
                    nullable = true
                }
            )


        )
        {
            entry ->
            NotesScreen(name = entry.arguments?.getString("name"))
            //NotesScreen()
        }

    }
}

@Composable
fun WelcomeScreen(navController: NavController){
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ){
        Text("Welcome!", fontSize = 25.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Go to Stop Watch", fontSize = 21.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {navController.navigate(NavigationItem.StartTime.route)}
        )
        {
            Text("Stop Watch")
        }
    }
}


@Composable
fun StartTimeScreen(navController: NavController){
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Here we have the stop watch function! ")
        //time ui
        StopWatchDisplay(elapsedTime = 0)
        Button(
            onClick = {
                //startRunningTime();
                navController.navigate(NavigationItem.WatchRunning.route) }
        )
        {
            Text("Start")
        }

    }

}

@Composable
fun WatchRunningScreen(navController: NavController){
    var passedTime by remember { mutableStateOf(0L)
    }
    var timeIsRunning by remember {
        mutableStateOf(true)
    }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start    )
    {
    LaunchedEffect(timeIsRunning) {
        if (timeIsRunning) {
            while (true) {
                passedTime += 100L
                delay(100L)
            }
        }
    }

        Text("Time is ticking...")
        StopWatchDisplay(elapsedTime = passedTime)
        Row {
            //either stop or resume
            Button(onClick = {timeIsRunning = !timeIsRunning})
            {
                if (timeIsRunning){
                    Text("Stop")
                    //timeIsRunning = false
                } else {
                    Text("Resume")
                    //timeIsRunning = true
                }
            }
            Button(onClick = {
                timeIsRunning = false
                navController.navigate(NavigationItem.StartTime.route)
            }) {
                Text("Reset")
            }
        }
    }
    
}

/*
 @Composable
 fun StoppedTimeScreen(passedTime: Long){
     Column(
         modifier = Modifier.padding(32.dp),
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.Start    )
     {
         Text("Time has stopped")
         StopWatchDisplay(elapsedTime = passedTime)
         Row {
             Button(onClick = { /*TODO*/ }) {
                 Text("Resume")
             }
             Button(onClick = {navController.navigate(NavigationItem.StartTime.route)})
             {
                 Text(text = "Reset")
             }
         }
         
     }
 }

 */

@Composable
fun StopWatchDisplay(elapsedTime: Long) {
    val hours = TimeUnit.MILLISECONDS.toHours(elapsedTime)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60
    val milliseconds = (elapsedTime % 1000) / 10

    val formattedTime = String.format("%02d:%02d:%02d.%02d", hours, minutes, seconds, milliseconds)
    Text(
        text = formattedTime,
        style = MaterialTheme.typography.headlineMedium,
        fontSize = 24.sp
    )
}



@Composable
fun NotesScreen(name: String?){
//fun NotesScreen(){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()){
        Text(text = "Hello, $name")
    }

}
