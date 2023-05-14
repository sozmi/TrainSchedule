package com.example.train_schedule.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.train_schedule.R;
import com.example.train_schedule.main.fragments.ScheduleListFragment;
import com.example.train_schedule.model.server.DataManager;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataManager.loadData(this);
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_fragment_view, new ScheduleListFragment());
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        DataManager.saveRoutes(this);
        super.onDestroy();
    }
}