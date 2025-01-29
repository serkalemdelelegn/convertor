package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NumberConv extends AppCompatActivity {

    private TextView display;
    private String currentInput = "";
    private String currentBase = "10"; // Default to decimal base

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_conv);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |    // Hide status bar
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | // Hide navigation bar
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // Enable immersive mode
        );

        Intent caller= getIntent();
        Button button = findViewById(R.id.button_stand);
        // Set an OnClickListener on the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NumberConv.this, MainActivity.class);
                startActivity(intent);
            }
        });

        display = findViewById(R.id.textview_display);

        // Set OnClickListener for Base buttons (B, O, H)
        Button buttonB = findViewById(R.id.button_B);
        Button buttonO = findViewById(R.id.button_O);
        Button buttonH = findViewById(R.id.button_H);

        // Button B (Binary) Click Handler
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBase = "B";
                updateDisplay();
            }
        });

        // Button O (Octal) Click Handler
        buttonO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBase = "O";
                updateDisplay();
            }
        });

        // Button H (Hexadecimal) Click Handler
        buttonH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBase = "H";
                updateDisplay();
            }
        });

        // Number Buttons (0-9)
        setNumberButtonListener(R.id.button_0);
        setNumberButtonListener(R.id.button_1);
        setNumberButtonListener(R.id.button_2);
        setNumberButtonListener(R.id.button_3);
        setNumberButtonListener(R.id.button_4);
        setNumberButtonListener(R.id.button_5);
        setNumberButtonListener(R.id.button_6);
        setNumberButtonListener(R.id.button_7);
        setNumberButtonListener(R.id.button_8);
        setNumberButtonListener(R.id.button_9);

        // Button DEL (Delete)
        Button buttonDel = findViewById(R.id.button_del);
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    updateDisplay();
                }
            }
        });

        // Button AC (Clear All)
        Button buttonAC = findViewById(R.id.button_ac);
        buttonAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput = "";
                currentBase = "10"; // Reset to decimal base
                updateDisplay();
            }
        });
    }

    private void setNumberButtonListener(int buttonId) {
        Button numberButton = findViewById(buttonId);
        numberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput += numberButton.getText().toString();
                updateDisplay();
            }
        });
    }

    private void updateDisplay() {
        String result = currentInput;

        // Convert the input based on selected base
        try {
            if (!currentInput.isEmpty()) {
                int decimalValue = Integer.parseInt(currentInput, 10); // Default decimal
                if ("B".equals(currentBase)) {
                    result = Integer.toBinaryString(decimalValue); // Convert to binary
                } else if ("O".equals(currentBase)) {
                    result = Integer.toOctalString(decimalValue); // Convert to octal
                } else if ("H".equals(currentBase)) {
                    result = Integer.toHexString(decimalValue).toUpperCase(); // Convert to hexadecimal
                }
            }
        } catch (NumberFormatException e) {
            result = "Error";
        }

        display.setText(result);
    }
}



