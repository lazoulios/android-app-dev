package com.example.androidappproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ComparisonsActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private LinearLayout navHome, navAddCar, navCompare;
    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private CarDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comparisons_screen);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerViewCars);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Setup navigation menu
        navHome = findViewById(R.id.nav_home);
        navAddCar = findViewById(R.id.nav_add_car);
        navCompare = findViewById(R.id.nav_compare);

        navHome.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            drawerLayout.closeDrawers();
        });

        navAddCar.setOnClickListener(v -> {
            startActivity(new Intent(this, AddCarActivity.class));
            drawerLayout.closeDrawers();
        });

        navCompare.setOnClickListener(v -> {
            Toast.makeText(this, "Already on Comparisons screen", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawers();
        });

        // Initialize database and load cars
        dbHelper = new CarDatabaseHelper(this);
        List<Car> carList = dbHelper.getAllCars();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        carAdapter = new CarAdapter(carList);
        recyclerView.setAdapter(carAdapter);
    }
}
