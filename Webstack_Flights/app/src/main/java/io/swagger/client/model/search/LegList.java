
package io.swagger.client.model.search;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LegList implements Serializable
{

    @SerializedName("cabin")
    @Expose
    private String cabin;
    @SerializedName("flightNumber")
    @Expose
    private Integer flightNumber;
    @SerializedName("equipment")
    @Expose
    private String equipment;
    @SerializedName("stops")
    @Expose
    private Integer stops;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("departure")
    @Expose
    private Departure departure;
    @SerializedName("arrival")
    @Expose
    private Arrival arrival;
    @SerializedName("marketingAirline")
    @Expose
    private MarketingAirline marketingAirline;
    @SerializedName("operationAirline")
    @Expose
    private OperationAirline operationAirline;
    private final static long serialVersionUID = -2684967770251276925L;

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public Integer getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(Integer flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Integer getStops() {
        return stops;
    }

    public void setStops(Integer stops) {
        this.stops = stops;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public MarketingAirline getMarketingAirline() {
        return marketingAirline;
    }

    public void setMarketingAirline(MarketingAirline marketingAirline) {
        this.marketingAirline = marketingAirline;
    }

    public OperationAirline getOperationAirline() {
        return operationAirline;
    }

    public void setOperationAirline(OperationAirline operationAirline) {
        this.operationAirline = operationAirline;
    }

}
