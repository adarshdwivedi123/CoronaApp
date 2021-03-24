package com.example.coronaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class WorldDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_data);
        getSupportActionBar().setTitle("Covid-19 Tracker (India)");
    }
}