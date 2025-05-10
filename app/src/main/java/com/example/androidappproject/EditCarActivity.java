package com.example.androidappproject;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class EditCarActivity extends AppCompatActivity {

    private int carId;
    private CarDatabaseHelper dbHelper;

    private EditText editName, editDescription, editMake, editModel, editEngine,
            editGasType, editDistanceUnit, editVolumeUnit, editConsumptionUnit;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        // Setup toolbar
        MaterialToolbar toolbar = findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Car");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Initialize views
        editName = findViewById(R.id.edit_name);
        editDescription = findViewById(R.id.edit_description);
        editMake = findViewById(R.id.edit_make);
        editModel = findViewById(R.id.edit_model);
        editEngine = findViewById(R.id.edit_engine);
        editGasType = findViewById(R.id.edit_gas_type);
        editDistanceUnit = findViewById(R.id.edit_distance_unit);
        editVolumeUnit = findViewById(R.id.edit_volume_unit);
        editConsumptionUnit = findViewById(R.id.edit_consumption_unit);
        saveButton = findViewById(R.id.button_save_car);

        // Get extras from intent
        carId = getIntent().getIntExtra("car_id", -1);
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
        editGasType.setText(getIntent().getStringExtra("gasType"));
        editDistanceUnit.setText(getIntent().getStringExtra("distanceUnit"));
        editVolumeUnit.setText(getIntent().getStringExtra("volumeUnit"));
        editConsumptionUnit.setText(getIntent().getStringExtra("consumptionUnit"));

        dbHelper = new CarDatabaseHelper(this);

        // Save changes
        saveButton.setOnClickListener(v -> {
            Log.d("SaveClick", "Save button clicked. Car ID: " + carId);
            ContentValues values = new ContentValues();

            try {
                values.put("name", editName.getText().toString());
                values.put("description", editDescription.getText().toString());
                values.put("make", editMake.getText().toString());
                values.put("model", editModel.getText().toString());

                String engineText = editEngine.getText().toString();
                int engineValue = Integer.parseInt(engineText);
                values.put("engine", engineValue);  // ΣΩΣΤΟ ΟΝΟΜΑ στηλών DB

                values.put("gas_type", editGasType.getText().toString());
                values.put("distance_unit", editDistanceUnit.getText().toString());
                values.put("volume_unit", editVolumeUnit.getText().toString());
                values.put("consumption_unit", editConsumptionUnit.getText().toString());

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
