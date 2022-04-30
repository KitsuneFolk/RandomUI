package com.example.randomui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.randomui.ui.headsandtails.HeadsAndTailsFragment;
import com.example.randomui.ui.MySettings;
import com.example.randomui.ui.random.RandomFragment;
import com.example.randomui.ui.random_a_lot_of.RandomALotOfFragment;
import com.example.randomui.ui.settings.SettingsActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = "MyLogs";

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "MainActivity.onCreate");
        MySettings mySettings = new MySettings(this);
        mySettings.load();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_random, R.id.nav_headsandtails, R.id.nav_randomALotOf,
                R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //not used
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //Нужно для работы переключения itemОВ при их выборе и чтобы работал share.
        FragmentTransaction ft;
        switch (menuItem.getItemId()) {
            case R.id.nav_random:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                        new RandomFragment()).commit();
                break;
            case R.id.nav_headsandtails:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                        new HeadsAndTailsFragment()).commit();
                break;
            case R.id.nav_randomALotOf:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                        new RandomALotOfFragment()).commit();
                break;
            case R.id.nav_settings:
                Log.w("MyLogs", "MainActivity.onNavigationItemSelected: nav_settings is selected");
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_share:
                Log.w("MyLogs", "MainActivity.onNavigationItemSelected: nav_share is selected");
                break;

        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
