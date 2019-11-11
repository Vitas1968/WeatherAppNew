package com.example.googlelerning.weather.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.googlelerning.weather.NavigationHost;
import com.example.googlelerning.weather.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;


public class ChoiceCityFragment extends Fragment {
    private TextInputEditText inputCity;
    private String city;
    private MaterialButton viewWatherBtn;
    private LinearLayout lLayout;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.choice_city_fragment, container, false);
        view.setId(R.id.choice_city_fr);
        view.setTag("choiceCityFragment");
        ininViews(view);
        setListenerEditText();
        setClickListenerButton();
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRetainInstance(true);
    }

    private void ininViews(View view) {
        inputCity=view.findViewById(R.id.inputCity);
        viewWatherBtn=view.findViewById(R.id.viewWatherBtn);
    }

    private void setClickListenerButton() {
        viewWatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city!=null) {
                    ((NavigationHost) Objects.requireNonNull(getActivity())).navigateTo(new ShowWeatherFragment(), true,city);
                } else {
                    Snackbar.make(v, "City not input", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnTransmitCity");
        }
    }
}
