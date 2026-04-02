package uit.q21.myapplication

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CounterButton(
    text: String,
    isEnabled: Boolean = true,
    color: Color = Color.Blue,
    onClick: ()->Unit
) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,      // enabled background
            contentColor = Color.White        // enabled text/icon
        ) ,
        enabled = isEnabled,
        modifier = Modifier.padding(1.dp)
    ) {
        Text(text = text, fontSize = 20.sp)
    }
}

@Composable
fun BarValue(modifier: Modifier = Modifier, maxValue: Int, currentValue: Int = 0) {
    Row {
        for (i in 1..maxValue) {
            Spacer(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .width(20.dp)
                        .padding(horizontal = 2.dp)
                        .background(
                            if (i<=currentValue) Color.Green else Color.Gray
                        )
            )
        }
    }
}


@Composable
fun Counter(
    modifier: Modifier = Modifier,
    counterObj: CounterObject = CounterObject()
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        CounterButton(
            text = "-",
            isEnabled = counterObj.canDecrease(),
            color = counterObj.getColor()) {
            counterObj.decrease()
        }
        Spacer(modifier = Modifier.padding(horizontal = 10.dp))
        Text(
            text = counterObj.getCurrentValue().intValue.toString(),
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = counterObj.getColor()
        )

        Spacer(modifier = Modifier.padding(horizontal = 10.dp))
        CounterButton(
            text = "+",
            isEnabled = counterObj.canIncrease(),
            color = counterObj.getColor()) {
            counterObj.increase()
        }
    }
}
