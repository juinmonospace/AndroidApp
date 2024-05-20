/*package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

        composable(route = Screen.GPS.name){
            GpsScreen(navController = navController)
        }

        composable(route = Screen.HOME.name){
            MainScreen(navController = navController)
        }

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
            GpsScreen(navController = navController)
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
        Text(text = "Press Button to make a note!", fontSize = 21.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            //onClick = {navController.navigate(NavigationItem.Gps.route)}
            onClick = {navController.navigate(NavigationItem.Gps.route)}
        )
        {
            Text("Write note")
        }
    }
}


@Composable
fun GpsScreen(navController: NavController){
    Column {
        //Text("Gps Screen")


    }
}





@Composable
fun MainScreen(navController: NavController){
    var text by remember {
        mutableStateOf("")
    }
    Column(){
        //text input
        TextField(value= text, onValueChange = {
            text = it
        } )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (text != null) { //ensure entered text
                    navController.navigate(NavigationItem.Notes.withArgs(text))
                }
            }
        )
        {
            Text(text = "To NotesScreen")
        }
    }
}

 */
