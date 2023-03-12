package com.example.tipcalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// Calculates bills using 15% and custom percentage tips.
import java.text.NumberFormat;  // for currency formatting

import android.app.Activity;    // base class for activities
import android.os.Bundle;       // for saving state information
import android.text.Editable;   // for EditText event handling
import android.text.TextWatcher;    // EditText listener
import android.widget.EditText;     // for bill amount input
import android.widget.SeekBar;      // for changing custom tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView; // for displaying text

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // currency and percent formatters
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0; // bill amount entered by the user
    private double customPercent = 0.18; // initial custom tip percentage
    private TextView amountDisplayTextView; // shows formatted bill amount
    private TextView percentCustomTextView; // shows custom tip percentage
    private TextView tip15TextView; // shows 15% tip
    private TextView total15TextView; // shows total with 15% tip
    private TextView tipCustomTextView; // shows custom tip amount
    private TextView totalCustomTextView; // shows total with custom tip

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // // get references to the TextViews
        // that MainActivity interacts with programmatically
        amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
        percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
        tip15TextView = (TextView) findViewById(R.id.tip15TextView);
        total15TextView = (TextView) findViewById(R.id.total15TextView);
        tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);
        totalCustomTextView = (TextView) findViewById(R.id.totalCustomTextView);

        // update GUI based on billAmount and customPercent
        amountDisplayTextView.setText(currencyFormat.format(billAmount));
        updateStard();  // update the 15% tip TextViews
        updateCustom();  // update the custom tip TextViews

        // set amountEditText's TextWatcher
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // set customTipSeekBar's OnSeekBarChangeListener
        SeekBar customTipSeekBar = (SeekBar) findViewById(R.id.customTipSeekBar);
        customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListenr);
    }

    private void updateStard() {
        double fifteenPercentTip = billAmount * 0.15;
        double fifteenPercentTotal = billAmount + fifteenPercentTip;

        // display 15% tip and total formatted as currency
        tip15TextView.setText(currencyFormat.format(fifteenPercentTip));
        total15TextView.setText(currencyFormat.format(fifteenPercentTotal));
    }

    // updates the custom tip and total TextViews
    private void updateCustom() {
        // show customPercent in percentCustomTextView formatted as %
        percentCustomTextView.setText(percentFormat.format(customPercent));

        // calculate the custom tip and total
        double customTip = billAmount * customPercent;
        double customTotal = billAmount + customTip;

        // display custom tip and total formatted as currency
        tipCustomTextView.setText(currencyFormat.format(customTip));
        totalCustomTextView.setText(currencyFormat.format(customTotal));
    } // end method

    private OnSeekBarChangeListener customSeekBarListenr = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            customPercent = progress / 100.0;
            updateCustom();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // not need
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // not need
        }
    };

    private TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // convert amountEditText's text to a double
            try {
                billAmount = Double.parseDouble(s.toString()) / 100.0;
            }
            catch (NumberFormatException ex)
            {
                billAmount = 0.0; // default value in exception occurs
            }

            amountDisplayTextView.setText(currencyFormat.format(billAmount));
            updateStard();
            updateCustom();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // no need
        }

        @Override
        public void afterTextChanged(Editable s) {
            // no need
        }
    };
}