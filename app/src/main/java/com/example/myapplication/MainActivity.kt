package com.example.myapplication

//import java.lang.reflect.Modifier
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.asLiveData
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    lateinit var etName: EditText
    lateinit var tvName: TextView
    lateinit var saveButton: Button

    lateinit var userManager: UserManager
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val (latitude, setLatitude) = remember {
                mutableStateOf("")
            }
            val (longitude, setLongitude) = remember { mutableStateOf("") }
            val (address, setAddress) = remember { mutableStateOf("") }
            val scope = rememberCoroutineScope()
            var name by remember { mutableStateOf("") }

            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0.753f, 0.753f, 0.933f)
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                    ) {


                        Column()
                        {

                           // Note(userManager, scope)
                            Button(
                                //onClick = {navController.navigate(NavigationItem.Gps.route)}
                                onClick = {buttonSave()}
                            )
                            {
                                Text("save")
                            }


                            //observeData()
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Name") }
                            )

                            Text("Your current location: ", fontSize = 22.sp)
                            Text(
                                "Latitude: $latitude",
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 18.sp
                            )
                            Text(
                                "Longitude: $longitude",
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 18.sp
                            )
                        }

                        Column() {
                            Text("The current time and date: ", fontSize = 22.sp)
                            Text(
                                getTimeAndDate(),
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 18.sp
                            )
                        }
                    }
                    Navigation()
                }
            }

                        fusedLocationProviderClient =
                            LocationServices.getFusedLocationProviderClient(this@MainActivity)
                        checkAndRequestPermissions(setLatitude, setLongitude)
                        //getAddressLocation(latitude.toDouble(), longitude.toDouble(), setAddress)


                    }
                }
    private fun observeData() {


        // Updates name
        // every time user name changes it will be observed by userNameFlow
        // here it refers to the value returned from the usernameFlow function
        // of UserManager class
        userManager.userNameFlow.asLiveData().observe(this) {
            name = it
            tvName.text = it.toString()
        }
    }


    private fun buttonSave() {
        // Gets the user input and saves it
        saveButton.setOnClickListener {
            name = etName.text.toString()

            GlobalScope.launch {
                userManager.storeUser(name)
            }
        }
    }

    private fun checkAndRequestPermissions(
                    setLatitude: (String) -> Unit,
                    setLongitude: (String) -> Unit
                ) {
                    if (checkPermissions()) {
                        if (isLocationEnabled()) {
                            getCurrentLocation { location ->
                                setLatitude(location.latitude.toString())
                                setLongitude(location.longitude.toString())
                            }
                        } else {
                            //Toast.makeText(this, "Turn location on ", Toast.LENGTH_SHORT).show()
                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            startActivity(intent)
                        }
                    } else {
                        requestLocationPermission()
                    }
                }

                // Get current date and time to display
                private fun getTimeAndDate(): String {
                    val currentDate = SimpleDateFormat("'Date: 'dd-MM-yyyy '\nTime: 'HH:mm:ss z")
                    val currentDateAndTime = currentDate.format(Date())
                    return currentDateAndTime.toString()
                }


                private fun isLocationEnabled(): Boolean {
                    val locationManager: LocationManager =
                        getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                            || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                }

                private fun requestLocationPermission() {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ),
                        PERMISSION_REQUEST_ACCESS_LOCATION
                    )
                }

                companion object {
                private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
            }

                private fun checkPermissions(): Boolean {
                    return (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                            == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    )
                            == PackageManager.PERMISSION_GRANTED)
                }

                fun getCurrentLocation(callback: (Location) -> Unit) {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }
                    fusedLocationProviderClient.lastLocation
                        .addOnSuccessListener { location ->
                            callback(location)
                        }
                        .addOnFailureListener { exception ->
                            Log.e("MainActivity", "Error getting location", exception)
                        }


                }


                // get address (city, country) based on current location
                fun getAddressLocation(
                    latitude: Double,
                    longitude: Double,
                    setAddress: (String) -> Unit
                ) {
                    val geocoderLocation = GeocoderLocation(this@MainActivity)
                    val locationInfo = geocoderLocation.getAddress(latitude, longitude)
                    setAddress(locationInfo)
                }

            }

@Composable
fun Note(userManager: UserManager, scope: CoroutineScope){
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") }
        )
/*
        Button(onClick = {
            scope.launch {
                val ageInt = age.toIntOrNull() ?: 0
                //userManager.storeUser(ageInt, name)
            }
        }) {
            Text("Save Note")
        }

 */
    }
}


class GeocoderLocation(private val context: Context) {
    fun getAddress(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses != null) {
            return if (addresses.isNotEmpty()) {
                val address = addresses[0]
                val city = address.locality ?: ""
                val country = address.countryName ?: ""
                "$city, $country"
            } else {
                "No location found"
            }
        }
        return "No location found."
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        //Start()

    }
}