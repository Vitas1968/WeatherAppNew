package com.example.googlelerning.weather.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.googlelerning.weather.NavigationHost;
import com.example.googlelerning.weather.R;
import com.example.googlelerning.weather.recicler.BuildDataForRecicler;
import com.example.googlelerning.weather.recicler.DataClass;
import com.example.googlelerning.weather.recicler.RecyclerViewAdapter;
import com.example.googlelerning.weather.weather_processing.WeatherLoaderService;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Objects;

public class ShowWeatherFragment extends Fragment {
    private RecyclerView recycView;
    private TextView placeName;
    private ArrayList<DataClass> list = new ArrayList<>();
    private AppCompatActivity mContext;
    private Intent intent;
    private BroadcastReceiver mBroadcastReceiver;
    public final static String PARAM_CITY ="city";
    public final static String BROADCAST_ACTION ="com.example.googlelerning.weather.fragments.GET_WEATHER";


    private void initBroadcastReceiver(){
        mBroadcastReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                String jsonString = intent.getStringExtra("jsonObject");
                try {
                    JSONObject jsonObject= new JSONObject(Objects.requireNonNull(jsonString));
                    launch(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        IntentFilter intFiltr = new IntentFilter(BROADCAST_ACTION);
        Objects.requireNonNull(getActivity()).registerReceiver(mBroadcastReceiver, intFiltr);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Objects.requireNonNull(getActivity()).unregisterReceiver(mBroadcastReceiver);
        getActivity().stopService(intent);
    }

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
        initBroadcastReceiver();
    }

    public void updateWeatherData(final String city,AppCompatActivity activity) {
        mContext=activity;
        intent=new Intent(activity, WeatherLoaderService.class);
        intent.putExtra(ShowWeatherFragment.PARAM_CITY,city);
        Objects.requireNonNull(activity).startService(intent);
    }


    private void launch(JSONObject jsonObject){
        if(jsonObject!=null) {
            try {
                list = new BuildDataForRecicler(jsonObject, mContext).createListData();
                setPlaceName(list);
                initRecyclerView();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else navigateErrorFragment();
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
