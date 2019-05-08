package com.example.kamilazoldyek.webstack_flights.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kamilazoldyek.webstack_flights.R;
import com.example.kamilazoldyek.webstack_flights.adapter.TripRecyclerAdapter;
import com.example.kamilazoldyek.webstack_flights.util.Constant;
import com.example.kamilazoldyek.webstack_flights.util.LocalData;

import java.util.ArrayList;
import java.util.List;

import io.swagger.client.ApiClient;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.model.Location;
import io.swagger.client.model.search.FlightList;
import io.swagger.client.model.search.RequestedFlightSegmentList;
import io.swagger.client.model.search.SearchTrip;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTV, isroundtripTV, passTV,
            departCityTV, arrivCityTV, depAirportNameTV, arrAirportNameTV, errorTV;

    private List<Location> locationList;
    private List<FlightList> flightLists;
    private List<RequestedFlightSegmentList> segmentLists;

    private ApiClient apiClient;
    private DefaultApi api;
    private LocalData localData;
    private Boolean isRoundTrip;

    private TripRecyclerAdapter tripAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private LinearLayout errorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_list_activity);

        //        use when theres toolBar
        toolbar = findViewById(R.id.toolbar);
        toolbarTV = findViewById(R.id.toolbarTextView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTV.setText("Voos");

        locationList = new ArrayList<>();
        flightLists = new ArrayList<>();
        segmentLists = new ArrayList<>();

        //            Start API service
        apiClient = new ApiClient();
        apiClient.createDefaultAdapter();
        api = apiClient.createService();

        localData = new LocalData(getApplicationContext());
        isRoundTrip = localData.getRoundTrip();

        recyclerView = findViewById(R.id.recyclerView);
        isroundtripTV = findViewById(R.id.isRoundTrip);
        passTV = findViewById(R.id.passTV);
        departCityTV = findViewById(R.id.departCityTV);
        arrivCityTV = findViewById(R.id.arrivCityTV);
        progressBar = findViewById(R.id.progressBar2);
        errorLayout = findViewById(R.id.error_layout);
        errorTV = findViewById(R.id.error_TV);

        errorLayout.setVisibility(View.GONE);

        if (isRoundTrip) {
            isroundtripTV.setText("Ida e volta");
        } else {
            isroundtripTV.setText("Ida");
        }
        int passengers = Integer.valueOf(localData.getPassengers());

        if (passengers == 1) {
            passTV.setText("1 passageiro");
        } else {
            passTV.setText(localData.getPassengers() + " passageiros");
        }
        departCityTV.setText(localData.getOrigCity());
        arrivCityTV.setText(localData.getDestCity());

        linearLayoutManager = new LinearLayoutManager(FlightListActivity.this);

        getSearch();

    }

    public void getSearch() {

        Call<SearchTrip> call = api.searchGet(localData.getDepartureCode(),
                localData.getArrivalCode(),
                localData.getDepartureDate(),
                Integer.valueOf(localData.getPassengers()),
                localData.getReturnDate());

        call.enqueue(new Callback<SearchTrip>() {
            @Override
            public void onResponse(Call<SearchTrip> call, Response<SearchTrip> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.VISIBLE);
                    switch (response.code()) {
                        case 400:
                            errorTV.setText(Constant.ERR_400_BAD_INPUT);
                            break;
                        case 401:
                            errorTV.setText(Constant.ERR_401_NOT_LOGGED);
                            break;
                        case 404:
                            errorTV.setText(Constant.ERR_404_NOT_FOUND);
                            break;
                        case 500:
                            errorTV.setText(Constant.ERR_500_SERVER_ERROR);
                            break;
                        default:
                            errorTV.setText("");
                    }
                    return;
                }

                if (!(response.body() == null)) {
                    SearchTrip searchTrip = response.body();
                    segmentLists = searchTrip.getRequestedFlightSegmentList(); //two segments = roundtrip  one segment = oneway

                    List<FlightList> departures = segmentLists.get(0).getFlightList();
                    List<FlightList> cat = new ArrayList<>(departures);

                    if (isRoundTrip) {
                        List<FlightList> returns = segmentLists.get(1).getFlightList();
                        cat.addAll(returns);
                        setUpRoundTripRecyclerAdapter(cat);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        setUpRoundTripRecyclerAdapter(departures);
                        progressBar.setVisibility(View.GONE);
                    }

                } else {
                    errorLayout.setVisibility(View.VISIBLE);
                    errorTV.setText("");
                }
            }
            @Override
            public void onFailure(Call<SearchTrip> call, Throwable t) {
                errorLayout.setVisibility(View.VISIBLE);
                errorTV.setText(t.getMessage());
            }
        });
    }

    public void setUpRoundTripRecyclerAdapter(List<FlightList> list) {
        tripAdapter = new TripRecyclerAdapter(list, FlightListActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(tripAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
