package com.example.androidappproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class AddCarActivity extends AppCompatActivity {

    private Spinner spinnerGas, spinnerDistanceUnit, spinnerVolumeUnit, spinnerConsumptionUnit;
    private EditText inputCarName, inputDescription, inputMake, inputModel, inputEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car_screen);

        MaterialToolbar toolbar = findViewById(R.id.add_car_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        inputCarName = findViewById(R.id.inputCarName);
        inputDescription = findViewById(R.id.inputDescription);
        inputMake = findViewById(R.id.inputMake);
        inputModel = findViewById(R.id.inputModel);
        inputEngine = findViewById(R.id.inputEngine);

        spinnerGas = findViewById(R.id.spinnerGas);
        spinnerDistanceUnit = findViewById(R.id.spinnerDistanceUnit);
        spinnerVolumeUnit = findViewById(R.id.spinnerVolumeUnit);
        spinnerConsumptionUnit = findViewById(R.id.spinnerConsumptionUnit);

        setupSpinners();

        ExtendedFloatingActionButton saveButton = findViewById(R.id.saveCarButton);
        saveButton.setOnClickListener(v -> {
            // Validate & Save
            Toast.makeText(this, "Car Saved!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void setupSpinners() {
        String[] gasOptions = {"Gasoline", "Diesel", "Electric", "Hybrid"};
        String[] distOptions = {"Kilometers", "Miles"};
        String[] volOptions = {"Liters", "Gallons"};
        String[] consOptions = {"L/100km", "mpg"};

        spinnerGas.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, gasOptions));
        spinnerDistanceUnit.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, distOptions));
        spinnerVolumeUnit.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, volOptions));
        spinnerConsumptionUnit.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, consOptions));
    }
}

