package com.example.kamilazoldyek.webstack_flights.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
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


    private ApiClient apiClient;
    private DefaultApi api;
    private Button button;

    private List<Location> loc;
    private List<FlightList> flightLists;
    private List<RequestedFlightSegmentList> segmentLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//            Start API service
        apiClient = new ApiClient();
        apiClient.createDefaultAdapter();
        api = apiClient.createService();


//        use when theres actionBar
        /*
        toolbar = findViewById(R.id.toolbar);
        toolbarTV = findViewById(R.id.toolbarTextView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTV.setText("aeroportos");
        */


//        test stuff
       /* tv = findViewById(R.id.textview);
        loc = new ArrayList<>();
        flightLists = new ArrayList<>();
        segmentLists = new ArrayList<>();
        getLocations();
        getSearch(); */

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }




    public void getTaxes(){

    }





    public void getSearch(){
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

                for (RequestedFlightSegmentList segmentList : segmentLists) {
                    flightLists = segmentList.getFlightList();
                    for(FlightList flight : flightLists) {
                        String content = "";
                        content += "Airline: " + flight.getAirline().getName() + "\n";
                        content += "Arrival: " + flight.getArrival().getDate() + "\n";
                        content += "Seats: " + flight.getAvailableSeats() + "\n";
                        content += "Departure Airport: " + flight.getDeparture().getAirport().getName() + "\n";
                        content += "Cabin: " + flight.getCabin() + "\n\n";

                        for(FareList fare : flight.getFareList()){
                            content += "    Fare: " + fare.getType() + "\n";
                            content += "        Miles: " + fare.getMiles() + "\n";
                            content += "        Base Miles: " + fare.getBaseMiles() + "\n";
                            content += "        Money: " + fare.getMoney() + "\n";
                            content += "        Factor: " + fare.getLoadFactor() + "\n";
                        }

                        tv.append(content);
                    }
                }
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
                loc = response.body();
                for (Location location : loc) {
                    String content = "";
                    content += "Nome: " + location.getCity() + "\n";
                    content += "Code: " + location.getCode() + "\n\n";

                    tv.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.i("Test", "Code: " + t.getMessage());

            }
        });



    }
}
