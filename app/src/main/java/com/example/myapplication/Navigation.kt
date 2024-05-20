package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.Acharnes
import com.example.myapplication.ui.theme.Flighter
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
    }
}

@Composable
fun WelcomeScreen(navController: NavController){
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Welcome!", fontSize = 25.sp,)
        Spacer(modifier = Modifier.height(12.dp))
        //Text(text = "Go to Stop Watch", fontSize = 21.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {navController.navigate(NavigationItem.StartTime.route)}
        )
        {
            Text("Stop Time!", fontFamily = Flighter,fontSize = 23.sp)
        }
    }
}


@Composable
fun StartTimeScreen(navController: NavController){
    Column(
        modifier = Modifier.padding(40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //time ui
        StopWatchDisplay(elapsedTime = 0)
        Spacer(modifier = Modifier.padding(60.dp))
        Column(
            modifier = Modifier.padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
        Button(
            onClick = {
                navController.navigate(NavigationItem.WatchRunning.route) },
            shape = CircleShape,
            border = BorderStroke(6.dp, Color.White),
            modifier = Modifier.size(120.dp),
        )
        {
            Text("Start", fontFamily = Flighter,fontSize = 23.sp)
        }
        }

    }

}

@Composable
fun WatchRunningScreen(navController: NavController){
    var passedTime by remember { mutableStateOf(0L) }
    var timeIsRunning by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(timeIsRunning) {
        if (timeIsRunning) {
            while (true) {
                passedTime += 100L
                delay(100L)
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(40.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        StopWatchDisplay(elapsedTime = passedTime)
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            border = BorderStroke(6.dp, Color.White),
            modifier = Modifier.size(120.dp),
            onClick = { timeIsRunning = !timeIsRunning }
        ) {
            if (timeIsRunning) {
                Text("Stop", fontFamily = Flighter, fontSize = 23.sp)
            } else {
                Text("Resume", fontFamily = Flighter, fontSize = 23.sp)
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                border = BorderStroke(6.dp, Color.White),
                onClick = {
                    timeIsRunning = false
                    navController.navigate(NavigationItem.StartTime.route)
                }
            ) {
                Text("Reset", fontFamily = Flighter, fontSize = 23.sp)
            }
        }
    }
}



@Composable
fun ButtonDesign(
    modifier: Modifier = Modifier.size(100.dp),
    shape: Shape = CircleShape,
    border: BorderStroke = BorderStroke(6.dp, Color.DarkGray),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue),
    onClick: () -> Unit,
    function: @Composable () -> Unit
    ) { Button(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            border = border,
            contentPadding = contentPadding,
            colors = colors
    ) {
        Text(text = "")
}
}

@Composable
fun StopWatchDisplay(elapsedTime: Long) {
    //val hours = TimeUnit.MILLISECONDS.toHours(elapsedTime)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60
    val milliseconds = (elapsedTime % 1000) / 10

    val formattedTime = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds)
    Text(
        text = formattedTime,
        fontFamily = Acharnes,
        fontSize = 45.sp,
    )
}


fun func(){}

@Preview
@Composable
fun Preview(){
    val navController : NavController = rememberNavController()
    WatchRunningScreen(navController)
}



