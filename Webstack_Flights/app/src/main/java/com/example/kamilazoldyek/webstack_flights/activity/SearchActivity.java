package com.example.kamilazoldyek.webstack_flights.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kamilazoldyek.webstack_flights.R;
import com.example.kamilazoldyek.webstack_flights.adapter.AutoCompleteLocationAdapter;
import com.example.kamilazoldyek.webstack_flights.fragment.DatePickerFragment;
import com.example.kamilazoldyek.webstack_flights.util.CustomDateFormat;
import com.example.kamilazoldyek.webstack_flights.util.LocalData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.swagger.client.ApiClient;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.model.Location;
import io.swagger.client.model.search.FlightList;
import io.swagger.client.model.search.RequestedFlightSegmentList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements DatePickerFragment.DatePickerFragmentListener {

    private ApiClient apiClient;
    private DefaultApi api;
    private List<Location> locationList;
    private List<FlightList> flightLists;
    private List<RequestedFlightSegmentList> segmentLists;
    private Toolbar toolbar;
    private TextView toolbarTV, departureDateTV, returnDateTV;
    public AutoCompleteTextView originAutoComplete, destinationAutoComplete;
    private LinearLayout returnLayout, layout, departurePickerLayout;
    private CheckBox checkBox;
    private Spinner passengersSpinner;
    private String departureDate, returnDate;
    public boolean isRoundTrip;
    public LocalData localData;
    private Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_trip_activity);

        localData = new LocalData(getApplicationContext());

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
        departurePickerLayout = findViewById(R.id.departureLayout);
        departureDateTV = findViewById(R.id.departureDateTV);
        returnDateTV = findViewById(R.id.returnDateTV);
        searchButton = findViewById(R.id.button_search);

        layout.requestFocus();
        searchButton.setVisibility(View.GONE);

        //        checkbox
        checkBox.setChecked(false);
        isRoundTrip = false;
        returnLayout.setVisibility(View.GONE);
        onCheckboxClicked(checkBox);

        //        Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.passengersSpinner, R.layout.spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        passengersSpinner.setAdapter(adapter);

        //        Search locations
        getLocations();


        //        date picker
        departurePickerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRoundTrip = false;
                openDialog();
            }
        });
        returnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRoundTrip = true;
                openDialog();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localData.setPassengers(passengersSpinner.getSelectedItem().toString());
                Intent intent = new Intent(SearchActivity.this, FlightListActivity.class);
                startActivity(intent);
            }
        });

//        Listener for when done typing an destination airport
        destinationAutoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                View v = destinationAutoComplete;
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    destinationAutoComplete.setSelection(0);
                    destinationAutoComplete.clearFocus();
                    layout.requestFocus();
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
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
//
//                    String or = originAutoComplete.getText().toString();
//                    localData.setDepartureCode(or);
//                    Log.i("kamis", or);
//
//                    String dp = originAutoComplete.getText().toString();
//                    localData.setDepartureCode(dp);
//                    Log.i("kamis", dp);



                    originAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                            Location current = (Location)parent.getItemAtPosition(position);
                            localData.setDepartureCode(current.getCode());
                            localData.setOrigCity(current.getCity());
                        }
                    });

                    destinationAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                            Location current = (Location)parent.getItemAtPosition(position);
                            localData.setArrivalCode(current.getCode());
                            localData.setDestCity(current.getCity());
                        }
                    });



                    // TODO: 06/05/19 pick the code from the edittext
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
            isRoundTrip = true;
            localData.setRoundTrip(true);
            returnLayout.setAnimation(AnimationUtils.loadAnimation(SearchActivity.this, R.anim.dropdown_anim));
            returnLayout.setVisibility(View.VISIBLE);
        } else {
            isRoundTrip = false;
            localData.setRoundTrip(false);
            returnLayout.setAnimation(AnimationUtils.loadAnimation(SearchActivity.this, R.anim.upward_anim));
            returnLayout.setVisibility(View.GONE);
        }
    }

    public void openDialog(){
        DatePickerFragment datepickDialog = new DatePickerFragment();
        datepickDialog.show(getSupportFragmentManager(), "Picker");
    }

    @Override
    public void onDateSet(int y, int m, int d) {
        String date;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, y);
        c.set(Calendar.MONTH, m);
        c.set(Calendar.DAY_OF_MONTH, d);


        if(isRoundTrip){
            returnDate = y + "-" + (m+1) + "-" + d;
            date = CustomDateFormat.CustomDateFormatA(returnDate);
            returnDateTV.setText(date);
            localData.setReturnDate(returnDate);

        }else {
            departureDate = y + "-" + (m+1) + "-" + d;
            date = CustomDateFormat.CustomDateFormatA(departureDate);
            departureDateTV.setText(date);
            localData.setDepartureDate(departureDate);
            searchButton.setVisibility(View.VISIBLE);
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
