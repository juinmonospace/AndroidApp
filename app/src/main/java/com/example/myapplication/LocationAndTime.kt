/*
package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.location.LocationManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun LocationAndTime(
    navController: NavController,
  //  latitude: String,
    //longitude: String,
    //address: String
    ){
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    val (latitude, setLatitude) = remember { mutableStateOf("") }
    val (longitude, setLongitude) = remember { mutableStateOf("") }
    val (address, setAddress) = remember { mutableStateOf("") }

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
                        }
                    }
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)
    //checkAndRequestPermissions(setLatitude, setLongitude)
    //getAddressLocation(latitude.toDouble(), longitude.toDouble(), setAddress)

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
            //Toast.makeText(this, "Turn location on ", Toast.LENGTH_SHORT).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }
    else{
        requestLocationPermission()
    }
}

//private fun isLocationEnabled(): Boolean{
fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    //val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

private fun requestLocationPermission(){
    val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    ActivityCompat.requestPermissions(
        this, arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ),
    PERMISSION_REQUEST_ACCESS_LOCATION
    )
}

/*
companion object{
    private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
}

 */

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
            android.Manifest.permission.ACCESS_FINE_LOCATION
    )
        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
            (this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
    ) {
    return
    }
    fusedLocationProviderClient.lastLocation
    .addOnSuccessListener
    {
    location -> callback(location)
}
    .addOnFailureListener{
    exception ->
    Log.e("MainActivity", "Error getting location", exception)
}


}



/*
// get address (city, country) based on current location
fun getAddressLocation( latitude: Double, longitude: Double,setAddress: (String) -> Unit){
    val geocoderLocation = GeocoderLocation(this@MainActivity)
    val locationInfo = geocoderLocation.getAddress(latitude, longitude)
    setAddress(locationInfo)
}

 */