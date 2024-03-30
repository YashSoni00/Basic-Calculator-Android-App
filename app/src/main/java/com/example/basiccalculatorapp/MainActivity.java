package com.example.basiccalculatorapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.function.Function;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make OnClickListeners for all the buttons
        TextView tvClear = findViewById(R.id.tvAC);
        TextView tvDel = findViewById(R.id.tvDel);
        TextView tv0 = findViewById(R.id.tv0);
        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);
        TextView tv3 = findViewById(R.id.tv3);
        TextView tv4 = findViewById(R.id.tv4);
        TextView tv5 = findViewById(R.id.tv5);
        TextView tv6 = findViewById(R.id.tv6);
        TextView tv7 = findViewById(R.id.tv7);
        TextView tv8 = findViewById(R.id.tv8);
        TextView tv9 = findViewById(R.id.tv9);
        TextView tvDot = findViewById(R.id.tvDot);
        TextView tvEquals = findViewById(R.id.tvAns);
        TextView tvPlus = findViewById(R.id.tvAdd);
        TextView tvMinus = findViewById(R.id.tvSub);
        TextView tvMultiply = findViewById(R.id.tvMul);
        TextView tvDivide = findViewById(R.id.tvDiv);

        // Function to update the text view
        Function<String, Void> updateTextView = (String text) -> {
            TextView tv = findViewById(R.id.display);
            HorizontalScrollView hsv = findViewById(R.id.hsv);
            hsv.postDelayed(new Runnable() {
                public void run() {
                    hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                }
            }, 100L);

            // Get the current text
            String currentText = tv.getText().toString();

            // If the current text is 0, replace it with the new text
            if ((currentText.equals("0") || currentText.equals("Error")
                    || currentText.equals("Invalid expression"))
                    && !text.equals("DEL") && !text.equals("AC")) {
                // These are the only cases where the current text is replaced
                tv.setText(text);
            } else if (text.equals("DEL")) {
                // If the new text is DEL, remove the last character
                if (currentText.length() > 1) {
                    if (currentText.charAt(currentText.length() - 1) == ' ')
                        // If the last character is a space, remove the last 3 characters -> (operator and space)
                        tv.setText(currentText.substring(0, currentText.length() - 3));
                    else
                        tv.setText(currentText.substring(0, currentText.length() - 1));
                } else {
                    tv.setText("0");
                }
            } else if (text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/")) {
                // If the new text is an operator, add a space before and after it
                tv.setText(currentText + " " + text + " ");
            } else if (text.equals("=")) {
                // If the new text is =, evaluate the expression
                tv.setText(Calculator.getResult(currentText));
            } else if (text.equals("AC")) {
                // If the new text is AC, clear the text view
                tv.setText("0");
            } else {
                // Otherwise, append the new text to the current text
                tv.setText(currentText + text);
            }
            return null;
        };

        // Set the OnClickListener for each button
        tv0.setOnClickListener(v -> updateTextView.apply("0"));
        tv1.setOnClickListener(v -> updateTextView.apply("1"));
        tv2.setOnClickListener(v -> updateTextView.apply("2"));
        tv3.setOnClickListener(v -> updateTextView.apply("3"));
        tv4.setOnClickListener(v -> updateTextView.apply("4"));
        tv5.setOnClickListener(v -> updateTextView.apply("5"));
        tv6.setOnClickListener(v -> updateTextView.apply("6"));
        tv7.setOnClickListener(v -> updateTextView.apply("7"));
        tv8.setOnClickListener(v -> updateTextView.apply("8"));
        tv9.setOnClickListener(v -> updateTextView.apply("9"));
        tvPlus.setOnClickListener(v -> updateTextView.apply("+"));
        tvMinus.setOnClickListener(v -> updateTextView.apply("-"));
        tvMultiply.setOnClickListener(v -> updateTextView.apply("*"));
        tvDivide.setOnClickListener(v -> updateTextView.apply("/"));
        tvDel.setOnClickListener(v -> updateTextView.apply("DEL"));
        tvDot.setOnClickListener(v -> updateTextView.apply("."));
        tvEquals.setOnClickListener(v -> updateTextView.apply("="));
        tvClear.setOnClickListener(v -> updateTextView.apply("AC"));
    }
}