package com.example.androidappproject;

public class Car {
    public String name;
    public String description;
    public String make;
    public String model;
    public int engineDisplacement;
    public String gasType;
    public String distanceUnit;
    public String volumeUnit;
    public String consumptionUnit;

    public Car(String name, String description, String make, String model,
               int engineDisplacement, String gasType, String distanceUnit,
               String volumeUnit, String consumptionUnit) {
        this.name = name;
        this.description = description;
        this.make = make;
        this.model = model;
        this.engineDisplacement = engineDisplacement;
        this.gasType = gasType;
        this.distanceUnit = distanceUnit;
        this.volumeUnit = volumeUnit;
        this.consumptionUnit = consumptionUnit;
    }

    // Getters if needed
}
