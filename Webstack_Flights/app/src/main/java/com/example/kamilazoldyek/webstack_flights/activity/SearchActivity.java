package com.example.kamilazoldyek.webstack_flights.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamilazoldyek.webstack_flights.R;
import com.example.kamilazoldyek.webstack_flights.adapter.AutoCompleteLocationAdapter;

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

public class SearchActivity extends AppCompatActivity {

    private ApiClient apiClient;
    private DefaultApi api;
    private List<Location> locationList;
    private List<FlightList> flightLists;
    private List<RequestedFlightSegmentList> segmentLists;
    private Toolbar toolbar;
    private TextView toolbarTV, originTV, destinationTV;
    public AutoCompleteTextView originAutoComplete, destinationAutoComplete;
    private LinearLayout returnLayout, layout;
    private CheckBox checkBox;
    private Spinner passengersSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_trip_activity);

        //        use when theres toolBar
        toolbar = findViewById(R.id.toolbar);
        toolbarTV = findViewById(R.id.toolbarTextView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTV.setText("Pesquisa");

        locationList = new ArrayList<>();
        flightLists = new ArrayList<>();
        segmentLists = new ArrayList<>();

        //            Start API service
        apiClient = new ApiClient();
        apiClient.createDefaultAdapter();
        api = apiClient.createService();

        //        views
        returnLayout = findViewById(R.id.returnLayout);
        checkBox = findViewById(R.id.checkBox);
        passengersSpinner = findViewById(R.id.passangersSpinner);
        layout = findViewById(R.id.originLayout);
        originAutoComplete = findViewById(R.id.autoCompleteOrigin);
        destinationAutoComplete = findViewById(R.id.autoCompleteDestination);

        layout.requestFocus();

        //        checkbox
        checkBox.setChecked(false);
        returnLayout.setVisibility(View.GONE);
        onCheckboxClicked(checkBox);

        //        Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.passengersSpinner, R.layout.spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        passengersSpinner.setAdapter(adapter);


        //        Search locations
        getLocations();
    }

    public void getSearch() {
        Call<SearchTrip> call = api.searchGet("GIG", "GRU", "2019-07-15", 1, "");
        call.enqueue(new Callback<SearchTrip>() {
            @Override
            public void onResponse(Call<SearchTrip> call, Response<SearchTrip> response) {
                if (!response.isSuccessful()) {
                    Log.i("TestKamis", "Code: " + response.code());
                    return;
                }
                Log.i("TestKamis", "Code: " + response.code());
                SearchTrip searchTrip = response.body();
                segmentLists = searchTrip.getRequestedFlightSegmentList();

                /*for (RequestedFlightSegmentList segmentList : segmentLists) {
                    flightLists = segmentList.getFlightList();
                    for (FlightList flight : flightLists) {
                        String content = "";
                        content += "Airline: " + flight.getAirline().getName() + "\n";
                        content += "Arrival: " + flight.getArrival().getDate() + "\n";
                        content += "Seats: " + flight.getAvailableSeats() + "\n";
                        content += "Departure Airport: " + flight.getDeparture().getAirport().getName() + "\n";
                        content += "Cabin: " + flight.getCabin() + "\n\n";

                        for (FareList fare : flight.getFareList()) {
                            content += "    Fare: " + fare.getType() + "\n";
                            content += "        Miles: " + fare.getMiles() + "\n";
                            content += "        Base Miles: " + fare.getBaseMiles() + "\n";
                            content += "        Money: " + fare.getMoney() + "\n";
                            content += "        Factor: " + fare.getLoadFactor() + "\n";
                        }

                        tv.append(content);
                    }
                }*/
            }

            @Override
            public void onFailure(Call<SearchTrip> call, Throwable t) {
                Log.i("Test", "Code: " + t.getMessage());
            }
        });


    }

    public void getLocations() {

        Call<List<Location>> call = api.locationsGet();
        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (!response.isSuccessful()) {
                    Log.i("TestKamis", "Code: " + response.code());
                    return;
                }
                Log.i("TestKamis", "Code: " + response.code());
                locationList = response.body();
                if(!locationList.isEmpty()){
                    AutoCompleteLocationAdapter adapterLoc = new AutoCompleteLocationAdapter(SearchActivity.this, locationList);
                    originAutoComplete.setAdapter(adapterLoc);
                    destinationAutoComplete.setAdapter(adapterLoc);
                }
            }
            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.i("Test", "Code: " + t.getMessage());
            }
        });
    }

    public void onCheckboxClicked(View cb) {
        boolean checked = ((CheckBox) cb).isChecked();
        if (checked) {
            returnLayout.setAnimation(AnimationUtils.loadAnimation(SearchActivity.this, R.anim.dropdown_anim));
            returnLayout.setVisibility(View.VISIBLE);
        } else {
            returnLayout.setAnimation(AnimationUtils.loadAnimation(SearchActivity.this, R.anim.upward_anim));
            returnLayout.setVisibility(View.GONE);
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: 06/05/19 reseta tudo aqui
    }
}
