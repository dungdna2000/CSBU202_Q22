package uit.q21.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import uit.q21.myapplication.ui.theme.MyApplicationTheme
import java.time.LocalDate
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
//    var data = CalculatorData(0,0,-1)

//    val data = arrayOf(
//        arrayOf(CardData("1"),CardData("A"),CardData("3"),CardData("H")),
//        arrayOf(CardData("3"),CardData("H"),CardData("A"),CardData("1")),
//        arrayOf(CardData("2"),CardData("*"),CardData("&"),CardData("$")),
//        arrayOf(CardData("*"),CardData("2"),CardData("&"),CardData("2"))
//    )
//    val chessData =
//        Array(8) {
//            Array(8) { mutableIntStateOf(0) }
//        }
//
//    val highlightData =
//        Array(8) {
//            Array(8) { mutableStateOf(false) }
//        }
//
//    init {
//        chessData[0][0].intValue = 1
//        chessData[0][3].intValue = 2
//        for (col in 0..7)
//            chessData[1][col].intValue = 3
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val now = LocalDate.now()
//        var selectedDate = mutableIntStateOf(now.dayOfMonth)
//        var selectedMonth = mutableIntStateOf(now.monthValue)
//        var selectedYear = mutableIntStateOf(now.year)

//        val counterList =
//            mutableStateListOf(
//                CounterObject(10,0,Color.Blue),
//                CounterObject(15,5,Color.Cyan),
//                CounterObject(6,1,Color.Yellow),
//
//                )

        val stopWatchData = StopWatchViewModel()
        stopWatchData.start()

        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StopWatchUI(
                        modifier =
                            Modifier
                                .padding(innerPadding),
                            stopWatchData = stopWatchData,
                        )
//                    CounterScreen(
//                        modifier = Modifier.padding(innerPadding),
//                        counters = counterList)

//                    ChessBoard(
//                        modifier = Modifier.padding(innerPadding),
//                        chessData,
//                        highlightData,
//                    )
//                    CalendarScreen(selectedDate, selectedMonth, selectedYear)
//                    MindGameScreen(innerPadding,data)
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
fun ChessCell(
    modifier: Modifier = Modifier,
    row: Int,
    column: Int,
    data: MutableIntState,
    isSelected: Boolean = false,
    isHighLighted: Boolean = false,
    onClick: () -> Unit
) {
    val blackColor = Color.Black
    val whiteColor = Color.LightGray
    val selectedColor = Color.Cyan
    val highlightColor = Color(0,128,0,255)

    val cellColor =
        if (isSelected) selectedColor
        else if (isHighLighted) highlightColor
        else if ( (row%2==0 && column%2==1) || (row%2==1 && column%2==0))
                blackColor
            else
                whiteColor

    val padding = if (isHighLighted) 1.dp else 0.dp
    Box(
        modifier = modifier
            .padding(padding)
            .aspectRatio(1.0f)
            .background(cellColor)
            .clickable { onClick() }
    ) {
        val id =
            when (data.intValue) {
                1 -> R.drawable.b_king_2x_ns
                2 -> R.drawable.w_bishop_2x_ns
                3 -> R.drawable.w_pawn_2x_ns
                else -> 0
            }

        if (id>0)
        Image(painter = painterResource(id),
            contentDescription = "")
    }
}

fun highLightMove(
    row: Int,
    column: Int,
    piece: Int,
    highLightData: Array<Array<MutableState<Boolean>>>,
    chessData: Array<Array<MutableIntState>>
) {
    for (r in 0..7)
        for (c in 0..7)
            highLightData[r][c].value = false

    when (piece) {
        1 -> {
            for (r in row-1..row+1)
                for (c in column-1..column+1)
                    if (r in 0..7 && c in 0..7)
                        highLightData[r][c].value = true
        }
        2 -> {
            var i=1
            while ((row-i) in 0..7 && (column-i) in 0..7) {
                if (chessData[row-i][column-i].intValue!=0) break
                highLightData[row - i][column - i].value = true
                i++
            }

            i=1
            while ((row-i) in 0..7 && (column + i) in 0..7) {
                if (chessData[row-i][column+i].intValue!=0) break
                highLightData[row - i][column + i].value = true
                i++
            }

            i=1
            while ((row+i) in 0..7 && (column + i) in 0..7) {
                if (chessData[row+i][column + i].intValue!=0) break
                highLightData[row+i][column + i].value = true
                i++
            }

            i=1
            while ((row+i) in 0..7 && (column - i) in 0..7) {
                if (chessData[row + i][column - i].intValue!=0) break
                highLightData[row + i][column - i].value = true
                i++
            }


        }
    }
}

@Composable
fun ChessBoard(
    modifier: Modifier = Modifier,
    chessData: Array<Array<MutableIntState>>,
    highlightData: Array<Array<MutableState<Boolean>>>
) {

    var selectedRow by remember { mutableIntStateOf(-1)}
    var selectedColumn by remember { mutableIntStateOf(-1)}

    Box(
        modifier = modifier.fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Column {
           for (row in 0..7) {
               Row(
                   modifier = Modifier.fillMaxWidth()
               ) {
                   for (column in 0..7) {
                       ChessCell(
                           modifier = Modifier.weight(1.0f),
                           row = row, column = column, chessData[row][column],
                           isSelected = (row == selectedRow && column == selectedColumn),
                           isHighLighted = (highlightData[row][column].value)
                       ) {
                           if (selectedColumn>=0 && selectedRow>=0 && chessData[row][column].intValue == 0) {
                               chessData[row][column].intValue = chessData[selectedRow][selectedColumn].intValue
                               chessData[selectedRow][selectedColumn].intValue = 0
                           }
                           selectedRow = row
                           selectedColumn = column

                           highLightMove(
                               row,
                               column,
                               chessData[selectedRow][selectedColumn].intValue,
                               highlightData,
                               chessData
                           )
                       }
                   }
               }
           }
        }    // column
    }

}

@Composable
fun YearSelector(
    selectedYear: MutableIntState = mutableIntStateOf(1980))
{
    Row {
        Button(onClick={selectedYear.intValue--}, shape = RectangleShape) { Text(text = "<") }
        Text(text = "${selectedYear.intValue}")
        Button(onClick={selectedYear.intValue++}, shape = RectangleShape) { Text(text = ">") }
    }
}

@Composable
fun MonthSelector(
    selectedMonth: MutableIntState = mutableIntStateOf(1),
    selectedYear: MutableIntState = mutableIntStateOf(1980)
) {
    Row {
        Button(onClick={
            if (selectedMonth.intValue == 1) {
                selectedMonth.intValue = 12
                selectedYear.intValue--
            } else
                selectedMonth.intValue--
        }, shape = RectangleShape) { Text(text = "<") }
        Text(text = "${selectedMonth.intValue}")
        Button(onClick={
            if (selectedMonth.intValue==12) {
                selectedMonth.intValue = 1
                selectedYear.intValue++
            } else {
                selectedMonth.intValue++
            }
        }, shape = RectangleShape) { Text(text = ">") }
    }
}

@Composable
fun DateSelector(
    selectedMonth: MutableIntState = mutableIntStateOf(1),
    selectedYear: MutableIntState = mutableIntStateOf(1980),
    selectedDate: MutableIntState = mutableIntStateOf(1)
) {
    val selected = LocalDate.of(
        selectedYear.intValue,
        selectedMonth.intValue,
        selectedDate.intValue)

    val selectedFirstDay = LocalDate.of(
        selectedYear.intValue,
        selectedMonth.intValue,
        1)


    val lastDay = selected.month.length(selected.isLeapYear)
    val startMonthWeekDay = selectedFirstDay.dayOfWeek.value

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (d in arrayOf("S","M","T","W","Th","F","S")) {
                Box(
                    modifier = Modifier.weight(1.0f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = d)
                }
//                Button(onClick={}, enabled = false, shape=RectangleShape) {
//                    Text( text = d)
//                }
//                Spacer(modifier = Modifier.padding(horizontal = 1.dp))
            }
        }

        val colStart = (if (startMonthWeekDay==7) 0 else (startMonthWeekDay))

        var dd = -(colStart - 1)
        Column {
            for (r in 0..4) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (c in 0..6) {
                        val t = if (dd<1) "." else dd
                        Box(
                            modifier = Modifier.weight(1.0f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "$t")
                        }

//                        Button(onClick={}, shape=RectangleShape) {
//                            Text(text = "$t")
//                        }
//                        Spacer(modifier = Modifier.padding(horizontal = 1.dp))

                        dd++
                    }
                }
            }
        }

    }
}

@Composable
fun CalendarScreen(
    selectedDate: MutableIntState = mutableIntStateOf(1),
    selectedMonth: MutableIntState = mutableIntStateOf(1),
    selectedYear: MutableIntState = mutableIntStateOf(1980)
    ) {
    Column {
//        Box(modifier = Modifier.width(100.dp)) {
//            Image(painter = painterResource(R.drawable.w_bishop_2x_ns), contentDescription = "")
//        }
        Text( text = "${selectedDate.intValue}/${selectedMonth.intValue}/${selectedYear.intValue}")
        YearSelector(selectedYear)
        MonthSelector(selectedMonth = selectedMonth, selectedYear = selectedYear)
        DateSelector(selectedMonth, selectedYear, selectedDate)
    }
}

@Composable
fun MindGameCard(cardData: CardData, onClick: ()->Unit) {

    Button(
        onClick = onClick,
        shape = RectangleShape,
        enabled = cardData.state.value!=3,
        modifier = Modifier.padding(2.dp)
    ) {
        Text(fontSize = 30.sp, text = if (cardData.state.value>=1) cardData.value else "?")
    }
}

@Composable
fun MindGameScreen(innerpadding: PaddingValues, data: Array<Array<CardData>>) {
    var lastRow by remember { mutableIntStateOf(-1)}
    var lastColumn by remember { mutableIntStateOf(-1)}
    var openCount by remember { mutableIntStateOf(0)}
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.padding(innerpadding))
        for (r in 0..3)
            Row{
                for (c in 0..3)
                    MindGameCard(data[r][c]) {
                        if (openCount<2 && (lastRow!=r || lastColumn!=c)) {
                            data[r][c].state.value = 1
                            openCount++

                            if (lastRow>=0 && (data[r][c].value == data[lastRow][lastColumn].value)) {
                                data[r][c].state.value = 3
                                data[lastRow][lastColumn].state.value = 3
                                lastRow = -1
                                lastColumn = -1
                            } else {
                                lastRow = r
                                lastColumn = c
                            }
                        }
                    }
            }

        LaunchedEffect(openCount) {
            if (openCount==2) {
                // close all open
                delay(2000)
                for (r in 0..3)
                    for (c in 0..3) {
                        if (data[r][c].state.value==1) {
                            data[r][c].state.value=0
                        }
                    }
                openCount=0
                lastRow = -1
                lastColumn = -1
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
fun ChessBoardPreview() {

//    val data = arrayOf(
//        arrayOf(CardData("1"),CardData("A"),CardData("3"),CardData("H")),
//        arrayOf(CardData("3"),CardData("H"),CardData("A"),CardData("1")),
//        arrayOf(CardData("2"),CardData("*"),CardData("&"),CardData("$")),
//        arrayOf(CardData("*"),CardData("2"),CardData("&"),CardData("2"))
//    )

    MyApplicationTheme {
        //ChessBoard()
    }
}