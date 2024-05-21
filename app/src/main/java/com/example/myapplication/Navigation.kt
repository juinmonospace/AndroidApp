package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
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
import com.example.myapplication.ui.theme.Montserrat
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
        composable(route = Screen.TIMER.name){
            TimerScreen(navController = navController)
        }
        composable(route = Screen.TIMERISRUNNING.name){
            TimerIsRunningScreen(navController = navController, remainingTime = 3L)
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
        Row(
            //modifier = Modifier.padding(3.dp)
        ) {
            Button(
                onClick = {navController.navigate(NavigationItem.StartTime.route)}
            )
            {
                Text("Stopwatch", fontFamily = Flighter,fontSize = 23.sp)
            }
            Button(
                onClick = {navController.navigate(NavigationItem.Timer.route)}
            )
            {
                Text("Timer", fontFamily = Flighter, fontSize = 23.sp)
            }
        }

    }
}

@Composable
fun TimerScreen(navController: NavController){
    Column (
        modifier = Modifier.padding(40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Spacer(modifier = Modifier.height(80.dp))
        TimerDisplay(remainingTime = 0)
        Spacer(modifier = Modifier.height(60.dp))
        Button(onClick = {
            navController.navigate(NavigationItem.TimerIsRunning.route)
            //TimerIsRunning()
            //startTimer(selectedHour = 0, selectedMinute = 0, selectedSecond = 3)
        }) {
            Text(text = "Start", fontFamily = Flighter)
        }
    }
}

@Composable
fun TimerIsRunningScreen(navController: NavController, remainingTime: Long){
    //StopWatchDisplay(elapsedTime = 3)
    while(remainingTime.toInt() != 0){
        Row {
            Button(onClick = { navController.navigate(NavigationItem.Timer.route)}) {
                Text(text = "Cancel", fontFamily = Flighter)
            }
            Button(onClick = {
                //TODO: pause timer
            }) {
                Text(text = "Pause", fontFamily = Flighter)
            }
        }
        while(remainingTime.toInt() <= 4 && remainingTime.toInt() != 0){
            Text("Time is running up!", fontFamily = Montserrat)
        }
    }
    Text(text = "Time ran up!")
    Row {
        Button(onClick = { navController.navigate(NavigationItem.Timer.route) }) {
            Text(text = "Restart", fontFamily = Flighter)
        }
        Button(onClick = { /*TODO*/ }) {

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerDisplay(remainingTime: Long){
    //val hours = TimeUnit.MILLISECONDS.toHours(elapsedTime)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60
    val milliseconds = (remainingTime % 1000) / 10

    val formattedTime = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds)
    Column {
        Row (
            //modifier = Modifier.padding(4.dp)
        ){
            Text(text = "Hours", fontFamily = Montserrat)
            Spacer(modifier = Modifier.width(29.dp))
            Text(text = "Minutes", fontFamily = Montserrat)
            Spacer(modifier = Modifier.width(29.dp))
            Text(text = "Seconds", fontFamily = Montserrat)
        }
        Spacer(modifier = Modifier.height(70.dp))
        TimePickerDemo()
        //Text(text = formattedTime, fontFamily = Acharnes, fontSize = 45.sp,)
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun TimePickerDemo() {
    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }
    var selectedSecond by remember { mutableStateOf(0) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimePicker(
            selectedHour = selectedHour,
            onHourChange = { selectedHour = it },
            selectedMinute = selectedMinute,
            onMinuteChange = { selectedMinute = it },
            selectedSecond = selectedSecond,
            onSecondChange = { selectedSecond = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = String.format("%02d:%02d:%02d", selectedHour, selectedMinute, selectedSecond),
            fontFamily = Montserrat
        )
    }
}

@Composable
fun TimePicker(
    selectedHour: Int,
    onHourChange: (Int) -> Unit,
    selectedMinute: Int,
    onMinuteChange: (Int) -> Unit,
    selectedSecond: Int,
    onSecondChange: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NumberPicker(
            range = 0..23,
            selectedNumber = selectedHour,
            onNumberChange = onHourChange,
            label = "Hours"
        )
        Text(
            text = ":",
            fontSize = 24.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        NumberPicker(
            range = 0..59,
            selectedNumber = selectedMinute,
            onNumberChange = onMinuteChange,
            label = "Minutes"
        )
        Text(
            text = ":",
            fontSize = 24.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        NumberPicker(
            range = 0..59,
            selectedNumber = selectedSecond,
            onNumberChange = onSecondChange,
            label = "Seconds"
        )
    }
}

@Composable
fun NumberPicker(
    range: IntRange,
    selectedNumber: Int,
    onNumberChange: (Int) -> Unit,
    label: String
) {
    Box(
        modifier = Modifier
            .height(130.dp)
            .width(80.dp)
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
            //.background(Color.White)
            //.border(1.dp, Color.Gray)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),

            ) {
            itemsIndexed(range.toList()) { _, item ->
                Text(
                    text = item.toString(),
                    fontSize = 28.sp,
                    color = if (item == selectedNumber) Color.Black else Color.LightGray,
                    fontFamily = Acharnes,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onNumberChange(item) }
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.Transparent)
                .height(60.dp)
                .width(80.dp)
                .border(2.dp, Color.Black)
        )
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
    //WatchRunningScreen(navController)
    //WelcomeScreen(navController)
    //TimerScreen(navController)
    TimerIsRunningScreen(navController = navController, remainingTime =4)
}



