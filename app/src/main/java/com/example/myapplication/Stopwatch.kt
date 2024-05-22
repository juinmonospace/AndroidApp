package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.Acharnes
import com.example.myapplication.ui.theme.Flighter
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun StopwatchScreen(navController: NavController){
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
        ) {
            Button(
                onClick = {
                    navController.navigate(NavigationItem.WatchRunning.route)
                },
                shape = CircleShape,
                border = BorderStroke(6.dp, Color.White),
                modifier = Modifier.size(120.dp),
            )
            {
                Text("Start", fontFamily = Flighter, fontSize = 23.sp)
            }
            Spacer(modifier = Modifier.height(50.dp))
            Row (
                horizontalArrangement = Arrangement.End
            ){
                Spacer(modifier = Modifier.width(100.dp))
                Button(onClick = { navController.navigate(NavigationItem.Welcome.route) }) {
                    Text(text = "Menu", fontFamily = Flighter)
                }
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
                    navController.navigate(NavigationItem.Stopwatch.route)
                }
            ) {
                Text("Reset", fontFamily = Flighter, fontSize = 23.sp)
            }
        }
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

