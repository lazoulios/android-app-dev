package com.example.androidappproject;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class EditCarActivity extends AppCompatActivity {

    private int carId;
    private CarDatabaseHelper dbHelper;

    private EditText editName, editDescription, editMake, editModel, editEngine;
    private Spinner spinnerGas, spinnerDistance, spinnerVolume, spinnerConsumption;



    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        // Toolbar setup
        MaterialToolbar toolbar = findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Car");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        editName = findViewById(R.id.edit_name);
        editDescription = findViewById(R.id.edit_description);
        editMake = findViewById(R.id.edit_make);
        editModel = findViewById(R.id.edit_model);
        editEngine = findViewById(R.id.edit_engine);

        spinnerGas = findViewById(R.id.edit_gas_type_spinner);
        spinnerDistance = findViewById(R.id.edit_distance_unit_spinner);
        spinnerVolume = findViewById(R.id.edit_volume_unit_spinner);
        spinnerConsumption = findViewById(R.id.edit_consumption_unit_spinner);



        // Options for Spinners
        String[] gasTypes = {"Gasoline", "Diesel", "Electric", "Hybrid"};
        String[] distanceUnits = {"Kilometers", "Miles"};
        String[] volumeUnits = {"Liters", "Gallons"};
        String[] consumptionUnits = {"L/100km", "mpg"};

        // Adapters
        ArrayAdapter<String> gasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gasTypes);
        gasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGas.setAdapter(gasAdapter);

        ArrayAdapter<String> distanceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, distanceUnits);
        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistance.setAdapter(distanceAdapter);

        ArrayAdapter<String> volumeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, volumeUnits);
        volumeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVolume.setAdapter(volumeAdapter);

        ArrayAdapter<String> consumptionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, consumptionUnits);
        consumptionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConsumption.setAdapter(consumptionAdapter);

        // Get intent data
        int carId = getIntent().getIntExtra("car_id", -1);
        if (carId == -1) {
            Toast.makeText(this, "Car not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        editName.setText(getIntent().getStringExtra("name"));
        editDescription.setText(getIntent().getStringExtra("description"));
        editMake.setText(getIntent().getStringExtra("make"));
        editModel.setText(getIntent().getStringExtra("model"));
        editEngine.setText(String.valueOf(getIntent().getIntExtra("engine", 0)));

        spinnerGas.setSelection(gasAdapter.getPosition(getIntent().getStringExtra("gasType")));
        spinnerDistance.setSelection(distanceAdapter.getPosition(getIntent().getStringExtra("distanceUnit")));
        spinnerVolume.setSelection(volumeAdapter.getPosition(getIntent().getStringExtra("volumeUnit")));
        spinnerConsumption.setSelection(consumptionAdapter.getPosition(getIntent().getStringExtra("consumptionUnit")));


        dbHelper = new CarDatabaseHelper(this);
        saveButton = findViewById(R.id.button_save_car);



        // Save changes
        saveButton.setOnClickListener(v -> {

            ContentValues values = new ContentValues();

            try {
                values.put("name", editName.getText().toString());
                values.put("description", editDescription.getText().toString());
                values.put("make", editMake.getText().toString());
                values.put("model", editModel.getText().toString());

                String engineText = editEngine.getText().toString();
                int engineValue = Integer.parseInt(engineText);
                values.put("engine", engineValue);  // ΣΩΣΤΟ ΟΝΟΜΑ στηλών DB

                values.put("gas_type", spinnerGas.getSelectedItem().toString());
                values.put("distance_unit", spinnerDistance.getSelectedItem().toString());
                values.put("volume_unit", spinnerVolume.getSelectedItem().toString());
                values.put("consumption_unit", spinnerConsumption.getSelectedItem().toString());

                Log.d("SaveClick", "Calling updateCar()...");
                boolean updated = dbHelper.updateCar(carId, values);
                Log.d("SaveClick", "Update success: " + updated);

                if (updated) {
                    Toast.makeText(this, "Car updated successfully", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(this, "Failed to update car", Toast.LENGTH_SHORT).show();
                }

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Engine must be a valid number", Toast.LENGTH_SHORT).show();
                Log.e("SaveClick", "Invalid engine input: " + e.getMessage());
            } catch (Exception e) {
                Toast.makeText(this, "Error updating car", Toast.LENGTH_SHORT).show();
                Log.e("SaveClick", "Exception: " + e.getMessage(), e);
            }
        });
    }
}
