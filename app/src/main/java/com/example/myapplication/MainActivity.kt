package com.example.myapplication

import android.icu.number.NumberFormatter.UnitWidth
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //call function
                    //Start()
                }
            }
        }
    }
}

@Composable
fun ApiTest(){
    val apiCall = ApiCall()
    ApiCall.getData(this){ dataModel ->
        //Callback function, invoked when API response is received
        val text = dataModel.value

    }
}

@Composable
fun Start(){
    var headerText by remember {
        mutableStateOf("Choose a dish to start!")
    }
    var entryText by remember {
        mutableStateOf("Entry")
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
    /*
    Row(modifier = Modifier.padding(5.dp)){
        TextCell(text = "1", modifier = Modifier.weight(0.25f, fill = true))
        TextCell(text = "1", modifier = Modifier.weight(0.25f, fill = true))
        TextCell(text = "1", modifier = Modifier.weight(0.25f, fill = true))
    }*/
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
        MainScreen()
    }
}