package com.ahaya.earthquakeviewer.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahaya.earthquakeviewer.databinding.ListItemEarthquakeBinding;
import com.ahaya.earthquakeviewer.entity.Earthquake;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class EarthquakeRecyclerViewAdapter extends RecyclerView.Adapter<EarthquakeRecyclerViewAdapter.ViewHolder> {
    private final List<Earthquake> earthquakeList;

    private static final SimpleDateFormat TIME_FORMATE = new SimpleDateFormat("HH:mm", Locale.CHINA);
    private static final NumberFormat MAGNITUDE_FORMATE = new DecimalFormat("0.0");

    public EarthquakeRecyclerViewAdapter(List<Earthquake> earthquakeList) {
        this.earthquakeList = earthquakeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_earthquake, parent, false);
//        return new ViewHolder(view);
        ListItemEarthquakeBinding binding = ListItemEarthquakeBinding.inflate(
                LayoutInflater.from(parent.getContext()),parent,false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Earthquake earthquake = earthquakeList.get(position);
//        holder.date.setText(TIME_FORMATE.format(earthquake.getDate()));
//        holder.details.setText(earthquake.getDetails());
//        holder.magnitude.setText(MAGNITUDE_FORMATE.format(earthquake.getMagnitude()));
        holder.binding.setEarthquake(earthquake);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return earthquakeList == null ? 0 : earthquakeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

//        public final TextView date;
//        public final TextView details;
//        public final TextView magnitude;
        public final ListItemEarthquakeBinding binding;



        public ViewHolder(@NonNull ListItemEarthquakeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setTimeformate(TIME_FORMATE);
            binding.setMagnitudeformate(MAGNITUDE_FORMATE);
//            date = itemView.findViewById(R.id.date);
//            details = itemView.findViewById(R.id.details);
//            magnitude = itemView.findViewById(R.id.magnitude);
        }


    }


}
