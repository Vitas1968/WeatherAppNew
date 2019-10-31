package com.example.googlelerning.weather.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.googlelerning.weather.NavigationHost;
import com.example.googlelerning.weather.R;
import com.example.googlelerning.weather.Sensors;
import com.example.googlelerning.weather.recicler.BuildDataForRecicler;
import com.example.googlelerning.weather.recicler.DataClass;
import com.example.googlelerning.weather.recicler.RecyclerViewAdapter;
import com.example.googlelerning.weather.weather_processing.WeatherDataLoader;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Objects;

public class ShowWeatherFragment extends Fragment {
    private final Handler handler = new Handler();
    private RecyclerView recycView;
    private TextView placeName;
    private TextView tempSensor;
    private TextView humidSensor;
    private ArrayList<DataClass> list = new ArrayList<>();
    private Context mContext;
    private Sensors mSensors;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.show_weather_fragment, container, false);
        view.setId(R.id.show_weather_fr);
        view.setTag("showWeatherFragment");
        recycView=view.findViewById(R.id.recycler_view);
        placeName=view.findViewById(R.id.place_name);
        tempSensor=view.findViewById(R.id.temp_sensor);
        humidSensor=view.findViewById(R.id.humid_Sensor);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        mSensors =new Sensors(getActivity());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }
    // Слушатель датчика температуры
    private SensorEventListener listenerTempSensor = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        @Override
        public void onSensorChanged(SensorEvent event) {
            showTempSensor(event);
        }
    };
    private void showTempSensor(SensorEvent event){
        String currentTemp = "\n" + "Current temperature here: "+ event.values[0];
        tempSensor.setText(currentTemp);

    }

    // Слушатель датчика влажности
    private SensorEventListener listenerHumidSensor = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        @Override
        public void onSensorChanged(SensorEvent event) {
            showHumidSensor(event);
        }
    };

    private void showHumidSensor(SensorEvent event){
        String currentHumid = "\n" + "Current humidity here: "+ event.values[0];
        humidSensor.setText(currentHumid);

    }

    @Override
    public void onResume() {
        super.onResume();
        // Регистрируем слушатель датчика температуры
        Sensor tempSensor=mSensors.getSensors().get(0);
        mSensors.getSensorManager().registerListener(listenerTempSensor, tempSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        // Регистрируем слушатель датчика влажности
        Sensor humidSensor=mSensors.getSensors().get(1);
        mSensors.getSensorManager().registerListener(listenerHumidSensor, humidSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onPause() {
        super.onPause();
        Sensor tempSensor=mSensors.getSensors().get(0);
        mSensors.getSensorManager().unregisterListener(listenerTempSensor, tempSensor);
        Sensor humidSensor=mSensors.getSensors().get(1);
        mSensors.getSensorManager().unregisterListener(listenerHumidSensor, humidSensor);
    }


    public void updateWeatherData(final String city) {
        new Thread() {
            @Override
            public void run() {
                final JSONObject jsonObject = WeatherDataLoader.getJSONData(city);

                if(jsonObject == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            navigateErrorFragment();
                        }
                    });
                } else {

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            launch(jsonObject);
                        }
                    });
                }
            }
        }.start();
    }


    private void launch(JSONObject jsonObject){
        try {
            list= new BuildDataForRecicler(jsonObject,mContext).createListData();
            setPlaceName(list);
            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        DividerItemDecoration itemDecoration;
        itemDecoration= new DividerItemDecoration(Objects.requireNonNull(mContext),LinearLayoutManager.VERTICAL);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mContext, R.drawable.separator)));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(list,this);
        recycView.setLayoutManager(layoutManager);
        recycView.addItemDecoration(itemDecoration);
        recycView.setAdapter(adapter);
    }
    private void setPlaceName(ArrayList<DataClass> list){
        placeName.setText(list.get(0).getCityField());
    }

    private void navigateErrorFragment(){
        ((NavigationHost) Objects.requireNonNull(getActivity())).navigateTo(new ErrorCityFragment(), true, null);
    }

}
