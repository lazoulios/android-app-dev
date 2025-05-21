package com.example.androidappproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class ViewTripsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TripAdapter adapter;
    private CarDatabaseHelper dbHelper;
    private TextView emptyMessage;
    private int carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_trips);

        MaterialToolbar toolbar = findViewById(R.id.trips_toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerViewTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        emptyMessage = findViewById(R.id.empty_trips_message);

        carId = getIntent().getIntExtra("car_id", -1);
        if (carId == -1) {
            Toast.makeText(this, "Car ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        dbHelper = new CarDatabaseHelper(this);
        List<Trip> trips = dbHelper.getTripsForCar(carId);
        String distanceUnit = getIntent().getStringExtra("distance_unit");
        String volumeUnit = getIntent().getStringExtra("volume_unit");
        String consumptionUnit = getIntent().getStringExtra("consumption_unit");
        adapter = new TripAdapter(trips, distanceUnit, volumeUnit, consumptionUnit);
        recyclerView.setAdapter(adapter);

        if (trips.isEmpty()) {
            emptyMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyMessage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new TripAdapter(trips, distanceUnit, volumeUnit, consumptionUnit);
            recyclerView.setAdapter(adapter);
        }
    }
}
