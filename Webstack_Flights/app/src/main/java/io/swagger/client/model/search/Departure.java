
package io.swagger.client.model.search;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Departure implements Serializable
{

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("airport")
    @Expose
    private Airport airport;
    private final static long serialVersionUID = 3877334219970488866L;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

}
