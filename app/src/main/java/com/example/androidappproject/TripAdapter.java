package com.example.androidappproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private List<Trip> trips;
    private String distanceUnit;
    private String volumeUnit;
    private String consumptionUnit;


    public TripAdapter(List<Trip> trips, String distanceUnit, String volumeUnit, String consumptionUnit) {
        this.trips = trips;
        this.distanceUnit = distanceUnit;
        this.volumeUnit = volumeUnit;
        this.consumptionUnit = consumptionUnit;
    }


    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = trips.get(position);
        holder.date.setText("Date: " + trip.date);
        holder.distance.setText("Distance: " + trip.distance + " " + distanceUnit);
        holder.volume.setText("Fuel: " + trip.volume + " " + volumeUnit);
        holder.cost.setText("Cost: " + trip.cost + " €");

        holder.cost.setText("Cost: " + trip.cost + " €");

        holder.deleteButton.setOnClickListener(v -> {
            Context context = v.getContext();
            CarDatabaseHelper dbHelper = new CarDatabaseHelper(context);

            boolean deleted = dbHelper.deleteTrip(trip);
            if (deleted) {
                trips.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, trips.size());
                Toast.makeText(context, "Trip deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to delete trip", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView date, distance, volume, cost;
        ImageButton deleteButton;

        public TripViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tripDate);
            distance = itemView.findViewById(R.id.tripDistance);
            volume = itemView.findViewById(R.id.tripVolume);
            cost = itemView.findViewById(R.id.tripCost);
            deleteButton = itemView.findViewById(R.id.delete_trip_button); // Make sure this ID exists in item_trip.xml
        }
    }
}
