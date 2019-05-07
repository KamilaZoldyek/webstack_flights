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

    public String getOrigin() {return sharedPreferences.getString(origin, "*"); }
    public void setOrigin(String o) {
        editor.putString(origin, o);
        editor.commit();
    }

    public String getDestination() {return sharedPreferences.getString(destination, "*");}
    public void setDestination(String d) {
        editor.putString(destination, d);
        editor.commit();
    }

    public String getPassengers() {return sharedPreferences.getString(passengers, "*");}
    public void setPassengers(String p) {
        editor.putString(passengers, p);
        editor.commit();
    }
}
