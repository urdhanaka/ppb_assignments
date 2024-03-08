package com.example.assignment_1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void generateText(View v) {
        Random rand = new Random();
        String[] randomWords = {"test", "random", "words", "greatest", "not", "simple"};
        LinearLayout textPlaceholderLayout = findViewById(R.id.textPlaceholder);
        int selectedWordsIndex = rand.nextInt(randomWords.length);
        TextView temp = new TextView(this);
        temp.setTextColor(Color.BLACK);
        temp.setText(randomWords[selectedWordsIndex]);
        textPlaceholderLayout.addView(temp);
    }
}