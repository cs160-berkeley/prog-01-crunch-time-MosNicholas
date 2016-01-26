package com.example.nicholasmoschopoulos.calorieconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String EMPTY_VALUE = "";
    public final static String CONVERT_FROM = "CONVERT_FROM";
    public final static String CONVERT_TO = "CONVERT_TO";

    private List<CharSequence> conversionOptions;
    private boolean hasInstantiated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conversionOptions = Arrays.asList(this.getResources().getTextArray(R.array.conversion_options));

        final Spinner firstSpinner = (Spinner) findViewById(R.id.convert_dialog_first_question);
        ArrayAdapter<CharSequence> adapter = createAdapter(conversionOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstSpinner.setAdapter(adapter);

        final Spinner secondSpinner = (Spinner) findViewById(R.id.convert_dialog_ending);

        firstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<CharSequence> convertInto = deepCopy(conversionOptions);
                convertInto.remove(position);
                convertInto.add(0, EMPTY_VALUE);
                hasInstantiated = false;
                ArrayAdapter<CharSequence> secondSpinnerAdapter = createAdapter(convertInto);
                secondSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                secondSpinner.setAdapter(secondSpinnerAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        secondSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!hasInstantiated) {
                    hasInstantiated = true;
                    return;
                } else if (parent.getSelectedItem().toString() == EMPTY_VALUE) {
                    return;
                }

                Intent intent = createConversionIntent();
                intent.putExtra(CONVERT_FROM, firstSpinner.getSelectedItem().toString());
                intent.putExtra(CONVERT_TO, parent.getSelectedItem().toString());
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private Intent createConversionIntent() {
        return new Intent(this, ConversionStats.class);
    }

    private ArrayAdapter<CharSequence> createAdapter(List<CharSequence> conversionItems) {
        return new ArrayAdapter(this, android.R.layout.simple_spinner_item, conversionItems);
    }

    private List<CharSequence> deepCopy(List<CharSequence> charSequences) {
        List<CharSequence> newList = new ArrayList<>();
        for (CharSequence s : charSequences) {
            newList.add(s);
        }

        return newList;
    }
}
