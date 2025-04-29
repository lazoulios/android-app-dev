package com.example.androidappproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> carList;

    public CarAdapter(List<Car> carList) {
        this.carList = carList;
    }

    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.textViewName.setText(car.name);
        holder.textViewDetails.setText(car.make + " " + car.model + " (" + car.engineDisplacement + "cc)");
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, CarDetailsActivity.class);
            intent.putExtra("name", car.name);
            intent.putExtra("description", car.description);
            intent.putExtra("make", car.make);
            intent.putExtra("model", car.model);
            intent.putExtra("engine", car.engineDisplacement);
            intent.putExtra("gasType", car.gasType);
            intent.putExtra("distanceUnit", car.distanceUnit);
            intent.putExtra("volumeUnit", car.volumeUnit);
            intent.putExtra("consumptionUnit", car.consumptionUnit);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewDetails;

        CarViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.car_name);
            textViewDetails = itemView.findViewById(R.id.car_details);
        }
    }
}
