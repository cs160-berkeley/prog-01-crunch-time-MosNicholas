package com.example.nicholasmoschopoulos.calorieconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversionStats extends AppCompatActivity {
    private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private Map<String, String> conversionTitlesMap = new HashMap<>();
    private Map<String, Integer> conversionRatesMap = new HashMap<>();
    String convertFromMetric;
    String convertIntoMetric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_stats);

        Intent intent = getIntent();
        convertFromMetric = intent.getStringExtra(MainActivity.CONVERT_FROM);
        convertIntoMetric = intent.getStringExtra(MainActivity.CONVERT_TO);

        CharSequence[] conversionOptions = this.getResources().getTextArray(R.array.conversion_options);
        CharSequence[] conversionTitles = this.getResources().getTextArray(R.array.conversion_titles);
        int[] conversionRates = this.getResources().getIntArray(R.array.conversion_rates);
        for(int i=0; i<conversionOptions.length; i++) {
            conversionTitlesMap.put(conversionOptions[i].toString(), conversionTitles[i].toString());
            conversionRatesMap.put(conversionOptions[i].toString(), conversionRates[i]);
        }

        String convertFromTitle = conversionTitlesMap.get(convertFromMetric);
        String convertIntoTitle = conversionTitlesMap.get(convertIntoMetric);

        EditText repCount = (EditText) findViewById(R.id.rep_count_input);
        repCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                TextView convertedInto = (TextView) findViewById(R.id.this_many_reps);
                String repsInputted = s.toString();

                if (repsInputted.isEmpty()) {
                    convertedInto.setText("");
                    return;
                }

                int numReps = Integer.parseInt(repsInputted);
                Float ratio = numReps * (conversionRatesMap.get(convertIntoMetric).floatValue()/conversionRatesMap.get(convertFromMetric).floatValue());
                SpannableString content = new SpannableString(DECIMAL_FORMAT.format(ratio));
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                convertedInto.setText(content);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        TextView convertedFromUnits = (TextView) findViewById(R.id.rep_title);
        convertedFromUnits.setText(convertFromTitle);

        TextView convertedIntoUnits = (TextView) findViewById(R.id.converted_unit);
        convertedIntoUnits.setText(convertIntoTitle);

    }

}
