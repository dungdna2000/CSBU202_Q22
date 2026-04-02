package uit.q21.myapplication

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

@Composable
fun ColorSelector(
    modifier: Modifier = Modifier,
    selectedColor: MutableState<Color> = mutableStateOf(Color.Blue),
    colorList: List<Color> =
        listOf(Color.Blue, Color.Cyan, Color.Black)
) {
    Row {
        for (color in colorList) {
            Button(
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = color,
                    contentColor = Color.White
                ),
                onClick = {
                    selectedColor.value = color
                }
            ) {
                Text(text = if (color==selectedColor.value) "x" else ".")
            }
        }
    }
}