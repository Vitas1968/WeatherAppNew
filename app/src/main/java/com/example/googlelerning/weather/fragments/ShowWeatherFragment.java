package com.example.googlelerning.weather.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.googlelerning.weather.NavigationHost;
import com.example.googlelerning.weather.R;
import com.example.googlelerning.weather.database.ConvertDataFromDB;
import com.example.googlelerning.weather.database.DatabaseHelper;
import com.example.googlelerning.weather.recycler.BuildDataForRecicler;
import com.example.googlelerning.weather.recycler.DataClass;
import com.example.googlelerning.weather.recycler.RecyclerViewAdapter;
import com.example.googlelerning.weather.rest.OpenWeatherRepo;
import com.example.googlelerning.weather.rest.entities.WeatherRequestRestModel;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowWeatherFragment extends Fragment {
    private boolean flagSwitch;
    private RecyclerView recycView;
    private TextView placeName;
    private ArrayList<DataClass> list;
    private Context mContext;
    private static final String OPEN_WEATHER_API_KEY = "6ca9777de46f536071253935840cba46";
    private static final String JSON_MODE = "json";
    private static final String UNITS = "metric";
    private SQLiteDatabase database;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.show_weather_fragment, container, false);
        view.setId(R.id.show_weather_fr);
        view.setTag("showWeatherFragment");

        recycView=view.findViewById(R.id.recycler_view);
        placeName=view.findViewById(R.id.place_name);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        initDB();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    public void updateWeatherData(final String city) {
        //если переключатель выключен flagSwitch=false читаем онлайн
        getFlagSwitch();
        if(!flagSwitch) {
            String debug="debug";
            OpenWeatherRepo.getSingleton().getAPI().loadWeather(city, OPEN_WEATHER_API_KEY, JSON_MODE, UNITS).enqueue(new Callback<WeatherRequestRestModel>() {
                @Override
                public void onResponse(@NonNull Call<WeatherRequestRestModel> call, @NonNull Response<WeatherRequestRestModel> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        launch(response.body());
                    } else navigateErrorFragment();
                }

                @Override
                public void onFailure(Call<WeatherRequestRestModel> call, Throwable t) {
                    Toast.makeText(getContext(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            if(city!=null) {
                launchForDB(city);
            } else navigateErrorFragment();
             }


    }
    private void getFlagSwitch(){
        Bundle args=getArguments();
        flagSwitch= Objects.requireNonNull(args).getBoolean("flagSwitch",false);
    }
    private void launchForDB(String city){

        list= new ConvertDataFromDB(database, mContext).createListDataFromDB(city);
        setPlaceName(list.get(0).getCityField());
        initRecyclerView();
    }

    private void launch(WeatherRequestRestModel weatherRequestRestModel){

            list= new BuildDataForRecicler(weatherRequestRestModel,mContext,database).createListData();
            setPlaceName(weatherRequestRestModel);
            initRecyclerView();
    }

    private void initDB() {
        database = new DatabaseHelper(Objects.requireNonNull(getActivity()).getApplicationContext()).getWritableDatabase();
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
    private void setPlaceName(WeatherRequestRestModel weatherRequestRestModel){
        String cityName= weatherRequestRestModel.mCityInfoModel.getName()+
                " , "+weatherRequestRestModel.mCityInfoModel.getCountry();
        placeName.setText(cityName);
    }
    private void setPlaceName(String cityName){
        placeName.setText(cityName);
    }

    private void navigateErrorFragment(){
        ((NavigationHost) Objects.requireNonNull(getActivity())).navigateTo(new ErrorCityFragment(), true, null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        database.close();
    }
}
