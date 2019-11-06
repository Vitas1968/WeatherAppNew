package com.example.googlelerning.weather;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.googlelerning.weather.fragments.InfoAppDrawableFragment;
import com.example.googlelerning.weather.fragments.InfoAutorDrawableFragment;
import com.example.googlelerning.weather.fragments.InfoServerDrawableFragment;
import com.example.googlelerning.weather.fragments.LoginFragment;
import com.example.googlelerning.weather.fragments.ShowWeatherFragment;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationHost {

    public static final String LOG_TAG="MainActivity";
    private String[] mItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private Toolbar mToolbar;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private int updateHour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG,"Перед setContentView");
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG,"После setContentView");
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_fragment, new LoginFragment())
                    .commit();
            Log.d(LOG_TAG,"После коммита");
        }
        mTitle = getTitle();
        mItemTitles = getResources().getStringArray(R.array.drawer_items);
        mDrawerLayout =findViewById(R.id.drawer_layout);
        mDrawerListView =findViewById(R.id.left_drawer);
        setupToolbar();
        Log.d(LOG_TAG,"После setupToolbar");
        ItemModel[] dItems = fillDataModel();
        DrawerAdapter adapter = new DrawerAdapter(this, R.layout.item_row, dItems);
        mDrawerListView.setAdapter(adapter);
        mDrawerListView.setOnItemClickListener(new ItemClickListener());
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();
        Log.d(LOG_TAG,"В конце онкриэйт");
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
            Bundle bundle=new Bundle();
            bundle.putInt("updateHour",updateHour);
             fragment.setArguments(bundle);
             ((ShowWeatherFragment) fragment).updateWeatherData(city);
        }
    }

    void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private ItemModel[] fillDataModel() {
        return new ItemModel[]{
                new ItemModel(R.drawable.ic_mood_black_24dp, mItemTitles[0]),
                new ItemModel(R.drawable.sharp_info_black_18dp, mItemTitles[1]),
                new ItemModel(R.drawable.ic_settings_system_daydream_black_24dp, mItemTitles[2])
        };
    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
    }



    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        Objects.requireNonNull(getSupportActionBar()).setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        handleMenuItemClick(item);
        return super.onOptionsItemSelected(item);
    }

    private void handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.weather_on_0_hour: {
                updateHour=0;
                break;
            }
            case R.id.weather_on_3_hour: {
                updateHour=3;
                break;
            }
            case R.id.weather_on_6_hour: {
                updateHour=6;
                break;
            }
            case R.id.weather_on_9_hour: {
                updateHour=9;
                break;
            }
            case R.id.weather_on_12_hour: {
                updateHour=12;
                break;
            }
            case R.id.weather_on_15_hour: {
                updateHour=15;
                break;
            }
            case R.id.weather_on_18_hour: {
                updateHour=18;
                break;
            }
            case R.id.weather_on_21_hour: {
                updateHour=21;
                break;
            }
            default: {
                updateHour=24;
            }
        }
    }

    private class ItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = new InfoAutorDrawableFragment();
                    break;
                case 1:
                    fragment = new InfoAppDrawableFragment();
                    break;
                case 2:
                    fragment = new InfoServerDrawableFragment();
                    break;

                default:
                    break;
            }
            if (fragment != null) {
                navigateTo(fragment,false,null);
            }
        }
    }


}
