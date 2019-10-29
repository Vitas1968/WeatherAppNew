package com.example.googlelerning.weather;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.example.googlelerning.weather.fragments.LoginFragment;
import com.example.googlelerning.weather.fragments.ShowWeatherFragment;

public class MainActivity extends AppCompatActivity implements NavigationHost {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_fragment, new LoginFragment())
                    .commit();
        }
    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack, String city) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_fragment, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();

        if (fragment instanceof ShowWeatherFragment){
            ((ShowWeatherFragment) fragment).updateWeatherData(city);
        }
    }
}
