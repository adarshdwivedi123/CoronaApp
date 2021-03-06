package com.example.coronaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tv_confirmed, tv_confirmed_new, tv_active, tv_active_new, tv_recovered, tv_recovered_new, tv_death,
            tv_death_new, tv_tests, tv_tests_new, tv_date, tv_time;
    private SwipeRefreshLayout swipeRefreshLayout;

    private PieChart pieChart;
    private ProgressDialog progressDialog;
    private LinearLayout lin_state_data, lin_world_data;
    private String str_confirmed, str_confirmed_new, str_active, str_active_new, str_recovered, str_recovered_new,
            str_death, str_death_new, str_tests, str_tests_new, str_last_update_time;
    private int int_active_new=0;
    //private ProgressDialog progressDialog;
    private boolean doubleBackToExitPressedOnce = false;
    private Toast backPressToast;
    //private MainActivity activity=new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setting up titile from thetext
        getSupportActionBar().setTitle("Covid-19 Tracker (India)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Init();
        fetchData();



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
                swipeRefreshLayout.setRefreshing(false);
                //Toast.makeText(MainActivity.this, "Data refreshed!", Toast.LENGTH_SHORT).show();
            }
        });


        lin_state_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(MainActivity.this, "State data", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(MainActivity.this, StateWiseDataActivity.class));
            }
        });

        lin_world_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "World data", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(MainActivity.this, WorldDataActivity.class);
                //startActivity(intent);
                startActivity(new Intent(MainActivity.this, WorldDataActivity.class));
            }
        });

    }

    private void fetchData() {
        ShowDialog(this);
        // here we intialzing and creating the request Queue
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String apiUrl="https://api.covid19india.org/data.json";

        pieChart.clearChart();
        //json request null because we don't want to send any thing to the server that why it null
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray  all_state_jsonArray =null;
                JSONArray  testData_jsonArray=null;

                try {
                    all_state_jsonArray=response.getJSONArray("statewise"); // 1 array
                    testData_jsonArray=response.getJSONArray("tested");  // 2 array
                    JSONObject data_india=all_state_jsonArray.getJSONObject(0);
                    JSONObject test_data_india=testData_jsonArray.getJSONObject(testData_jsonArray.length()-1);


                    //str_confirmed=data_india.getString("confirmed");
                    str_confirmed = data_india.getString("confirmed");   //Confirmed cases in India
                    str_confirmed_new = data_india.getString("deltaconfirmed");   //New Confirmed cases from last update time

                    str_active = data_india.getString("active");    //Active cases in India

                    str_recovered = data_india.getString("recovered");  //Total recovered cased in India
                    str_recovered_new = data_india.getString("deltarecovered"); //New recovered cases from last update time

                    str_death = data_india.getString("deaths");     //Total deaths in India
                    str_death_new = data_india.getString("deltadeaths");    //New death cases from last update time

                    str_last_update_time = data_india.getString("lastupdatedtime"); //Last update date and time

                    str_tests = test_data_india.getString("totalsamplestested"); //Total samples tested in India
                    str_tests_new = test_data_india.getString("samplereportedtoday");   //New samples tested today

                    //handler we use to make some delay in call
                    Handler delayToShowProgress=new Handler();
                    delayToShowProgress.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv_confirmed.setText(NumberFormat.getInstance().format(Integer.parseInt(str_confirmed)));
                            tv_confirmed_new.setText("+" + NumberFormat.getInstance().format(Integer.parseInt(str_confirmed_new)));

                            tv_active.setText(NumberFormat.getInstance().format(Integer.parseInt(str_active)));

                            int_active_new = Integer.parseInt(str_confirmed_new)
                                    - (Integer.parseInt(str_recovered_new) + Integer.parseInt(str_death_new));
                            tv_active_new.setText("+"+NumberFormat.getInstance().format(int_active_new));

                            tv_recovered.setText(NumberFormat.getInstance().format(Integer.parseInt(str_recovered)));
                            tv_recovered_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_recovered_new)));

                            tv_death.setText(NumberFormat.getInstance().format(Integer.parseInt(str_death)));
                            tv_death_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_death_new)));

                            tv_tests.setText(NumberFormat.getInstance().format(Integer.parseInt(str_tests)));
                           // tv_tests_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_tests_new)));

                            tv_date.setText(FormatDate(str_last_update_time, 1));
                            tv_time.setText(FormatDate(str_last_update_time, 2));
                            pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(str_active), Color.parseColor("#007afe")));
                            pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(str_recovered), Color.parseColor("#08a045")));
                            pieChart.addPieSlice(new PieModel("Deceased", Integer.parseInt(str_death), Color.parseColor("#F6404F")));

                            pieChart.startAnimation();

                           DismissDialog();

                        }

                    },1000);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonObjectRequest);

    }

    public void ShowDialog(Context context) {

        //setting up progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.project_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }



    public void DismissDialog()
    {
        progressDialog.dismiss();
    }


    public String FormatDate(String date, int testCase) {
        Date mDate = null;
        String dateFormat;
        try {
            mDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(date);
            if (testCase == 0) {
                dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a").format(mDate);
                return dateFormat;
            } else if (testCase == 1) {
                dateFormat = new SimpleDateFormat("dd MMM yyyy").format(mDate);
                return dateFormat;
            } else if (testCase == 2) {
                dateFormat = new SimpleDateFormat("hh:mm a").format(mDate);
                return dateFormat;
            } else {
                Log.d("error", "Wrong input! Choose from 0 to 2");
                return "Error";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }


    // initilalize the views
    private void Init() {
        tv_confirmed = findViewById(R.id.activity_main_confirm_textview);
        tv_confirmed_new = findViewById(R.id.activity_main_configured_new_textview);
        tv_active = findViewById(R.id.activity_main_active_textview);
        tv_active_new = findViewById(R.id.activity_main_active_new_textview);
        tv_recovered = findViewById(R.id.activity_main_confirm_recovery_textview);
        tv_recovered_new = findViewById(R.id.activity_main_confirm_recovery_new_textview);
        tv_death = findViewById(R.id.activity_main_death_confirm_textview);
        tv_death_new = findViewById(R.id.activity_main_death_configured_new_textview);
        tv_tests = findViewById(R.id.activity_main_confirm_sample_textview);
        tv_tests_new = findViewById(R.id.activity_main_confirm_sample_new_textview);
        tv_date = findViewById(R.id.activity_main_date_lastUpdate_configured_new_textview);
        tv_time = findViewById(R.id.activity_main_time_lastUpdate_death_confirm_textview);
        pieChart = findViewById(R.id.activity_main_piechart);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        lin_state_data = findViewById(R.id.state_data);
        lin_world_data = findViewById(R.id.world_data);
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
                //Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
            }
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            backPressToast.cancel();
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        backPressToast = Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT);
        backPressToast.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

        }

}