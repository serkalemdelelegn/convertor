package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String expression = "";  // Stores full expression (e.g., "78 + 35")
    private boolean lastInputWasOperator = false;  // Ensures valid input sequence

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                       View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        // UI Elements
        TextView screen = findViewById(R.id.textview_display);

        // Define buttons
        Button ac = findViewById(R.id.button_ac);
        Button div = findViewById(R.id.button_div);
        Button sev = findViewById(R.id.button_7);
        Button eight = findViewById(R.id.button_8);
        Button nine = findViewById(R.id.button_9);
        Button multiply = findViewById(R.id.button_multiply);
        Button four = findViewById(R.id.button_4);
        Button five = findViewById(R.id.button_5);
        Button six = findViewById(R.id.button_6);
        Button subtract = findViewById(R.id.button_subtract);
        Button one = findViewById(R.id.button_1);
        Button two = findViewById(R.id.button_2);
        Button three = findViewById(R.id.button_3);
        Button add = findViewById(R.id.button_add);
        Button del = findViewById(R.id.button_del);
        Button zero = findViewById(R.id.button_0);
        Button dot = findViewById(R.id.button_dot);
        Button equal = findViewById(R.id.button_equals);
        Button numConv = findViewById(R.id.button_numconv);

        // Assign number and operator button click listeners
        setNumberClickListener(sev, screen);
        setNumberClickListener(eight, screen);
        setNumberClickListener(nine, screen);
        setNumberClickListener(four, screen);
        setNumberClickListener(five, screen);
        setNumberClickListener(six, screen);
        setNumberClickListener(one, screen);
        setNumberClickListener(two, screen);
        setNumberClickListener(three, screen);
        setNumberClickListener(zero, screen);
        setNumberClickListener(dot, screen);

        setOperatorClickListener(add, screen);
        setOperatorClickListener(subtract, screen);
        setOperatorClickListener(multiply, screen);
        setOperatorClickListener(div, screen);

        // Equals button (calculate result)
        equal.setOnClickListener(v -> {
            if (!expression.isEmpty()) {
                double result = evaluateExpression(expression);
                screen.setText(expression + " = " + result);
                expression = String.valueOf(result);
            }
        });

        // Clear button (AC)
        ac.setOnClickListener(v -> {
            expression = "";
            screen.setText("0");
        });

        // Delete last character (DEL)
        del.setOnClickListener(v -> {
            if (!expression.isEmpty()) {
                expression = expression.substring(0, expression.length() - 1);
                screen.setText(expression.isEmpty() ? "0" : expression);
            }
        });

        // Open Number Converter Activity
        numConv.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NumberConv.class);
            startActivity(intent);
        });
    }

    // Handles number button clicks
    private void setNumberClickListener(Button button, TextView screen) {
        button.setOnClickListener(v -> {
            String value = button.getText().toString();
            expression += value;
            screen.setText(expression);
            lastInputWasOperator = false;
        });
    }

    // Handles operator button clicks
    private void setOperatorClickListener(Button button, TextView screen) {
        button.setOnClickListener(v -> {
            if (!expression.isEmpty() && !lastInputWasOperator) {
                expression += " " + button.getText().toString() + " ";
                screen.setText(expression);
                lastInputWasOperator = true;
            }
        });
    }

    // Evaluates the mathematical expression
    private double evaluateExpression(String expression) {
        try {
            String[] tokens = expression.split(" ");
            double result = Double.parseDouble(tokens[0]);

            for (int i = 1; i < tokens.length; i += 2) {
                String operator = tokens[i];
                double nextNumber = Double.parseDouble(tokens[i + 1]);

                switch (operator) {
                    case "+":
                        result += nextNumber;
                        break;
                    case "-":
                        result -= nextNumber;
                        break;
                    case "X":
                        result *= nextNumber;
                        break;
                    case "/":
                        if (nextNumber != 0) {
                            result /= nextNumber;
                        } else {
                            return 0;  // Prevent division by zero
                        }
                        break;
                }
            }
            return result;
        } catch (Exception e) {
            return 0;  // Return 0 in case of an error
        }
    }
}
