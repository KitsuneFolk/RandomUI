package com.example.randomui.ui.random_a_lot_of;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.randomui.R;
import com.example.randomui.ui.MySettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RandomALotOf_listView extends AppCompatActivity {
    final String TAG = "MyLogs";

    Toolbar toolbar;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<Integer> array_nums = new ArrayList<Integer>(0);
    private ArrayList<ListItem> arrayItemList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_a_lot_of_listview);
        toolbar = findViewById(R.id.listView_Toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        //Смена иконки с чёрной на белую.
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_black);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        array_nums = intent.getIntegerArrayListExtra("array_nums");
        Log.d(TAG, "onCreate: array_nums count = "+array_nums.size());
        arrayItemList = new ArrayList<>();
        for (int i = 0; i < array_nums.size(); i++) {
            //Добавление ListItem в arrayItemList
            ListItem current = new ListItem(array_nums.get(i));
            arrayItemList.add(current);

        }
        Log.d(TAG, "onCreate: arrayItemList count = "+arrayItemList.size());
        adapter = new CustomAdapter(arrayItemList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: //Метод обработки нажатия на кнопку home.
                this.finish();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
