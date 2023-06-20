package com.calculator.calculator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calculator.calculator.ui.theme.CalculationHistoryManager
import com.calculator.calculator.ui.theme.Grey
import com.calculator.calculator.ui.theme.LightGrey
import com.calculator.calculator.ui.theme.RightAlignedEditText
import java.text.DecimalFormat


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Calculator() {
    val context = LocalContext.current
    var currentInput by remember { mutableStateOf("") }
    var isHistoryVisible by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("") }
    val calculationHistoryManager = CalculationHistoryManager(LocalContext.current)
    fun isSymbol(symbol: String): Boolean {
        return symbol in listOf("+", "-", "*", "/", "%")
    }

    fun getLastDigit(number: Int): Int {
        return number % 10
    }

    fun appendToInput(symbol: String) {
        val lastDigit = getLastDigit(currentInput.toInt())

        if (currentInput.isNotEmpty() && isSymbol(lastDigit.toString()) && isSymbol(symbol)) {
            // Replace the last operational symbol with the new symbol
            currentInput = currentInput.dropLast(1) + symbol
        } else {
            currentInput += symbol
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(), color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(painter = painterResource(if (isHistoryVisible) R.drawable.baseline_cleaning_services_24 else R.drawable.baseline_history_24),
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(30.dp)
                        .wrapContentWidth()
//                        .align(Alignment.Start)
                        .clickable {
                            if (isHistoryVisible) {
                                isHistoryVisible = false
                                calculationHistoryManager.clearCalculationHistory()
                            } else {
                                isHistoryVisible = true

                            }
                        })
                if (isHistoryVisible) {
                    Image(painter = painterResource(R.drawable.baseline_cancel_24),
                        contentDescription = "Image",
                        modifier = Modifier
                            .size(30.dp)
                            .wrapContentWidth()
//                        .align(Alignment.End)
                            .clickable {
                                isHistoryVisible = false
                            })
                }

            }


            AnimatedVisibility(
                visible = isHistoryVisible,
                enter = slideInHorizontally()
            ) {
                if (!calculationHistoryManager.getCalculationHistory().isEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {
                        items(calculationHistoryManager.getCalculationHistory()) {
                            Text(
                                text = it,
                                fontSize = 24.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            )
                        }
                    }

                } else {
                    Text(
                        text = "No History Available",
                        fontSize = 22.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp)
                    )
                }
            }
            AnimatedVisibility(
                visible = !isHistoryVisible,
                enter = slideInHorizontally(),
                exit = slideOutHorizontally()
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(16.dp))
                    RightAlignedEditText(text = currentInput, onTextChange = {
                        if (currentInput.contains("+") || currentInput.contains("-") || currentInput.contains(
                                "*"
                            ) || currentInput.contains("/")
                        ) {
                            result = calculateResult(currentInput)
                        }
                    })
                    Text(
                        text = removeTrailingZeros(result),
                        fontSize = 40.sp,
                        color = Color.White,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CalculatorButton("AC", onClick = {
                            currentInput = ""
                            result = ""
                        })
                        CalculatorButton("%", onClick = { currentInput += "%" })
                        CalculatorButton("C",
                            onClick = {
                                currentInput = currentInput.dropLast(1)
//                                currentInput = clearElement(currentInput.toTextFieldValue()).text
                            })
                        CalculatorButton("÷", onClick = { currentInput += "/" })
                    }

                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CalculatorButton("7",
                            backgroundColor = LightGrey,
                            onClick = {
                                currentInput += "7"
                                result = ""
                            })
                        CalculatorButton("8",
                            backgroundColor = LightGrey,
                            onClick = {
                                currentInput += "8"
                                result = ""
                            })
                        CalculatorButton("9",
                            backgroundColor = LightGrey,
                            onClick = {
                                currentInput += "9"
                                result = ""
                            })
                        CalculatorButton("X", onClick = { currentInput += "*" })
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CalculatorButton("4",
                            backgroundColor = LightGrey,
                            onClick = {
                                currentInput += "4"
                                result = ""
                            })
                        CalculatorButton("5",
                            backgroundColor = LightGrey,
                            onClick = {
                                currentInput += "5"
                                result = ""
                            })
                        CalculatorButton("6",
                            backgroundColor = LightGrey,
                            onClick = {
                                currentInput += "6"
                                result = ""
                            })
                        CalculatorButton("-", onClick = { currentInput += "-" })
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CalculatorButton("1",
                            backgroundColor = LightGrey,
                            onClick = {
                                currentInput += "1"
                                result = ""
                            })
                        CalculatorButton("2",
                            backgroundColor = LightGrey,
                            onClick = {
                                currentInput += "2"
                                result = ""
                            })
                        CalculatorButton("3",
                            backgroundColor = LightGrey,
                            onClick = {
                                currentInput += "3"
                                result = ""
                            })
                        CalculatorButton("+", onClick = { currentInput += "+" })
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CalculatorButton("00", backgroundColor = LightGrey, onClick = {
                            currentInput += "00"
                            result = ""
                        })
                        CalculatorButton("0", backgroundColor = LightGrey, onClick = {
                            currentInput += "0"
                            result = ""
                        })
                        CalculatorButton(".", backgroundColor = LightGrey, onClick = {
                            currentInput += "."
                            result = ""

                        })
                        CalculatorButton(
                            "=",
                            backgroundColor = MaterialTheme.colors.primary,
                            onClick = {
                                result = calculateResult(currentInput)
                                calculationHistoryManager.saveCalculation(
                                    currentInput,
                                    removeTrailingZeros(result)
                                )
                                currentInput = ""
                            })
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(size = 10.dp),
    backgroundColor: Color = Grey,
    contentColor: Color = Color.White,
    borderStroke: BorderStroke? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(2.dp)
            .height(65.dp)
            .width(65.dp),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor, contentColor = contentColor
        ),
        border = borderStroke
    ) {
        Text(text = text, Modifier.size(20.dp), textAlign = TextAlign.Center)
    }
}


fun calculateResult(input: String): String {
    return try {
        val result = evaluate(input)
        result.toString()
    } catch (e: Exception) {
        "Error"
    }
}


fun evaluate(input: String): Double {
    return object : Any() {
        var pos = -1
        var ch = ' '

        fun nextChar() {
            ch = if (++pos < input.length) input[pos] else '\u0000'
        }

        fun eat(charToEat: Char): Boolean {
            while (ch == ' ') nextChar()
            if (ch == charToEat) {
                nextChar()
                return true
            }
            return false
        }

        fun parse(): Double {
            nextChar()
            val x = parseExpression()
            if (pos < input.length) throw RuntimeException("Unexpected: " + ch)
            return x
        }

        fun parseExpression(): Double {
            var x = parseTerm()
            while (true) {
                when {
                    eat('+') -> x += parseTerm()
                    eat('-') -> x -= parseTerm()
                    else -> return x
                }
            }
        }

        fun parseTerm(): Double {
            var x = parseFactor()
            while (true) {
                when {
                    eat('*') -> x *= parseFactor()
                    eat('/') -> x /= parseFactor()
                    eat('%') -> x *= parseFactor() / 100
                    else -> return x
                }
            }
        }

        fun parseFactor(): Double {
            if (eat('+')) return parseFactor()
            if (eat('-')) return -parseFactor()

            var x: Double
            val startPos = this.pos
            if (eat('(')) {
                x = parseExpression()
                eat(')')
            } else if (ch in '0'..'9' || ch == '.') {
                while (ch in '0'..'9' || ch == '.') nextChar()
                x = input.substring(startPos, this.pos).toDouble()
            } else {
                throw RuntimeException("Unexpected: " + ch)
            }

            return x
        }
    }.parse()
}

fun removeTrailingZeros(value: String): String {
    val doubleValue = value.toDoubleOrNull()
    return doubleValue?.let {
        val decimalFormat = DecimalFormat("#.##")
        decimalFormat.format(it)
    } ?: value
}


