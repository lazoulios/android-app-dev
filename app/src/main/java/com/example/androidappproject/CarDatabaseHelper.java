package com.example.androidappproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CarDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cars.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_CARS = "cars";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_MAKE = "make";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_ENGINE = "engine";
    public static final String COLUMN_GAS_TYPE = "gas_type";
    public static final String COLUMN_DISTANCE_UNIT = "distance_unit";
    public static final String COLUMN_VOLUME_UNIT = "volume_unit";
    public static final String COLUMN_CONSUMPTION_UNIT = "consumption_unit";

    private static final String TABLE_TRIPS = "trips";

    private static final String COLUMN_TRIP_ID = "_id";
    private static final String COLUMN_CAR_ID = "car_id"; // FK to cars._id
    private static final String COLUMN_DATE = "date"; // (optional)
    private static final String COLUMN_DISTANCE = "distance"; // e.g. km
    private static final String COLUMN_VOLUME = "volume";     // e.g. liters
    private static final String COLUMN_COST = "cost";         // (optional)

    private static final String CREATE_TRIPS_TABLE =
            "CREATE TABLE " + TABLE_TRIPS + " (" +
                    COLUMN_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CAR_ID + " INTEGER, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_DISTANCE + " REAL, " +
                    COLUMN_VOLUME + " REAL, " +
                    COLUMN_COST + " REAL, " +
                    "FOREIGN KEY(" + COLUMN_CAR_ID + ") REFERENCES " + TABLE_CARS + "(" + COLUMN_ID + ")" +
                    ");";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CARS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_MAKE + " TEXT, " +
                    COLUMN_MODEL + " TEXT, " +
                    COLUMN_ENGINE + " INTEGER, " +
                    COLUMN_GAS_TYPE + " TEXT, " +
                    COLUMN_DISTANCE_UNIT + " TEXT, " +
                    COLUMN_VOLUME_UNIT + " TEXT, " +
                    COLUMN_CONSUMPTION_UNIT + " TEXT" +
                    ");";

    public CarDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(CREATE_TRIPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL(CREATE_TRIPS_TABLE);  // add new table without touching car data
        }
    }

    public long insertCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, car.name);
        values.put(COLUMN_DESCRIPTION, car.description);
        values.put(COLUMN_MAKE, car.make);
        values.put(COLUMN_MODEL, car.model);
        values.put(COLUMN_ENGINE, car.engineDisplacement);
        values.put(COLUMN_GAS_TYPE, car.gasType);
        values.put(COLUMN_DISTANCE_UNIT, car.distanceUnit);
        values.put(COLUMN_VOLUME_UNIT, car.volumeUnit);
        values.put(COLUMN_CONSUMPTION_UNIT, car.consumptionUnit);

        long id = db.insert(TABLE_CARS, null, values);
        db.close();
        return id;
    }


    public void insertTrip(Trip trip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CAR_ID, trip.carId);
        values.put(COLUMN_DATE, trip.date);
        values.put(COLUMN_DISTANCE, trip.distance);
        values.put(COLUMN_VOLUME, trip.volume);
        values.put(COLUMN_COST, trip.cost);
        db.insert(TABLE_TRIPS, null, values);
        db.close();
    }



    public List<Car> getAllCars() {
        List<Car> carList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String make = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAKE));
                String model = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODEL));
                int engine = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ENGINE));
                String gasType = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GAS_TYPE));
                String distanceUnit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DISTANCE_UNIT));
                String volumeUnit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VOLUME_UNIT));
                String consumptionUnit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONSUMPTION_UNIT));

                Car car = new Car(id, name, description, make, model, engine, gasType, distanceUnit, volumeUnit, consumptionUnit);
                carList.add(car);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return carList;
    }

    public boolean deleteCarById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete trips first
        db.delete(TABLE_TRIPS, COLUMN_CAR_ID + " = ?", new String[]{String.valueOf(id)});

        // Then delete the car
        int rows = db.delete(TABLE_CARS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }


    public boolean updateCar(int carId, ContentValues values) {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("DB", "Trying to update car ID = " + carId);
        int rows = db.update("cars", values, "_id = ?", new String[]{String.valueOf(carId)});
        Log.d("DB", "Update affected rows = " + rows);
        db.close();
        return rows > 0;
    }

    public List<Trip> getTripsForCar(int carId) {
        List<Trip> trips = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("trips", null, "car_id=?", new String[]{String.valueOf(carId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                double distance = cursor.getDouble(cursor.getColumnIndexOrThrow("distance"));
                double volume = cursor.getDouble(cursor.getColumnIndexOrThrow("volume"));
                double cost = cursor.getDouble(cursor.getColumnIndexOrThrow("cost"));

                Trip trip = new Trip(carId, date, distance, volume, cost);
                trips.add(trip);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return trips;
    }

    public boolean deleteTrip(Trip trip) {
        SQLiteDatabase db = this.getWritableDatabase();

        int rows = db.delete("trips",
                "car_id = ? AND date = ? AND distance = ? AND volume = ?",
                new String[] {
                        String.valueOf(trip.carId),
                        trip.date,
                        String.valueOf(trip.distance),
                        String.valueOf(trip.volume)
                });

        db.close();
        return rows > 0;
    }






}
