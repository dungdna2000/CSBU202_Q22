package uit.q21.myapplication

import android.graphics.fonts.FontStyle
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uit.q21.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    var data = CalculatorData(0,0,-1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Counter(
                        Modifier.padding(innerPadding),
                        maxValue = 15)

//                    Greeting(
//                        data,
//                        modifier = Modifier.padding(innerPadding)
//                    )
                }
            }
        }
    }
}

@Composable
fun CounterButton(text: String, isEnabled: Boolean = true, onClick: ()->Unit) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        enabled = isEnabled,
        modifier = Modifier.padding(1.dp)
    ) {
        Text(text = text, fontSize = 20.sp)
    }
}

@Composable
fun Counter(modifier: Modifier = Modifier, maxValue: Int = 10) {
    var counterValue by remember { mutableStateOf(0)}
    Column {
        Spacer(modifier)
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            CounterButton(text = "-", isEnabled = counterValue>0) {
                if (counterValue>0) counterValue--
            }
            Spacer(modifier = Modifier.padding(horizontal = 10.dp))
            Text(
                text = counterValue.toString(),
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.padding(horizontal = 10.dp))
            CounterButton(text = "+", isEnabled = counterValue<maxValue) {
                if (counterValue<maxValue) counterValue++
            }
        }
    }
}

@Composable
fun CalButton(text: String, onClick: ()->Unit) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        modifier = Modifier.padding(1.dp)
    ) {
        Text(text = text, fontSize = 20.sp)
    }
}

@Composable
fun Greeting(data: CalculatorData = CalculatorData(0,0,-1), modifier: Modifier = Modifier) {
    var v by remember { mutableStateOf("") }
    Column {
        Spacer(modifier = Modifier.padding(10.dp))

        if (v.isEmpty())
            Text(text = "0", fontSize = 40.sp, color = Color.Gray)
        else
            Text(text = v, fontSize = 40.sp)

        Row {
            CalButton(
                text = "1",
                onClick = {
                    v += "1"
                }
            )

            CalButton(text = "2", onClick = { v += "2" })

            Button(onClick = { v += "3"}) {
                Text(text = "3")
            }
            Button(onClick = {
                data.left = v.toInt()
                data.operator = 1
                v = ""
            }) {
                Text(text = "+")
            }
        }
        Row {
            Button(onClick = {
                v += "4"
            }) {
                Text(text = "4")
            }
            Button(onClick = { v += "5"}) {
                Text(text = "5")
            }
            Button(onClick = { v += "6"}) {
                Text(text = "6")
            }
            Button(onClick = {
                data.left = v.toInt()
                data.operator = 2
                v = ""
            }) {
                Text(text = "-")
            }
        }
        Row {
            Button(onClick = {
                v += "7"
            }) {
                Text(text = "7")
            }
            Button(onClick = { v += "8"}) {
                Text(text = "8")
            }
            Button(onClick = { v += "9"}) {
                Text(text = "9")
            }
            Button(onClick = {
                data.left = v.toInt()
                data.operator = 3
                v = ""
            }) {
                Text(text = "*")
            }
        }
        Row {
            Button(onClick = {
                v = ""
            }) {
                Text(text = "C")
            }
            Button(onClick = { v += "0"}) {
                Text(text = "0")
            }
            Button(onClick = {
                data.right = v.toInt()
                v = data.getResult().toString()
            }) {
                Text(text = "=")
            }
            Button(onClick = {
                data.operator = 4
                v = ""
            }) {
                Text(text = "/")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterPreview() {
    MyApplicationTheme {
        Counter()
    }
}