package uit.q21.myapplication

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.Color

class CounterObject(
    val maxValue: Int = 10,
    val initValue: Int = 0,
    private val color: Color = Color.Green
) {
    private var currentValue: MutableIntState = mutableIntStateOf(initValue)
    fun canDecrease(): Boolean = currentValue.intValue > 0
    fun decrease() {
        if (!canDecrease()) return
        currentValue.intValue--
    }
    fun canIncrease(): Boolean = currentValue.intValue < maxValue
    fun increase() {
        if (!canIncrease()) return
        currentValue.intValue++
    }

    fun getCurrentValue() = currentValue
    fun getColor() = color
}