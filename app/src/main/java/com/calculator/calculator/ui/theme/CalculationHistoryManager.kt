package com.calculator.calculator.ui.theme

import android.content.Context
import android.content.SharedPreferences

class CalculationHistoryManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("CalculationHistory", Context.MODE_PRIVATE)
    }

    fun saveCalculation(expression: String, result: String) {
        val history = getCalculationHistory().toMutableList()
        history.add("$expression = $result")

        val editor = sharedPreferences.edit()
        editor.putStringSet("history", history.toSet())
        editor.apply()
    }

    fun getCalculationHistory(): List<String> {
        return sharedPreferences.getStringSet("history", emptySet())?.toList() ?: emptyList()
    }

    fun clearCalculationHistory() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
