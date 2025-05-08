package com.example.androidappproject;

public class Trip {
    public int carId;
    public String date;
    public double distance;
    public double volume;
    public double cost;

    public Trip(int carId, String date, double distance, double volume, double cost) {
        this.carId = carId;
        this.date = date;
        this.distance = distance;
        this.volume = volume;
        this.cost = cost;
    }
}
