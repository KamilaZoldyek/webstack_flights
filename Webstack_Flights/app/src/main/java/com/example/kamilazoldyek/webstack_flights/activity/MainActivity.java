package com.example.kamilazoldyek.webstack_flights.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kamilazoldyek.webstack_flights.R;

import java.util.ArrayList;
import java.util.List;

import io.swagger.client.ApiClient;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.model.Location;
import io.swagger.client.model.search.FareList;
import io.swagger.client.model.search.FlightList;
import io.swagger.client.model.search.RequestedFlightSegmentList;
import io.swagger.client.model.search.SearchTrip;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    private Button button;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button_home);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                button.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.shrink_anim));
                button.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        button.setVisibility(View.VISIBLE);
    }
}
