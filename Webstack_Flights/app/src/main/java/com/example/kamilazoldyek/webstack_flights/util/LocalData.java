package com.example.kamilazoldyek.webstack_flights.util;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalData {

    private static final String APP_SHARED_PREFERENCES = "LocalData";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String departureDate = "depDate";
    private String returnDate = "retDate";
    private String origin = "origin";
    private String destination = "destination";
    private String passengers = "passengers";
    private String roundTrip = "roundTrip";
    private String origCity = "origCity";
    private String destCity = "destCity";
    private String isDeparture = "isDep";
    private String isReturn = "isRet";

    public LocalData(Context context) {
        this.sharedPreferences = context.getSharedPreferences(APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public String getDepartureDate() {return sharedPreferences.getString(departureDate, "*"); }
    public void setDepartureDate(String depDate) {
        editor.putString(departureDate, depDate);
        editor.commit();
    }

    public String getReturnDate() {return sharedPreferences.getString(returnDate, "");}
    public void setReturnDate(String retDate) {
        editor.putString(returnDate, retDate);
        editor.commit();
    }

    public String getDepartureCode() {return sharedPreferences.getString(origin, "*"); }
    public void setDepartureCode(String o) {
        editor.putString(origin, o);
        editor.commit();
    }

    public String getArrivalCode() {return sharedPreferences.getString(destination, "*");}
    public void setArrivalCode(String d) {
        editor.putString(destination, d);
        editor.commit();
    }

    public String getOrigCity() {return sharedPreferences.getString(origCity, "*");}
    public void setOrigCity(String c) {
        editor.putString(origCity, c);
        editor.commit();
    }

    public String getDestCity() {return sharedPreferences.getString(destCity, "*");}
    public void setDestCity(String c) {
        editor.putString(destCity, c);
        editor.commit();
    }

    public String getPassengers() {return sharedPreferences.getString(passengers, "*");}
    public void setPassengers(String p) {
        editor.putString(passengers, p);
        editor.commit();
    }

    public Boolean getIsDeparture() {return sharedPreferences.getBoolean(isDeparture, false);}
    public void setIsDeparture(Boolean b) {
        editor.putBoolean(isDeparture, b);
        editor.commit();
    }

    public Boolean getIsReturn() {return sharedPreferences.getBoolean(isReturn, false);}
    public void setIsReturn(Boolean b) {
        editor.putBoolean(isReturn, b);
        editor.commit();
    }

    public Boolean getRoundTrip() {return sharedPreferences.getBoolean(roundTrip, false);}
    public void setRoundTrip(Boolean b) {
        editor.putBoolean(roundTrip, b);
        editor.commit();
    }
}
