package com.example.androidappproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class AddCarActivity extends AppCompatActivity {

    private Spinner spinnerGas, spinnerDistanceUnit, spinnerVolumeUnit, spinnerConsumptionUnit;
    private EditText inputCarName, inputDescription, inputMake, inputModel, inputEngine;

    private MaterialToolbar toolbar;
    private DrawerLayout drawerLayout;
    private LinearLayout navHome, navAddCar, navCompare;
    private NavigationView navView;
    private CarDatabaseHelper dbHelper;  // Add database helper


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car_screen);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            // Πήγαινε στην αρχική οθόνη (MainActivity)
            Intent intent = new Intent(AddCarActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });


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
        dbHelper = new CarDatabaseHelper(this);

        ExtendedFloatingActionButton saveButton = findViewById(R.id.saveCarButton);
        saveButton.setOnClickListener(v -> saveCar());
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

    private void saveCar() {
        try {
            // Collect input values
            String carName = inputCarName.getText().toString().trim();
            String description = inputDescription.getText().toString().trim();
            String make = inputMake.getText().toString().trim();
            String model = inputModel.getText().toString().trim();
            String engineText = inputEngine.getText().toString().trim();

            // Validate
            if (carName.isEmpty() || make.isEmpty() || model.isEmpty() || engineText.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            int engineDisplacement = Integer.parseInt(engineText);
            String gasType = spinnerGas.getSelectedItem().toString();
            String distanceUnit = spinnerDistanceUnit.getSelectedItem().toString();
            String volumeUnit = spinnerVolumeUnit.getSelectedItem().toString();
            String consumptionUnit = spinnerConsumptionUnit.getSelectedItem().toString();

            // Create Car object
            Car car = new Car(
                    0, // ID will be set after insert
                    carName, description, make, model, engineDisplacement, gasType, distanceUnit, volumeUnit, consumptionUnit
            );

            CarDatabaseHelper dbHelper = new CarDatabaseHelper(this);
            long carId = dbHelper.insertCar(car);

            // Optional: update the car object if needed
            car.id = (int) carId;

            Toast.makeText(this, "Car Saved!", Toast.LENGTH_SHORT).show();
            finish();  // Close the activity after saving

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Engine displacement must be a number.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error saving car: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

