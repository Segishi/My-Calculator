package com.vont

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

/**
 * left off:
 * - work on parentheses function
 */
class MainActivity : AppCompatActivity() {
    private var stack = arrayListOf<String>()  //array that holds the inputs
    private var MDCounter = 0  //keeps track of how many multiplications and divisions inputted
    private var ASCounter = 0  //keeps track of how many additions and subtractions inputted
    var term = ""  //used to push whole numbers onto the stack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button0.setOnClickListener {
            onClickNumberFunction("0")
        }

        button1.setOnClickListener {
            onClickNumberFunction("1")
        }

        button2.setOnClickListener {
            onClickNumberFunction("2")
        }

        button3.setOnClickListener {
            onClickNumberFunction("3")
        }

        button4.setOnClickListener {
            onClickNumberFunction("4")
        }

        button5.setOnClickListener {
            onClickNumberFunction("5")
        }

        button6.setOnClickListener {
            onClickNumberFunction("6")
        }

        button7.setOnClickListener {
            onClickNumberFunction("7")
        }

        button8.setOnClickListener {
            onClickNumberFunction("8")
        }

        button9.setOnClickListener {
            onClickNumberFunction("9")
        }

        buttonDecimal.setOnClickListener {
            onClickNumberFunction(".")
        }

        buttonSign.setOnClickListener {
            onClickNumberFunction("-")
        }

        buttonAdd.setOnClickListener {
            onClickOperandFunction("+")
            ASCounter++
        }

        buttonMinus.setOnClickListener {
            onClickOperandFunction("-")
            ASCounter++
        }

        buttonMultiply.setOnClickListener {
            onClickOperandFunction("*")
            MDCounter++
        }

        buttonDivision.setOnClickListener {
            onClickOperandFunction("/")
            MDCounter++
        }

        buttonLeftParantheses.setOnClickListener {
            onClickOperandFunction("(")
        }

        buttonRightParantheses.setOnClickListener {
            onClickOperandFunction(")")
        }

        buttonClear.setOnClickListener {
            stack.clear()
            MDCounter = 0
            ASCounter = 0
            term = ""
            clear()
        }

        buttonEquals.setOnClickListener {
            if (stack.size != 1) {
                resultBox.text = calculate()
            }
        }
    }


    /**
     * Updates the string "term" and the text box on the UI
     * @param number string that carries the inputted number by user
     */
    private fun onClickNumberFunction(number: String) {
        term += number
        var currentText: String = resultBox.text.toString()
        currentText += number
        resultBox.text = currentText
    }

    /**
     * Adds the string "term" and inputted operand to the stack, resets "term", and updates text box on the UI
     * @param operation boolean that carries the inputted operand by user
     */
    private fun onClickOperandFunction(operation: String) {
        if (addTerm) {
            stack.add(term)
            term = ""
        }
        addTerm = true
        stack.add(operation)
        var currentText: String = resultBox.text.toString()
        currentText += operation
        resultBox.text = currentText
    }

    /**
     * Does a simple addition calculation
     * @param a first inputted integer by user
     * @param b second inputted integer by user
     * @return the addition of a and b
     */
    private fun add(a: Double, b: Double): Double {
        return a + b
    }

    /**
     * Does a simple subtraction calculation
     * @param a first inputted integer by user
     * @param b second inputted integer by user
     * @return the subtraction of a and b
     */
    private fun subtract(a: Double, b: Double): Double {
        return a - b
    }

    /**
     * Does a simple multiplication calculation
     * @param a first inputted integer by user
     * @param b second inputted integer by user
     * @return the multiplication of a and b
     */
    private fun multiply(a: Double, b: Double): Double {
        return a * b
    }

    /**
     * Does a simple division calculation
     * @param a first inputted integer by user
     * @param b second inputted integer by user
     * @return the division of a and b
     */
    private fun divide(a: Double, b: Double): Double {
        return a / b
    }

    /**
     * Clears the text box on UI
     */
    var addTerm =
        true  //boolean that keeps track when the calculator should add the term to "stack" or not

    private fun clear() {
        resultBox.text = ""
        addTerm = true
    }

    /**
     * Replace the 2 numbers and the operand, that were used to do a calculation, with the result of them
     * @param index integer that stores the index position of the an operand in the "stack"
     * @param result the calculation of the 2 numbers
     */
    private fun stackPopAndPush(index: Int, result: Double) {
        stack.set(index - 1, result.toString())
        stack.removeAt(index + 1)
        stack.removeAt(index)
    }

    /**
     * Calculates the entire inputted equation
     * @return the final calculation of the equation inputted by user
     */
    private fun calculate(): String {
        try {
            stack.add(term)
            addTerm = false
            term = ""

            //while loop to do all the multiplications and divisions in the equation (PEMDAS)
            var index = 0
            while (MDCounter != 0) {
                when (stack.get(index)) {
                    "*" -> {
                        val result = multiply(
                            stack.get(index - 1).toDouble(),
                            stack.get(index + 1).toDouble()
                        )
                        stackPopAndPush(index, result)
                        MDCounter--
                        index--
                    }
                    "/" -> {
                        val result =
                            divide(stack.get(index - 1).toDouble(), stack.get(index + 1).toDouble())
                        stackPopAndPush(index, result)
                        MDCounter--
                        index--
                    }
                }
                index++
                println("MD $stack")  //(for debugging purposes)
            }

            //while loop to do all the additions and subtractions in the equation (PEMDAS)
            index = 0
            while (ASCounter != 0) {
                when (stack.get(index)) {
                    "+" -> {
                        val result =
                            add(stack.get(index - 1).toDouble(), stack.get(index + 1).toDouble())
                        stackPopAndPush(index, result)
                        ASCounter--
                        index--
                    }
                    "-" -> {
                        val result =
                            subtract(
                                stack.get(index - 1).toDouble(),
                                stack.get(index + 1).toDouble()
                            )
                        stackPopAndPush(index, result)
                        ASCounter--
                        index--
                    }
                }
                index++
                println("AS $stack") //(for debugging purposes)
            }

            return stack.get(0)
        } catch (e: Exception) {
            println("Exception Occurred")
            return "Invalid Input, Press Clear"
        }
    }
}