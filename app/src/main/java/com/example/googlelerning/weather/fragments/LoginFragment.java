package com.example.googlelerning.weather.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import com.google.android.material.textfield.TextInputLayout;
import java.util.Objects;
import static android.content.Context.MODE_PRIVATE;
import static com.example.googlelerning.weather.R.id.login_fr;

public class LoginFragment extends Fragment implements NameFildsSettings {
    private TextInputEditText loginEditText;
    private String userLogin;
    private SharedPreferences.Editor prefEditor;
    private SharedPreferences settings;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        view.setId(login_fr);
        view.setTag("loginFragment");
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_text);
        MaterialButton nextButton = view.findViewById(R.id.next_button);
        loginEditText=view.findViewById(R.id.login_user_field);
        setNextButtonListener(passwordTextInput, passwordEditText, nextButton);
        setPasswordFieldListener(passwordTextInput, passwordEditText);
        setLoginFieldListener();
        initSharedPreferences();
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

    private void setNextButtonListener(final TextInputLayout passwordTextInput, final TextInputEditText passwordEditText, MaterialButton nextButton) {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(getString(R.string.error_password));
                } else {
                    passwordTextInput.setError(null);
                    prefEditor.putString(USER_LOGIN_SETTING, userLogin);
                    prefEditor.apply();
                    ((NavigationHost) Objects.requireNonNull(getActivity())).navigateTo(new ChoiceCityFragment(), true, null);
                }
            }
        });
    }

    private void setLoginFieldListener(){
        loginEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                userLogin = s.toString();

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        String login;
        if(settings.contains(USER_LOGIN_SETTING)) {
            login = settings.getString(USER_LOGIN_SETTING, "Undefined");
            loginEditText.setText(login);
        }
    }


    private void setPasswordFieldListener(final TextInputLayout passwordTextInput, final TextInputEditText passwordEditText) {
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(null);
                }
                return false;
            }
        });
    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 3;
    }
}
