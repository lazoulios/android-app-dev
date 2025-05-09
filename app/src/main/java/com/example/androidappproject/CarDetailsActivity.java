package com.example.androidappproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class CarDetailsActivity extends AppCompatActivity {

    private int carId;
    private CarDatabaseHelper dbHelper;
    private ExtendedFloatingActionButton addTripButton;
    private TextView carName, carDescription, carMake, carModel, carEngine, carGas, carDistance, carVolume, carConsumption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        MaterialToolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Get car ID
        carId = getIntent().getIntExtra("car_id", -1);
        if (carId == -1) {
            Toast.makeText(this, "Car not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        carName = findViewById(R.id.textCarName);
        carDescription = findViewById(R.id.textCarDescription);
        carMake = findViewById(R.id.textCarMake);
        carModel = findViewById(R.id.textCarModel);
        carEngine = findViewById(R.id.textCarEngine);
        carGas = findViewById(R.id.textCarGas);
        carDistance = findViewById(R.id.textCarDistance);
        carVolume = findViewById(R.id.textCarVolume);
        carConsumption = findViewById(R.id.textCarConsumption);

        addTripButton = findViewById(R.id.add_trip_button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            carName.setText(extras.getString("name"));
            carDescription.setText(extras.getString("description"));
            carMake.setText(extras.getString("make"));
            carModel.setText(extras.getString("model"));
            carEngine.setText(String.valueOf(extras.getInt("engine")) + "cc");
            carGas.setText(extras.getString("gasType"));
            carDistance.setText(extras.getString("distanceUnit"));
            carVolume.setText(extras.getString("volumeUnit"));
            carConsumption.setText(extras.getString("consumptionUnit"));

            // Fetch car data
            dbHelper = new CarDatabaseHelper(this);
            Car car = null;
            for (Car c : dbHelper.getAllCars()) {
                if (c.id == carId) {
                    car = c;
                    break;
                }
            }

            if (extras != null) {
                Log.d("CarDetails", "Received name: " + extras.getString("name"));
            }

            Button deleteButton = findViewById(R.id.buttonDeleteCar);

            deleteButton.setOnClickListener(v -> {
                CarDatabaseHelper dbHelper = new CarDatabaseHelper(this);
                String name = getIntent().getStringExtra("name");

                boolean deleted = dbHelper.deleteCarByName(name); // or use a unique ID if you have one

                if (deleted) {
                    Toast.makeText(this, "Car deleted", Toast.LENGTH_SHORT).show();
                    finish(); // go back to previous screen
                } else {
                    Toast.makeText(this, "Failed to delete car", Toast.LENGTH_SHORT).show();
                }

            });
            // Handle Add Trip
            addTripButton.setOnClickListener(v -> {
                Intent intent = new Intent(CarDetailsActivity.this, AddTripActivity.class);
                intent.putExtra("car_id", carId);
                startActivity(intent);
            });
        }
    }
}


