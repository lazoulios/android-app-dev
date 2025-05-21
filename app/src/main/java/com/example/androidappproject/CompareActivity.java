package com.example.androidappproject;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.List;

public class CompareActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarDatabaseHelper dbHelper;
    private CompareAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comparisons_screen);

        MaterialToolbar toolbar = findViewById(R.id.compare_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Compare Consumption"); // <-- to fix issue with the stupid title
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerViewCompare);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new CarDatabaseHelper(this);
        List<Car> cars = dbHelper.getAllCars();
        adapter = new CompareAdapter(cars, dbHelper);
        recyclerView.setAdapter(adapter);
    }
}
