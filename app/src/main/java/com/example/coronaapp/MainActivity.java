package com.example.coronaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;

public class MainActivity extends AppCompatActivity {

    private TextView tv_confirmed, tv_confirmed_new, tv_active, tv_active_new, tv_recovered, tv_recovered_new, tv_death,
            tv_death_new, tv_tests, tv_tests_new, tv_date, tv_time;
    private SwipeRefreshLayout swipeRefreshLayout;

    private PieChart pieChart;

    private LinearLayout lin_state_data, lin_world_data;
    //private LinearLayout lin_state_data, lin_world_data;

    private String str_confirmed, str_confirmed_new, str_active, str_active_new, str_recovered, str_recovered_new,
            str_death, str_death_new, str_tests, str_tests_new, str_last_update_time;
    private int int_active_new;
    private ProgressDialog progressDialog;
    private boolean doubleBackToExitPressedOnce = false;
    private Toast backPressToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        fetchData();

    }

    private void fetchData() {
        // here we intialzing and creating the request Queue
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String apiUrl="";
    }

    // initilalize the views
    private void Init() {
        tv_confirmed = findViewById(R.id.activity_main_confirm_recovery_textview);
        tv_confirmed_new = findViewById(R.id.activity_main_confirm_sample_new_textview);
        tv_active = findViewById(R.id.activity_main_active_textview);
        tv_active_new = findViewById(R.id.activity_main_active_new_textview);
        tv_recovered = findViewById(R.id.activity_main_confirm_recovery_textview);
        tv_recovered_new = findViewById(R.id.activity_main_confirm_recovery_new_textview);
        tv_death = findViewById(R.id.activity_main_death_configured_card);
        tv_death_new = findViewById(R.id.activity_main_death_configured_new_textview);
        tv_tests = findViewById(R.id.activity_main_configured_sample_card);
        tv_tests_new = findViewById(R.id.activity_main_confirm_sample_new_textview);
        tv_date = findViewById(R.id.activity_main_date_lastUpdate_configured_new_textview);
        tv_time = findViewById(R.id.activity_main_time_lastUpdate_death_confirm_textview);

        pieChart = findViewById(R.id.activity_main_piechart);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        //lin_state_data = findViewById(R.id.activity_main_statewise_configured_title_textview);
        //lin_world_data = findViewById(R.id.activity_mainworld_lastUpdate_configured_title_textview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //we use menuInflater class to inflate our menu in our app

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);
            if(item.getItemId() ==R.id.menu_about)
            {
                Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
            }
            return super.onOptionsItemSelected(item);
    }
}