package com.example.androidappproject;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTripActivity extends AppCompatActivity {

    private EditText inputDistance, inputVolume, inputCost, inputDate;
    private int carId;
    private final String COST_UNIT = " €";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        inputDistance = findViewById(R.id.input_distance);
        inputVolume = findViewById(R.id.input_volume);
        inputCost = findViewById(R.id.input_cost);
        inputDate = findViewById(R.id.input_date);

        // Get car ID from intent
        carId = getIntent().getIntExtra("car_id", -1);
        if (carId == -1) {
            Toast.makeText(this, "Error: no car selected", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Prefill current date
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        inputDate.setText(today);

        // Date picker dialog on click
        inputDate.setFocusable(false);
        inputDate.setClickable(true);
        inputDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(this, (view, y, m, d) -> {
                String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", y, m + 1, d);
                inputDate.setText(selectedDate);
            }, year, month, day);
            dialog.show();
        });

        String DIST_UNIT = getIntent().getStringExtra("distanceUnit");
        String VOL_UNIT = getIntent().getStringExtra("volumeUnit");


        // Add unit text watchers
        addUnitTextWatcher(inputDistance, DIST_UNIT);
        addUnitTextWatcher(inputVolume, VOL_UNIT);
        addUnitTextWatcher(inputCost, COST_UNIT);

        findViewById(R.id.button_save_trip).setOnClickListener(v -> {
            try {
                // Remove unit suffixes before parsing
                String distText = inputDistance.getText().toString().replace(DIST_UNIT, "").trim();
                String volText = inputVolume.getText().toString().replace(VOL_UNIT, "").trim();
                String costText = inputCost.getText().toString().replace(COST_UNIT, "").trim();

                double distance = Double.parseDouble(distText);
                double volume = Double.parseDouble(volText);
                double cost = costText.isEmpty() ? 0.0 : Double.parseDouble(costText);
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

    private void addUnitTextWatcher(EditText editText, String unit) {
        editText.addTextChangedListener(new TextWatcher() {
            private boolean isEditing = false;

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isEditing) return;

                isEditing = true;
                String input = s.toString().replace(unit, "").trim();

                if (!input.isEmpty() && !s.toString().endsWith(unit)) {
                    editText.setText(input + unit);
                    editText.setSelection(input.length()); // Cursor before unit
                }

                isEditing = false;
            }
        });
    }
}
