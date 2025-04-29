package com.example.androidappproject;

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
