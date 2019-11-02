package com.example.googlelerning.weather.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.googlelerning.weather.NameFildsSettings;
import com.example.googlelerning.weather.NavigationHost;
import com.example.googlelerning.weather.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class ChoiceCityFragment extends Fragment implements NameFildsSettings {
    private TextInputEditText inputCity;
    private String city;
    private MaterialButton viewWatherBtn;
    private SharedPreferences.Editor prefEditor;
    private SharedPreferences settings;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.choice_city_fragment, container, false);
        view.setId(R.id.choice_city_fr);
        view.setTag("choiceCityFragment");
        ininViews(view);
        setListenerEditText();
        initSharedPreferences();
        setClickListenerButton();
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRetainInstance(true);
    }
    @SuppressLint("CommitPrefEdits")
    private void initSharedPreferences(){
        settings= Objects.requireNonNull(getActivity()).getSharedPreferences(SETTINS_FILE_NAME,MODE_PRIVATE);
        prefEditor=settings.edit();
    }

    private void ininViews(View view) {
        inputCity=view.findViewById(R.id.inputCity);
        viewWatherBtn=view.findViewById(R.id.viewWatherBtn);
    }

    @Override
    public void onResume() {
        super.onResume();
        String city;
        if(settings.contains(CITY_NAME_SETTING)) {
            city = settings.getString(CITY_NAME_SETTING, "Undefined");
            inputCity.setText(city);
        }
    }

    private void setClickListenerButton() {
        viewWatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city!=null) {
                    prefEditor.putString(CITY_NAME_SETTING, city);
                    prefEditor.apply();
                    ((NavigationHost) Objects.requireNonNull(getActivity())).navigateTo(new ShowWeatherFragment(), true,city);
                } else {
                    ((NavigationHost) Objects.requireNonNull(getActivity())).navigateTo(new ErrorCityFragment(), true,null);
                }
            }
        });
    }

    private void setListenerEditText() {
        inputCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                city = s.toString();

            }
        });
    }
}
