package com.example.kamilazoldyek.webstack_flights.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import com.example.kamilazoldyek.webstack_flights.util.Constant;
import com.example.kamilazoldyek.webstack_flights.util.CustomDateFormat;
import com.example.kamilazoldyek.webstack_flights.util.LocalData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private AutoCompleteTextView originAutoComplete, destinationAutoComplete;
    private LinearLayout returnLayout, layout, departurePickerLayout,
            originLayout, destLayout, passagersLayout, departureDateLayout,
            returnQuestionLayout, returnDateLayout;
    private CheckBox checkBox;
    private Spinner passengersSpinner;
    private String departureDate, returnDate;
    private boolean isRoundTrip;
    private LocalData localData;
    private Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_trip_activity);

        localData = new LocalData(getApplicationContext());
        localData.setDepartureDate("*");
        localData.setDestCity("*");
        localData.setOrigCity("*");
        localData.setRoundTrip(false);
        localData.setReturnDate("");
        localData.setPassengers("1");
        localData.setArrivalCode("*");
        localData.setDepartureCode("*");
        localData.setIsDeparture(false);
        localData.setIsReturn(false);

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
        returnLayout = findViewById(R.id.returnDateLayout);
        checkBox = findViewById(R.id.checkBox);
        passengersSpinner = findViewById(R.id.passangersSpinner);
        layout = findViewById(R.id.originLayout);
        originAutoComplete = findViewById(R.id.autoCompleteOrigin);
        destinationAutoComplete = findViewById(R.id.autoCompleteDestination);
        departurePickerLayout = findViewById(R.id.departureDateLayout);
        departureDateTV = findViewById(R.id.departureDateTV);
        returnDateTV = findViewById(R.id.returnDateTV);
        searchButton = findViewById(R.id.button_search);
        originLayout = findViewById(R.id.originLayout);
        destLayout = findViewById(R.id.destLayout);
        passagersLayout = findViewById(R.id.passagersLayout);
        departureDateLayout = findViewById(R.id.departureDateLayout);
        returnQuestionLayout = findViewById(R.id.returnQuestionLayout);
        returnDateLayout = findViewById(R.id.returnDateLayout);

        layout.requestFocus();

        //        checkbox
        checkBox.setChecked(false);
        localData.setReturnDate("");
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
                openDateDialog();
            }
        });
        returnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRoundTrip = true;
                openDateDialog();
            }
        });


//      do the magic
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localData.setPassengers(passengersSpinner.getSelectedItem().toString());

                if (isSomethingEmpty()) {
                    openDialog(R.layout.error_no_date_dialog, "common");
                    } else
                        if (localData.getRoundTrip()) {
                            if (localData.getReturnDate().equals("")) {
                                openDialog(R.layout.error_no_date_dialog, "common");
                                Log.i(Constant.TEST, "onclick");
                            }
                            else {
                                    Intent intent = new Intent(SearchActivity.this, FlightListActivity.class);
                                    startActivity(intent);
                                    }
                        }else {
                            Intent intent = new Intent(SearchActivity.this, FlightListActivity.class);
                            startActivity(intent);
                        }
            }
        });


//        Listener for when done typing an destination airport
        destinationAutoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                View v = destinationAutoComplete;
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (actionId == EditorInfo.IME_ACTION_DONE) {
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
                    openDialog(R.layout.error_dialog, "fatal");

                }

                locationList = response.body();
                if (!locationList.isEmpty()) {
                    AutoCompleteLocationAdapter adapterLoc = new AutoCompleteLocationAdapter(SearchActivity.this, locationList);
                    originAutoComplete.setAdapter(adapterLoc);
                    destinationAutoComplete.setAdapter(adapterLoc);

                    originAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                            Location current = (Location) parent.getItemAtPosition(position);
                            localData.setDepartureCode(current.getCode());
                            localData.setOrigCity(current.getCity());


                        }
                    });

                    destinationAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                            Location current = (Location) parent.getItemAtPosition(position);
                            localData.setArrivalCode(current.getCode());
                            localData.setDestCity(current.getCity());


                        }
                    });
                } else {
                    openDialog(R.layout.error_dialog, "fatal");

                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                openDialog(R.layout.error_dialog, "fatal");

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

    public void openDateDialog() {
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


        if (isRoundTrip) {
            returnDate = y + "-" + (m + 1) + "-" + (d);
            if (isPast(returnDate, departureDate, 2)) {
                openDialog(R.layout.error_time_dialog, "return");
                returnDateTV.setText("Toque para selecionar");
            }
            String temp = y + "-" + (m + 1) + "-" + (d);
            date = CustomDateFormat.CustomDateFormatA(temp);
            returnDateTV.setText(date);
            localData.setReturnDate(returnDate);

        } else {
            departureDate = y + "-" + (m + 1) + "-" + (d);
            if (isPast(departureDate, "*", 1)) {
                openDialog(R.layout.error_time_dialog, "departure");
                departureDateTV.setText("Toque para selecionar");
            }
            if (!(returnDate == null)) {
                if(returnDate.equalsIgnoreCase("")) {
                    if (isPast(returnDate, departureDate, 2)) {
                        openDialog(R.layout.error_time_dialog, "departure");
                        departureDateTV.setText("Toque para selecionar");
                    }
                }
            }
            String temp = y + "-" + (m + 1) + "-" + (d);
            date = CustomDateFormat.CustomDateFormatA(temp);
            departureDateTV.setText(date);
            localData.setDepartureDate(departureDate);
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
    }

    public void openDialog(int layout, final String type) { //recebe o int do layout, r.layout.nome_do_layout

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SearchActivity.this);
        View mView = getLayoutInflater().inflate(layout, null);

        mBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (type) {
                    case "fatal":
                        finish();
                        break;
                    case "common":
                        dialogInterface.dismiss();
                        break;
                    case "departure":
                        departureDateTV.setText("Toque para selecionar");
                        localData.setDepartureDate("*");
                        dialogInterface.dismiss();
                        break;
                    case "return":
                        returnDateTV.setText("Toque para selecionar");
                        localData.setReturnDate("");
                        dialogInterface.dismiss();
                        break;
                    default:
                        dialogInterface.dismiss();
                }

            }
        });
        mBuilder.setView(mView)
                .create()
                .show();


    }

    public boolean isPast(String toTest, String control, int type) {
        boolean isPast = false;
        switch (type) {
            case 1: //before today
                try {
                    if (new SimpleDateFormat("yyyy-MM-dd").parse(toTest).before(new Date())) {
                        isPast = true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;

            case 2: //before return date
                if (control == null || control.equalsIgnoreCase("*") || control.equalsIgnoreCase("")) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    control = df.format(new Date());
                }
                if (toTest == null || toTest.equalsIgnoreCase("*") || toTest.equalsIgnoreCase("")) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    toTest = df.format(new Date());
                }

                try {
                    if (new SimpleDateFormat("yyyy-MM-dd").parse(toTest)
                            .before(new SimpleDateFormat("yyyy-MM-dd").parse(control))) {
                        isPast = true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
        return isPast;
    }

    public boolean isSomethingEmpty() {
        boolean isSomethingEmpty = false;
        if (localData.getDepartureDate().equalsIgnoreCase("*")
                || localData.getArrivalCode().equalsIgnoreCase("*")
                || localData.getDepartureCode().equalsIgnoreCase("*")
        ) {
            isSomethingEmpty = true;
        }
        return isSomethingEmpty;
    }


}
