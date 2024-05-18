package com.example.myapplication

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//import androidx.compose.ui.tooling.data.EmptyGroup
//import androidx.compose.ui.tooling.data.EmptyGroup.name


@Composable
fun GpsScreen(navController: NavController, context : Context) {
    val context = LocalContext.current
    //userManager = UserManager(context)
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Gps Screen")

        // Name Input
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        // Age Input
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Save Button
        Button(onClick = {
            // Launch a coroutine to call the suspend function
            val ageInt = age.toIntOrNull() ?: 0

            //userViewModel.storeUser(ageInt, name)
        }) {
            Text("Save")
        }
    }
}


private suspend fun saveNote(name: String, age: Int) {
    GlobalScope.launch {
        //UserManager.storeUser(age, name)
    }
}



/*
lateinit var etName: EditText
lateinit var etAge: EditText
lateinit var tvName: TextView
lateinit var tvAge: TextView
lateinit var saveButton: Button
lateinit var userManager: UserManager
var age = 0
var name = ""


 */


/*
etName = findViewById(R.id.et_name)
etAge = findViewById(R.id.et_age)
tvName = findViewById(R.id.tv_name)
tvAge = findViewById(R.id.tv_age)
saveButton = findViewById(R.id.btn_save)
 */

/*
@Composable
fun GpsScreen(navController: NavController){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    userManager = UserManager(context)
    Column(
        modifier = Modifier.padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        //write note

        //save button
        Button(
            onClick = {
                scope.launch{
                    saveNote()
                }
            }) {
            Text("Save")
        }
    }
}

private suspend fun saveNote(){
    saveButton.setOnClickListener {
        name = etName.text.toString()
        age = etAge.text.toString().toInt()

        // Stores the values
        // Since the storeUser function of UserManager
        // class is a suspend function
        // So this has to be done in a coroutine scope
       // GlobalScope.launch {
        userManager.storeUser(age, name)
        //}
    }}*/
