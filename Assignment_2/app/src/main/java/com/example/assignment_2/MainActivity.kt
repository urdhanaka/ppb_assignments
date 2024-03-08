package com.example.assignment_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // The Toolbar defined in the layout has the id "my_toolbar".
        setSupportActionBar(findViewById(R.id.MainToolbar))

        val resultView = findViewById<TextView>(R.id.resultTextView)
        val hitungButton = findViewById<Button>(R.id.buttonDefault)
        val exitButton = findViewById<Button>(R.id.buttonExit)
        val inputExpression = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.inputText)

        hitungButton.setOnClickListener {
            val expression = ExpressionBuilder(inputExpression.text.toString()).build().evaluate().toInt()
            resultView.text = expression.toString()
        }

        exitButton.setOnClickListener {
            finishAndRemoveTask()
        }
    }
}
