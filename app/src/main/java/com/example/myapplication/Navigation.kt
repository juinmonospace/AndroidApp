package com.example.myapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationItem.Home.route){
        composable(route = Screen.HOME.name){
            MainScreen(navController = navController)
        }
        composable(
            route = "${NavigationItem.Notes.route}/{name}", // will crash if nothing passed
            //route = NavigationItem.Notes.route + "?name = {name}",
            //route = "${ NavigationItem.Notes.route}",

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
                navController.navigate(NavigationItem.Notes.withArgs(text))
            }
        )
        {
            Text(text = "To NotesScreen")
        }
    }
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
