package uit.q21.myapplication

class CalculatorData(var left: Int, var right: Int, var operator: Int) {
    fun getResult(): Int =
        when (operator) {
            1 -> left + right
            2 -> left - right
            3 -> left * right
            4 -> left / right
            else -> 0
        }
}