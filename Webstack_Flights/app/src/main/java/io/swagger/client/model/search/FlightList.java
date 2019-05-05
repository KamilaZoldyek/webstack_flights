
package io.swagger.client.model.search;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlightList implements Serializable
{

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("cabin")
    @Expose
    private String cabin;
    @SerializedName("stops")
    @Expose
    private Integer stops;
    @SerializedName("departure")
    @Expose
    private Departure departure;
    @SerializedName("arrival")
    @Expose
    private Arrival arrival;
    @SerializedName("airline")
    @Expose
    private Airline airline;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("legList")
    @Expose
    private List<LegList> legList = null;
    @SerializedName("fareList")
    @Expose
    private List<FareList> fareList = null;
    @SerializedName("availableSeats")
    @Expose
    private Integer availableSeats;
    private final static long serialVersionUID = 1681858074164676997L;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public Integer getStops() {
        return stops;
    }

    public void setStops(Integer stops) {
        this.stops = stops;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public Arrival getArrival() {
        return arrival;
    }

    public void setArrival(Arrival arrival) {
        this.arrival = arrival;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<LegList> getLegList() {
        return legList;
    }

    public void setLegList(List<LegList> legList) {
        this.legList = legList;
    }

    public List<FareList> getFareList() {
        return fareList;
    }

    public void setFareList(List<FareList> fareList) {
        this.fareList = fareList;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

}
