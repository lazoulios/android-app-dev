package com.example.androidappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navView;
    private LinearLayout navHome, navAddCar, navCompare;

    private TextView emptyMessage;
    private RecyclerView recyclerViewCars;
    private CarDatabaseHelper dbHelper;
    private CarAdapter carAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Find Views
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.nav_view);
        emptyMessage = findViewById(R.id.empty_message);
        recyclerViewCars = findViewById(R.id.recyclerViewCars);
        ExtendedFloatingActionButton addCarButton = findViewById(R.id.add_car_button);

        // ✅ ΟΡΙΣΜΟΣ ΤΙΤΛΟΥ ΠΡΙΝ ΤΗΝ setSupportActionBar
        toolbar.setTitle("Car App");
        setSupportActionBar(toolbar);

        // Setup Drawer Toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // DB & RecyclerView setup
        dbHelper = new CarDatabaseHelper(this);
        recyclerViewCars.setLayoutManager(new LinearLayoutManager(this));
        loadCars();

        // Navigation drawer items
        navHome = navView.findViewById(R.id.nav_home);
        navAddCar = navView.findViewById(R.id.nav_add_car);
        navCompare = navView.findViewById(R.id.nav_compare);

        navHome.setOnClickListener(v -> {
            drawerLayout.closeDrawers();
            Toast.makeText(MainActivity.this, "Home selected", Toast.LENGTH_SHORT).show();
        });

        navAddCar.setOnClickListener(v -> {
            drawerLayout.closeDrawers();
            Toast.makeText(MainActivity.this, "Add Car selected", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, AddCarActivity.class));
        });

        navCompare.setOnClickListener(v -> {
            drawerLayout.closeDrawers();
            Toast.makeText(MainActivity.this, "Compare selected", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, ComparisonsActivity.class));
        });

        addCarButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCars(); // Reload cars when returning to activity
    }

    private void loadCars() {
        List<Car> carList = dbHelper.getAllCars();

        if (carList.isEmpty()) {
            emptyMessage.setVisibility(View.VISIBLE);
            recyclerViewCars.setVisibility(View.GONE);
        } else {
            emptyMessage.setVisibility(View.GONE);
            recyclerViewCars.setVisibility(View.VISIBLE);
            carAdapter = new CarAdapter(carList);
            recyclerViewCars.setAdapter(carAdapter);
        }
    }
}
