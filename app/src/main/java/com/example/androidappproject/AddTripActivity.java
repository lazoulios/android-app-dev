package com.example.androidappproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTripActivity extends AppCompatActivity {

    private EditText inputDistance, inputVolume, inputCost, inputDate;
    private Button saveButton;
    private int carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        inputDistance = findViewById(R.id.input_distance);
        inputVolume = findViewById(R.id.input_volume);
        inputCost = findViewById(R.id.input_cost);
        inputDate = findViewById(R.id.input_date);
        saveButton = findViewById(R.id.button_save_trip);

        // Prefill current date
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        inputDate.setText(today);

        // Get car ID from intent
        carId = getIntent().getIntExtra("car_id", -1);
        if (carId == -1) {
            Toast.makeText(this, "Error: no car selected", Toast.LENGTH_SHORT).show();
            finish();
        }

        saveButton.setOnClickListener(v -> {
            try {
                double distance = Double.parseDouble(inputDistance.getText().toString());
                double volume = Double.parseDouble(inputVolume.getText().toString());
                double cost = inputCost.getText().toString().isEmpty() ? 0.0 :
                        Double.parseDouble(inputCost.getText().toString());
                String date = inputDate.getText().toString();

                Trip trip = new Trip(carId, date, distance, volume, cost);
                CarDatabaseHelper dbHelper = new CarDatabaseHelper(this);
                dbHelper.insertTrip(trip);

                Toast.makeText(this, "Trip saved!", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
