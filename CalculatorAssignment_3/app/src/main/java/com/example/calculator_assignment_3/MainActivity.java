package com.example.calculator_assignment_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    com.google.android.material.textfield.TextInputEditText input1, input2;
    com.google.android.material.button.MaterialButton plusButton, minusButton, multiplyButton, divideButton;
    TextView resTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        input1 = findViewById(R.id.InputOne);
        input2 = findViewById(R.id.InputTwo);

        resTextView = findViewById(R.id.ResultTextView);

        plusButton = findViewById(R.id.PlusButton);
        minusButton = findViewById(R.id.MinusButton);
        multiplyButton = findViewById(R.id.MultipleButton);
        divideButton = findViewById(R.id.DivisionButton);

        plusButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        divideButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // input string checker
        if (input1.getText().toString().trim().length() == 0) {
            input1.setText("0");
        }
        if (input2.getText().toString().trim().length() == 0) {
            input2.setText("0");
        }

        float result = 0;
        float number1 = Float.parseFloat(input1.getText().toString());
        float number2 = Float.parseFloat(input2.getText().toString());

        int buttonPressed = v.getId();
        if (buttonPressed == R.id.PlusButton) {
            result = number1 + number2;
        } else if (buttonPressed == R.id.MinusButton) {
            result = number1 - number2;
        } else if (buttonPressed == R.id.MultipleButton) {
            result = number1 * number2;
        } else if (buttonPressed == R.id.DivisionButton) {
            result = number1 / number2;
        }

        resTextView.setText(String.valueOf(result));
    }
}