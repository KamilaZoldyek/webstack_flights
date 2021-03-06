package com.example.kamilazoldyek.webstack_flights.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.kamilazoldyek.webstack_flights.R;
import com.example.kamilazoldyek.webstack_flights.util.CustomDateFormat;
import com.example.kamilazoldyek.webstack_flights.util.LocalData;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.swagger.client.ApiClient;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.model.ResultTaxes;
import io.swagger.client.model.search.FareList;
import io.swagger.client.model.search.FlightList;
import io.swagger.client.model.search.LegList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripRecyclerAdapter extends RecyclerView.Adapter<TripRecyclerAdapter.ViewHolder> {


    private List<FlightList> flightList;
    private Context mContext;
    LocalData localData;

    public TripRecyclerAdapter(List<FlightList> flightList, Context mContext) {
        this.flightList = flightList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TripRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        localData = new LocalData(mContext);


        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.flight_card_item, viewGroup, false);
        final TripRecyclerAdapter.ViewHolder vHolder = new ViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TripRecyclerAdapter.ViewHolder v, int position) {

        // stuff to bind
        final FlightList flights = flightList.get(position);

        ApiClient apiClient = new ApiClient();
        apiClient.createDefaultAdapter();
        DefaultApi api = apiClient.createService();


        v.detailLayout.setVisibility(View.GONE);
        v.arrivalAirportCode.setVisibility(View.GONE);
        v.departureAirportCode.setVisibility(View.GONE);
        v.arrivalAirportNameTV.setVisibility(View.GONE);
        v.departureAirportNmeTV.setVisibility(View.GONE);
        v.detailIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(v.detailLayout.getVisibility()==View.GONE) {
                    v.detailIV.animate().rotation(180).start();

                    v.detailLayout.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.dropdown_anim));
                    v.detailLayout.setVisibility(View.VISIBLE);

                    v.arrivalAirportCode.setVisibility(View.VISIBLE);
                    v.arrivalAirportCode.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.dropdown_anim));

                    v.departureAirportNmeTV.setVisibility(View.VISIBLE);
                    v.departureAirportNmeTV.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.dropdown_anim));

                    v.departureAirportCode.setVisibility(View.VISIBLE);
                    v.departureAirportCode.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.dropdown_anim));

                    v.arrivalAirportNameTV.setVisibility(View.VISIBLE);
                    v.arrivalAirportNameTV.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.dropdown_anim));
                }
                else {
                    v.detailIV.animate().rotation(0).start();
                    v.detailLayout.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.upward_anim));
                    v.detailLayout.setVisibility(View.GONE);

                    v.arrivalAirportCode.setVisibility(View.GONE);
                    v.arrivalAirportCode.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.upward_anim));

                    v.departureAirportNmeTV.setVisibility(View.GONE);
                    v.departureAirportNmeTV.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.upward_anim));

                    v.departureAirportCode.setVisibility(View.GONE);
                    v.departureAirportCode.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.upward_anim));


                    v.arrivalAirportNameTV.setVisibility(View.GONE);
                    v.arrivalAirportNameTV.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.upward_anim));
                }
            }
        });

        List<LegList> legList = new ArrayList<>(flights.getLegList());
        List<FareList> fareLists = new ArrayList<>(flights.getFareList());

        String depDate = CustomDateFormat.CustomDateFormatDate(flights.getDeparture().getDate());
        String arrDate = CustomDateFormat.CustomDateFormatDate(flights.getArrival().getDate());
        String depTime = CustomDateFormat.CustomDateFormatTime(flights.getDeparture().getDate());
        String arrTime = CustomDateFormat.CustomDateFormatTime(flights.getArrival().getDate());

        String uid_flight = flights.getUid();
        String fare_uid = fareLists.get(1).getUid();
        int passengers = Integer.valueOf(localData.getPassengers());

        Call<ResultTaxes> call = api.taxesGet(uid_flight, fare_uid, passengers, "", "");
        call.enqueue(new Callback<ResultTaxes>() {
            @Override
            public void onResponse(Call<ResultTaxes> call, Response<ResultTaxes> response) {
                if (!response.isSuccessful()) {
                    v.taxes.setText("Erro");
                }else {
                    float taxes = response.body().getTaxes().getMoney();
                    String taxesFormatted = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(taxes);
                    v.taxes.setText("Taxa de " + taxesFormatted);
                }


            }

            @Override
            public void onFailure(Call<ResultTaxes> call, Throwable t) {

            }
        });


        String miles = NumberFormat.getNumberInstance(Locale.getDefault()).format(fareLists.get(0).getMiles());
        v.milesRadio.setText(miles + " Milhas");

        String milesMoney = NumberFormat.getNumberInstance(Locale.getDefault()).format(fareLists.get(2).getMiles());
        String money = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(fareLists.get(2).getMoney());
        v.milesMoneyRadio.setText(milesMoney + " Milhas + "+ money);

        v.flightNumberTV.setText("Voo número " + legList.get(0).getFlightNumber().toString());

        v.departureIV.setVisibility(View.GONE);
        v.returnIV.setVisibility(View.GONE);

        if(localData.getDepartureCode().equalsIgnoreCase(flights.getDeparture().getAirport().getCode())){
            v.isDeparture.setText("Ida");
            v.departureIV.setVisibility(View.VISIBLE);
        }else {
            v.isDeparture.setText("Volta");
            v.returnIV.setVisibility(View.VISIBLE);
        }

        v.departureDateTV.setText(depDate);
        v.departureTimeTV.setText(depTime);
        v.arrivalTimeTV.setText(arrTime);

        v.departureAirportCity.setText(flights.getDeparture().getAirport().getCity());
        v.departureAirportCode.setText(flights.getDeparture().getAirport().getCode());
        v.departureAirportNmeTV.setText(flights.getDeparture().getAirport().getName());
        v.departureLocaldate.setText(depDate);

        v.arrivalAirportCity.setText(flights.getArrival().getAirport().getCity());
        v.arrivalAirportCode.setText(flights.getArrival().getAirport().getCode());
        v.arrivalAirportNameTV.setText(flights.getArrival().getAirport().getName());
        v.arrivalLocalDate.setText(arrDate);

        v.flightCompanyTV.setText(flights.getAirline().getName());

        if(flights.getCabin().equalsIgnoreCase("economic")){
            v.cabinTypeTV.setText("Classe econômica");
        }else if(flights.getCabin().equalsIgnoreCase("business")){
            v.cabinTypeTV.setText("Classe executiva");
        }

        if(flights.getAvailableSeats()==1){
            v.seatsTV.setText("Último assento!");
        }else{
            v.seatsTV.setText(flights.getAvailableSeats() + " assentos restando");
        }

        if(flights.getStops()==0){
            v.stopsTV.setText("Sem escalas");
        }else if(flights.getStops()==1){
            v.stopsTV.setText("Uma escala");
        }else if(flights.getStops()==1){
            v.stopsTV.setText(flights.getStops() + " escalas");
        }
        v.durationTimeTV.setText(flights.getDuration() + "h \n Duração");

    }

    @Override
    public int getItemCount() {
        return flightList.size(); //should be flights size?
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView departureDateTV, flightCompanyTV, departureTimeTV,
                departureAirportNmeTV, durationTimeTV, isDeparture,
                arrivalTimeTV, arrivalAirportNameTV, cabinTypeTV,
                stopsTV, seatsTV, flightNumberTV, departureAirportCode,
                departureAirportCity,arrivalAirportCode, detailTV, taxes,
                departureLocaldate, arrivalLocalDate, arrivalAirportCity;

        public ImageView detailIV, departureIV, returnIV;
        public LinearLayout detailLayout;
        public RadioButton milesRadio, milesMoneyRadio;

        public ViewHolder(@NonNull View v) {
            super(v);
            departureDateTV = v.findViewById(R.id.departureDateTV);
            flightCompanyTV = v.findViewById(R.id.flightCompanyTV);
            departureTimeTV = v.findViewById(R.id.departureTimeTV);
            departureAirportNmeTV = v.findViewById(R.id.departureAirportName);
            durationTimeTV = v.findViewById(R.id.durationTimeTV);
            arrivalTimeTV = v.findViewById(R.id.arrivalTimeTV);
            arrivalAirportNameTV = v.findViewById(R.id.arrivalAirportName);
            cabinTypeTV = v.findViewById(R.id.cabinTypeTV);
            stopsTV = v.findViewById(R.id.stopsTV);
            seatsTV = v.findViewById(R.id.seatsTV);
            flightNumberTV = v.findViewById(R.id.flightNumberTV);
            isDeparture = v.findViewById(R.id.isDeparture);
            departureIV = v.findViewById(R.id.departureIV);
            returnIV = v.findViewById(R.id.returnIV);
            departureAirportCode = v.findViewById(R.id.departureAirportCode);
            departureAirportCity = v.findViewById(R.id.departureAirportCity);
            arrivalAirportCode = v.findViewById(R.id.arrivalAirportCode);
            arrivalAirportCity = v.findViewById(R.id.arrivalAirportCity);
            departureLocaldate = v.findViewById(R.id.departureLocalDate);
            arrivalLocalDate = v.findViewById(R.id.arrivalLocalDate);
            detailLayout = v.findViewById(R.id.detailLayout);
            detailIV = v.findViewById(R.id.detailIV);
            detailTV = v.findViewById(R.id.detailTV);
            milesRadio = v.findViewById(R.id.miles);
            milesMoneyRadio = v.findViewById(R.id.miles_money);
            taxes = v.findViewById(R.id.taxes);

        }
    }
}
