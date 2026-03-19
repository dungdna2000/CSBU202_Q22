package uit.q21.myapplication

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf

class CardData(
    val value: String,
    var state: MutableState<Int> = mutableIntStateOf(0)
)