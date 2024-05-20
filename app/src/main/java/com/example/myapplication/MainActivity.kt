package com.example.myapplication

//import java.lang.reflect.Modifier
//import okhttp3.Route
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.NoteListScreen
import com.example.myapplication.ui.theme.NoteListViewModel
import com.example.myapplication.ui.theme.NoteScreen
import com.example.myapplication.ui.theme.NoteViewModel
import com.example.myapplication.ui.theme.util.Route
import com.example.myapplication.ui.theme.util.UiEvent
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.noteList
                ){
                    composable(route = Route.noteList){
                        val viewModel = hiltViewModel<NoteListViewModel>()
                        val noteList by viewModel.noteList.collectAsStateWithLifecycle()

                        NoteListScreen(
                            noteList = noteList,
                            onNoteClick = {
                                navController.navigate(
                                    Route.note.replace(
                                        "{id}",
                                        it.id.toString()
                                    )
                                )
                            },
                            onAddNoteClick = {
                                navController.navigate(Route.note)
                            }
                        )
                    }
                    composable(route = Route.note) {
                        val viewModel = hiltViewModel<NoteViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()

                        LaunchedEffect(key1 = true) {
                            viewModel.event.collect { event ->
                                when (event) {
                                    is UiEvent.NavigateBack -> {
                                        navController.popBackStack()
                                    }

                                    else -> Unit
                                }
                            }
                    }
                        NoteScreen(
                            state = state,
                            onEvent = viewModel::onEvent
                        )
                    }
                }
            }
        }
    }
}
/*
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

@Composable
fun TextFieldNotes(){
    /*
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

     */
}


 */
