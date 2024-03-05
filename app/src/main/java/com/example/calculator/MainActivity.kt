package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.lang.IllegalArgumentException
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var displayEditText: EditText
    private var currentInput = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayEditText = findViewById(R.id.textView)

        val buttonIds = arrayOf(
            R.id.zero, R.id.one, R.id.two, R.id.three, R.id.four,
            R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine,
            R.id.point, R.id.add, R.id.subtract, R.id.multiply, R.id.divide
        )

        buttonIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                onButtonClick((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.clear).setOnClickListener {
            clearDisplay()
        }

        findViewById<Button>(R.id.backspace).setOnClickListener {
            removeLastChar()
        }

        findViewById<Button>(R.id.equal).setOnClickListener {
            evaluateExpression()
        }
    }

    private fun onButtonClick(input: String) {
        currentInput.append(input)
        displayEditText.setText(currentInput.toString())
    }

    private fun clearDisplay() {
        currentInput.clear()
        displayEditText.setText("")
    }

    private fun removeLastChar() {
        if (currentInput.isNotEmpty()) {
            currentInput.deleteCharAt(currentInput.length - 1)
            displayEditText.setText(currentInput.toString())
        }
    }

    private fun evaluateExpression() {
        val expression = displayEditText.text.toString()
        try {
            val result = eval(expression)
                displayEditText.setText(result.toString())
        } catch (e: ArithmeticException){
            displayEditText.setText("Error")
        } catch (e: IllegalArgumentException) {
            displayEditText.setText(("IllegalArgument"))
        }
    }

    private fun eval(expression: String): Number {
        val exp = ExpressionBuilder(expression).build()
        return exp.evaluate()
    }
}
