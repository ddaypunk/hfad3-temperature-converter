package com.example.temperatureconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.temperatureconverter.ui.theme.TemperatureConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    MainActivityContent()
                }
            }
        }
    }
}

@Composable
fun MainActivityContent() {
    val celsius = remember { mutableStateOf(0) }
    val newCelsius = remember { mutableStateOf("") }
    // alternately can be as such
    // var celsiusValue by remember { mutableStateOf(0) }
    // and be accessed with celsuisValue instead of celsiusValue.value

    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Header(image = R.drawable.sunrise, description = "an image of a sunrise")
        EnterTemperature(temperature = newCelsius.value) { newCelsius.value = it } // display newCelsius value in the input, and update it as the user enters a new value
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            ConvertButton { newCelsius.value.toIntOrNull()?.let { celsius.value = it } } // when button is clicked convert newCelcius value as currently to Int or null, if not null, update the value displayed in text composable
        }
        TemperatureText(celsius.value)
    }
}

// Example composable function
@Composable
fun Header(image: Int, description: String) {
    Image(
        painter = painterResource(id = image),
        contentDescription = description,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
    )
}

@Composable
fun TemperatureText(celsius: Int) {
    val fahrenheit = (celsius.toDouble() * 9/5) + 32
    Text(text = "$celsius Celsius is $fahrenheit Fahrenheit")
}

@Composable
fun ConvertButton(clicked: () -> Unit) {
    Button(onClick = clicked) {
        Text(text = "Convert")
    }
}

@Composable
fun EnterTemperature(
    temperature: String, // the temperature to display
    changed: (String) -> Unit // what to do when the temperature changes
) {
    TextField(
        value = temperature,
        onValueChange = changed,
        label = { Text("Enter a temperature in Celsius") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    TemperatureConverterTheme {
        Surface {
            MainActivityContent()
        }
    }
}