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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find views
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        // Setup hamburger toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Find custom drawer buttons
        navHome = findViewById(R.id.nav_home);
        navAddCar = findViewById(R.id.nav_add_car);
        navCompare = findViewById(R.id.nav_compare);

        // Handle Drawer Item Clicks
        navHome.setOnClickListener(v -> {
            drawerLayout.closeDrawers();
            Toast.makeText(AddCarActivity.this, "Home selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddCarActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: close AddCarActivity
        });

        navAddCar.setOnClickListener(v -> {
            drawerLayout.closeDrawers();
            Toast.makeText(AddCarActivity.this, "Already on Add Car", Toast.LENGTH_SHORT).show();
            // You are already on Add Car, no need to open again
        });

        navCompare.setOnClickListener(v -> {
            drawerLayout.closeDrawers();
            Toast.makeText(AddCarActivity.this, "Compare selected", Toast.LENGTH_SHORT).show();
            // TODO: Start CompareActivity when you make it
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

