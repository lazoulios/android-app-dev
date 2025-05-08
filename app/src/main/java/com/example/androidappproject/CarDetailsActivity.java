package com.example.androidappproject;

import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class CarDetailsActivity extends AppCompatActivity {

    private TextView carName, carDescription, carMake, carModel, carEngine, carGas, carDistance, carVolume, carConsumption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        MaterialToolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        carName = findViewById(R.id.textCarName);
        carDescription = findViewById(R.id.textCarDescription);
        carMake = findViewById(R.id.textCarMake);
        carModel = findViewById(R.id.textCarModel);
        carEngine = findViewById(R.id.textCarEngine);
        carGas = findViewById(R.id.textCarGas);
        carDistance = findViewById(R.id.textCarDistance);
        carVolume = findViewById(R.id.textCarVolume);
        carConsumption = findViewById(R.id.textCarConsumption);

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
        }

        if (extras != null) {
            Log.d("CarDetails", "Received name: " + extras.getString("name"));
        }

    }
}


