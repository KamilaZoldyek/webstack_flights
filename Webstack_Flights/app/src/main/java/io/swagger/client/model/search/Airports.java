
package io.swagger.client.model.search;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Airports implements Serializable
{

    @SerializedName("departureAirportList")
    @Expose
    private List<DepartureAirportList> departureAirportList = null;
    @SerializedName("arrivalAirportList")
    @Expose
    private List<ArrivalAirportList> arrivalAirportList = null;
    private final static long serialVersionUID = 4052298656580840630L;

    public List<DepartureAirportList> getDepartureAirportList() {
        return departureAirportList;
    }

    public void setDepartureAirportList(List<DepartureAirportList> departureAirportList) {
        this.departureAirportList = departureAirportList;
    }

    public List<ArrivalAirportList> getArrivalAirportList() {
        return arrivalAirportList;
    }

    public void setArrivalAirportList(List<ArrivalAirportList> arrivalAirportList) {
        this.arrivalAirportList = arrivalAirportList;
    }

}
