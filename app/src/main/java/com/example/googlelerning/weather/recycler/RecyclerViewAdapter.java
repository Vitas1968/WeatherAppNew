package com.example.googlelerning.weather.recycler;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.googlelerning.weather.R;
import java.util.ArrayList;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<DataClass> data = new ArrayList<>();
    Fragment mFragment;


    public RecyclerViewAdapter(ArrayList<DataClass> list, Fragment fragment) {
        mFragment=fragment;
        if(data != null) {
            this.data = list;
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.card_view_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.updatedField.setText(data.get(position).getUpdatedField());
        holder.weatherIcon.setText(data.get(position).getWeatherIcon());
        holder.currentTemperatureField.setText(data.get(position).getCurrentTemperatureField());
        holder.detailsField.setText(data.get(position).getDetailsField());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView updatedField;
        TextView weatherIcon;
        TextView currentTemperatureField;
        TextView detailsField;
        private Typeface weatherFont;



        ViewHolder(View view) {
            super(view);
            updatedField = view.findViewById(R.id.updated_field);
            weatherIcon = view.findViewById(R.id.weather_icon);
            currentTemperatureField = view.findViewById(R.id.current_temperature_field);
            detailsField = view.findViewById(R.id.details_field);
            initFonts();
        }
        private void initFonts() {
            weatherFont = Typeface.createFromAsset(Objects.requireNonNull(mFragment.getContext()).getAssets(), "fonts/weather.ttf");
            weatherIcon.setTypeface(weatherFont);
        }
    }
}
