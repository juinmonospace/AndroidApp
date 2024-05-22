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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
        startDestination = NavigationItem.Welcome.route,

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

/*
@Composable
fun TimerScreen(navController: NavController){
    var timerDuration by remember { mutableStateOf(Long) }

    var remainingTime by remember { mutableStateOf(Long)}
    val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60
    val milliseconds = (remainingTime % 1000) / 10
    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }
    var selectedSecond by remember { mutableStateOf(0) }

    //convert selected timer duration into Long argument to pass
    val totalTimeInMillis = remember(selectedHour, selectedMinute, selectedSecond) {
        (selectedHour * 3600 + selectedMinute * 60 + selectedSecond) * 1000L
    }

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
        //TimePickerDemo()
        //Text(text = formattedTime, fontFamily = Acharnes, fontSize = 45.sp,)
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = {
            navController.navigate("timerIsRunning/$totalTimeInMillis")
            //navController.navigate(NavigationItem.TimerIsRunning.route)
        }) {
            Text(text = "Start", fontFamily = Flighter)
        }
    }

    /*
    Column (
        modifier = Modifier.padding(40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Spacer(modifier = Modifier.height(80.dp))
        //TimerDisplay(remainingTime = 0)
        Spacer(modifier = Modifier.height(60.dp))

        Button(onClick = {
            navController.navigate("timerIsRunning/$totalTimeInMillis")
            //navController.navigate(NavigationItem.TimerIsRunning.route)
        }) {
            Text(text = "Start", fontFamily = Flighter)
        }
    }
     */
}

 */

@Composable
fun TimerScreen(navController: NavController) {
    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }
    var selectedSecond by remember { mutableStateOf(0) }

    // Convert selected timer duration into Long argument to pass
    val totalTimeInMillis = remember(selectedHour, selectedMinute, selectedSecond) {
        (selectedHour * 3600 + selectedMinute * 60 + selectedSecond) * 1000L
    }

    var timerDuration by remember { mutableStateOf(totalTimeInMillis) }
    var remainingTime by remember { mutableStateOf(timerDuration) }

    val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60
    val milliseconds = (remainingTime % 1000) / 10

    Column (
        modifier = Modifier.padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Row {
            Text(text = "Hours", fontFamily = Montserrat)
            Spacer(modifier = Modifier.width(29.dp))
            Text(text = "Minutes", fontFamily = Montserrat)
            Spacer(modifier = Modifier.width(29.dp))
            Text(text = "Seconds", fontFamily = Montserrat)
        }
        Spacer(modifier = Modifier.height(50.dp))
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
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = {
            navController.navigate("timerIsRunning/$totalTimeInMillis")
        }) {
            Icon(Icons.Rounded.PlayArrow, contentDescription = "Play")
            Text(text = "Start", fontFamily = Flighter)
        }
    }
}


@Composable
fun TimerIsRunningScreen(navController: NavController, setTimerDuration: Long) {
    var timeIsRunning by remember { mutableStateOf(true) }
    var remainingTime by remember { mutableStateOf(setTimerDuration) }


    LaunchedEffect(timeIsRunning) {
        if (timeIsRunning) {
            while (remainingTime > 0) {
                delay(1000L) // Delay for 1 second
                remainingTime -= 1000L
            }
            timeIsRunning = false //stop timer when it reaches 0
        }
    }

    //compute remaining time in hours, minutes, seconds
    val hours = TimeUnit.MILLISECONDS.toHours(remainingTime)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //TimerRunningDisplay(remainingTime = remainingTime)
        // Timer counting down UI
        Text(
            text= String.format("%02d:%02d:%02d",hours, minutes, seconds ),
            fontFamily = Acharnes,
            fontSize = 45.sp,
            color = if (remainingTime <= 3000) { Color.Red } else { Color.Black }
        )

        Row {
            if (remainingTime.toInt() !=0) {
                Button(onClick = { timeIsRunning = !timeIsRunning }) {
                    Text(text = if (timeIsRunning) "Pause" else "Resume", fontFamily = Flighter)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { navController.navigate("Timer") }) {
                    Text(text = "Cancel", fontFamily = Flighter)
                }
            }
            else
            Column(){
                //Text(text = "Time ran up!", fontFamily = Flighter)
                Row {
                    Button(onClick = {navController.navigate(NavigationItem.Timer.route)}){
                        Icon(Icons.Rounded.Refresh, contentDescription = "Restart")
                        Text(text = "Restart Timer", fontFamily = Flighter)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { navController.navigate("Welcome") }) {
                        Text(text = "Menu", fontFamily = Flighter)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun TimerRunningDisplay(remainingTime: Long){
    Text(text = String.format("%02d:%02d", remainingTime / 60, remainingTime % 60), fontFamily = Acharnes)
}

// Diplays the Time Picker
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerDisplay(remainingTime: Long){
    //val hours = TimeUnit.MILLISECONDS.toHours(elapsedTime)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60
    val milliseconds = (remainingTime % 1000) / 10
    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }
    var selectedSecond by remember { mutableStateOf(0) }

    //convert selected timer duration into Long argument to pass
    val totalTimeInMillis = remember(selectedHour, selectedMinute, selectedSecond) {
        (selectedHour * 3600 + selectedMinute * 60 + selectedSecond) * 1000L
    }

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
        //TimePickerDemo()
        //Text(text = formattedTime, fontFamily = Acharnes, fontSize = 45.sp,)
        Spacer(modifier = Modifier.height(50.dp))
    }
}

/*
@Composable
fun TimePickerDemo(){
    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }
    var selectedSecond by remember { mutableStateOf(0) }

    //convert selected timer duration into Long argument to pass
    val totalTimeInMillis = remember(selectedHour, selectedMinute, selectedSecond) {
        (selectedHour * 3600 + selectedMinute * 60 + selectedSecond) * 1000L
    }

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
 */

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



