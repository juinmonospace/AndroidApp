package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
//import java.lang.reflect.Modifier
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val (latitude, setLatitude) = remember {
                mutableStateOf("")
            }
            val (longitude, setLongitude) = remember { mutableStateOf("") }
            val (address, setAddress) = remember { mutableStateOf("") }

            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0.753f, 0.753f, 0.933f)
                ) {
                    Navigation()

                    Column(
                        modifier = Modifier.padding(32.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,

                    ) {

                        Column (modifier = Modifier.padding(40.dp))
                        {
                            Text ("Your current location: ", fontSize = 25.sp)
                            Text("Latitude: $latitude",
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 20.sp)
                            Text("Longitude: $longitude",
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 20.sp)
                        }

                        Column(modifier = Modifier.padding(40.dp)){
                            Text("The current time and date: ", fontSize = 25.sp)
                            Text(getTimeAndDate(),
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 20.sp)
                        }

                        //Text("Address: $address")
                        if (latitude != null){
                            //Navigation()
                        }
                    }
                }
            }

                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)


            checkAndRequestPermissions(setLatitude, setLongitude)
            //getAddressLocation(latitude.toDouble(), longitude.toDouble(), setAddress)
        }

    }

// Get current date and time to display
    private fun getTimeAndDate(): String{
        val currentDate = SimpleDateFormat("'Date: 'dd-MM-yyyy '\nTime: 'HH:mm:ss z")
        val currentDateAndTime = currentDate.format(Date())
        return currentDateAndTime.toString()
    }

    private fun checkAndRequestPermissions(
        setLatitude: (String) -> Unit,
        setLongitude: (String) -> Unit){
        if (checkPermissions()){
            if (isLocationEnabled()){
                getCurrentLocation { location ->
                    setLatitude(location.latitude.toString())
                    setLongitude(location.longitude.toString())
                }
            }
            else{
                Toast.makeText(this, "Turn location on ", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        else{
            requestLocationPermission()
        }
    }

    private fun isLocationEnabled(): Boolean{
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestLocationPermission(){
        ActivityCompat.requestPermissions(
            this, arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }

    private fun checkPermissions(): Boolean{
        return (ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
    }

    fun getCurrentLocation(callback: (Location) -> Unit){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
            ) {
            return
        }
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener{
                location -> callback(location)
            }
            .addOnFailureListener{
                exception ->
                Log.e("MainActivity", "Error getting location", exception)
            }


    }


    // get address (city, country) based on current location
    fun getAddressLocation( latitude: Double, longitude: Double,setAddress: (String) -> Unit){
        val geocoderLocation = GeocoderLocation(this@MainActivity)
        val locationInfo = geocoderLocation.getAddress(latitude, longitude)
        setAddress(locationInfo)
    }
}


/*
@Composable
fun MyNavGraph(navController: NavHostController){
    composable("home"){
        HomeScreen(
        onNavigateToNotes = {navController.navigate("notes") }
        )
    }
    composable("notes"){ NotesScreen() }
}

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ){
        composable("home"){
            HomeScreen(
                onNavigateToNotes = {
                    navController.navigate("notes")
                }

            )
        }
        composable("notes"){
            NotesScreen()
        }
    }
}
@Composable
fun HomeScreen(
    onNavigateToNotes: () -> Unit
)
{
    Button(onClick = onNavigateToNotes){
        Text(text = "Write note")
    }
}

@Composable
fun NotesScreen(){
    Text("This is NotesScreen")
}
*/
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