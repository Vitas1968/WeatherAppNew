package com.example.googlelerning.weather.fragments;

import android.content.Context;
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
    private ArrayList<DataClass> list = new ArrayList<>();
    private Context mContext;
    private int updateHour;

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
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle=getArguments();
        if (bundle != null) {
            updateHour=bundle.getInt("updateHour",24);
        } else updateHour=24;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
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
            list= new BuildDataForRecicler(jsonObject,mContext,updateHour).createListData();
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
