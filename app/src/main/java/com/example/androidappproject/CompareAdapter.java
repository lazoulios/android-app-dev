package com.example.androidappproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CompareAdapter extends RecyclerView.Adapter<CompareAdapter.CompareViewHolder> {

    private List<Car> cars;
    private CarDatabaseHelper dbHelper;

    public CompareAdapter(List<Car> cars, CarDatabaseHelper dbHelper) {
        this.cars = cars;
        this.dbHelper = dbHelper;
    }

    @Override
    public CompareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compare, parent, false);
        return new CompareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CompareViewHolder holder, int position) {
        Car car = cars.get(position);

        double userAvg = dbHelper.getAverageConsumptionForCar(car.id);  // users avg in l/100km
        double referenceAvg = getExpectedAverage(car.engineDisplacement); // l/100km

        String unit = car.consumptionUnit;

        if (unit.equals("mpg")) {
            referenceAvg = UnitConverter.l100kmToMpg(referenceAvg);
        }

        double diff = referenceAvg != 0 ? ((userAvg - referenceAvg) / referenceAvg) * 100 : 0;

        holder.carName.setText(car.name);
        holder.userAvg.setText(String.format("Your Avg: %.2f %s", userAvg, car.consumptionUnit));
        holder.expectedAvg.setText(String.format("Avarage for cars with similar cc.: %.2f %s", referenceAvg, car.consumptionUnit));
        holder.percentageDiff.setText(
                String.format("Percentage difference: %.1f%% %s", Math.abs(diff), diff >= 0 ? "above average" : "below average")
        );
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class CompareViewHolder extends RecyclerView.ViewHolder {
        TextView carName, userAvg, expectedAvg, percentageDiff;

        public CompareViewHolder(View itemView) {
            super(itemView);
            carName = itemView.findViewById(R.id.compare_car_name);
            userAvg = itemView.findViewById(R.id.compare_user_avg);
            expectedAvg = itemView.findViewById(R.id.compare_expected_avg);
            percentageDiff = itemView.findViewById(R.id.compare_diff);
        }
    }

    private double getExpectedAverage(int cc) {
        if (cc <= 1200) return 5.5;
        else if (cc <= 1400) return 6.5;
        else if (cc <= 1600) return 7.0;
        else if (cc <= 1800) return 7.5;
        else if (cc <= 2000) return 8.2;
        else if (cc <= 2500) return 9.5;
        else return 12;
    }

    public static class UnitConverter {
        // Convert mpg to L/100km
        public static double mpgToL100km(double mpg) {
            return  mpg / 235.215 ;
        }

        // Convert L/100km to mpg
        public static double l100kmToMpg(double l100km) {
            return 235.215 / l100km;
        }
    }

}

