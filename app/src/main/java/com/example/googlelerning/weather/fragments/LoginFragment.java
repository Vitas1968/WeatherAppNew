package com.example.googlelerning.weather.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.googlelerning.weather.NavigationHost;
import com.example.googlelerning.weather.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static com.example.googlelerning.weather.R.id.imageView;
import static com.example.googlelerning.weather.R.id.login_fr;

public class LoginFragment extends Fragment {
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        view.setId(login_fr);
        view.setTag("loginFragment");
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_text);
        MaterialButton nextButton = view.findViewById(R.id.next_button);
        setNextButtonListener(passwordTextInput, passwordEditText, nextButton);
        setPasswordFieldListener(passwordTextInput, passwordEditText);

        loadImageWithPicasso(view);

        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
    }

    private void setNextButtonListener(final TextInputLayout passwordTextInput, final TextInputEditText passwordEditText, MaterialButton nextButton) {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(getString(R.string.error_password));
                } else {
                    passwordTextInput.setError(null); // Clear the error
                    ((NavigationHost) Objects.requireNonNull(getActivity())).navigateTo(new ChoiceCityFragment(), true, null);
                }
            }
        });
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

    private void loadImageWithPicasso(View view) {

        ImageView imageView=view.findViewById(R.id.imageView);
        //Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(imageView);
        Picasso.get().load("https://gdefon.org/_ph/4/668342637.jpg").into(imageView);
    }
}
