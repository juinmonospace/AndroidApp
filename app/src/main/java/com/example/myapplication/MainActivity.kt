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
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random


//class MainActivity : ComponentActivity() {
class MainActivity : AppCompatActivity(){
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var adapter: NoteAdapter

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
                                onClick = { /*NewNote().show(supportFragmentManager, null)*/ },
                                modifier = Modifier.padding(16.dp),
                                shape = CircleShape
                            ){
                                Text("Make diary entry")
                            }
                        }
                    }
                }
            }

                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)


            checkAndRequestPermissions(setLatitude, setLongitude)
            //getAddressLocation(latitude.toDouble(), longitude.toDouble(), setAddress)

        }
        //adapter = NoteAdapter(this)
        //binding.recyclerView.adapter = adapter
        //binding.recyclerView.itemAnimator = DefaultItemAnimator()
        //adapter.noteList = retrieveNotes()
        //adapter.notifyItemRangeInserted(0, adapter.noteList.size)
    }

    fun createNewNote(note: Note) {
        adapter.noteList.add(note)
        adapter.notifyItemInserted(adapter.noteList.size -1)
        saveNotes()
    }

    fun deleteNote(index: Int) {
        adapter.noteList.removeAt(index)
        adapter.notifyItemRemoved(index)
        saveNotes()
    }
    fun showNote(index: Int) {
        val dialog = ShowNote(adapter.noteList[index], index)
        dialog.show(supportFragmentManager, null)
    }
    private fun saveNotes() {
        val notes = adapter.noteList
        val gson = GsonBuilder().create()
        val jsonNotes = gson.toJson(notes)

        val outputStream = openFileOutput(FILEPATH, Context.MODE_PRIVATE)
        OutputStreamWriter(outputStream).use { writer ->
            writer.write(jsonNotes)
        }
    }
    private fun retrieveNotes(): MutableList<Note> {
        val noteList = mutableListOf<Note>()
        if (getFileStreamPath(FILEPATH).isFile) {
            val fileInput = openFileInput(FILEPATH)
            BufferedReader(InputStreamReader(fileInput)).use { reader ->
                val stringBuilder = StringBuilder()
                for (line in reader.readLine()) stringBuilder.append(line)

                if (stringBuilder.isNotEmpty()){
                    val listType = object : TypeToken<List<Note>>() {}.type
                    noteList.addAll(Gson().fromJson(stringBuilder.toString(), listType))
                }
            }
        }
        return noteList
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
        private const val FILEPATH = "notes.json"

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



@Composable
fun Start(){
    var headerText by remember {
        mutableStateOf("Choose a dish to start!")
    }
    var entryText by remember {
        mutableStateOf("Entree")
    }
    var mainText by remember {
        mutableStateOf("Main")
    }
    var dessertText by remember {
        mutableStateOf("Dessert")
    }

        Column {
            Text(text = headerText,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center)
            Row {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(16.dp),
                    shape = CircleShape
                )
                    {
                        Text(entryText)
                    }

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(16.dp),
                    shape = CircleShape)
                    {
                      Text(mainText)
                    }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(16.dp),
                    shape = CircleShape,
                    ) {
                    Text(dessertText)
                }
            }
        }

}

@Composable
fun TextCell(text: String, modifier: Modifier = Modifier){
    val cellModifier = Modifier
        .padding(4.dp)
        .size(100.dp, 100.dp)
        .border(width = 4.dp, color = Color.Black)
    Text(text = text,
        cellModifier.then(modifier),
        fontSize = 70.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreen() {

    val items = (1..12).map{
        ItemProperties(
            color = Color(
                Random.nextInt(255),
                Random.nextInt(255),
                Random.nextInt(255)
            ),
            width = Random.nextInt(20,100).dp,
            height = Random.nextInt(10,100).dp
        )
    }
    FlowColumn(
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(300.dp)
            .height(120.dp)) {
        items.forEach{
            Box(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(2.dp)
                //.width(it.width)
                .width(30.dp)
                .height(30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(it.color))
        }
    }
}

data class ItemProperties(
    val color: Color,
    val width: Dp,
    val height: Dp
)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        //Start()

    }
}