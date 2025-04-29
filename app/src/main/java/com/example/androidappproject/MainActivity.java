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

import com.google.android.material.appbar.MaterialToolbar;
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

        // Handle window insets (safe area for gestures)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find Views
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.nav_view);
        emptyMessage = findViewById(R.id.empty_message);
        recyclerViewCars = findViewById(R.id.recyclerViewCars);

        // Setup Toolbar
        setSupportActionBar(toolbar);

        // Setup Drawer Toggle (hamburger icon)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        dbHelper = new CarDatabaseHelper(this);
        recyclerViewCars.setLayoutManager(new LinearLayoutManager(this));

        loadCars();

        // Find custom drawer items
        navHome = navView.findViewById(R.id.nav_home);
        navAddCar = navView.findViewById(R.id.nav_add_car);
        navCompare = navView.findViewById(R.id.nav_compare);

        // Handle Drawer Item Clicks
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Toast.makeText(MainActivity.this, "Home selected", Toast.LENGTH_SHORT).show();
                // TODO: Go to Home screen
            }
        });

        navAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Toast.makeText(MainActivity.this, "Add Car selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
                startActivity(intent);
            }
        });

        navCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Toast.makeText(MainActivity.this, "Compare selected", Toast.LENGTH_SHORT).show();
                // TODO: Go to Compare screen
            }
        });
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