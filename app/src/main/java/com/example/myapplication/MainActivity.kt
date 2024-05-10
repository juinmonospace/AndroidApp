package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.File
import java.util.Calendar
import java.util.Locale
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


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
                    color = MaterialTheme.colorScheme.background
                ) {

                    //call function
                    //Start()
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        //display data from gps
                        Text("Latitude: $latitude")
                        Text("Longitude: $longitude")
                        Text(getTimeAndDate())
                        //Text("Address: $address")
                        if (latitude != null){
                            Button(
                                onClick = { /* TODO */ }, // navigate to new page/write note
                                modifier = Modifier.padding(16.dp),
                                shape = CircleShape
                            ){
                                Text("Make diary entry")
                            }

                            if (shouldShowCamera.value){
                                CameraView(outputDirectory = outputDirectory,
                                    executor = cameraExecutor,
                                    onImageCaptured = ::handleImageCapture,
                                    onError = {Log.e("kilo", "View error:", it)}
                            }
                            requestCameraPermission()
                            outputDirectory = getOutputDirectory()
                            cameraExecutor = Executors.newSingleThreadExecutor()
                            if (shouldShowCamera.value){
                                Image(painter = rememberImagePainter(phototUri),
                                    contentDescription = null,)
                            }

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

private val requestPermissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestPermission()
) { isGranted ->
    if (isGranted) {
        Log.i("kilo", "Permission granted")
        shouldShowCamera.value = true
    } else {
        Log.i("kilo", "Permission denied")
    }
}
private fun requestCameraPermission() {
    when {
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED -> {
            Log.i("kilo", "Permission previously granted")
            shouldShowCamera.value = true
        }

        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.CAMERA
        ) -> Log.i("kilo", "Show camera permissions dialog")

        else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }
}

private lateinit var outputDirectory: File
private lateinit var cameraExecutor: ExecutorService
private lateinit var photoUri: Uri
private var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)
private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)


fun handleImageCapture(uri: Uri) {
    Log.i("kilo", "Image captured: $uri")
    shouldShowCamera.value = false
    photoUri = uri
    shouldShowPhoto.value = true
}

private fun getOutputDirectory(): File {
    val mediaDir = externalMediaDirs.firstOrNull()?.let {
        File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
    }

    return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
}

override fun onDestroy() {
    super.onDestroy()
    cameraExecutor.shutdown()
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