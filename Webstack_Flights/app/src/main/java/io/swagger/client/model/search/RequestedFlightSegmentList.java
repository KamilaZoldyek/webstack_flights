
package io.swagger.client.model.search;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestedFlightSegmentList implements Serializable
{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("flightList")
    @Expose
    private List<FlightList> flightList = null;
    @SerializedName("airports")
    @Expose
    private Airports airports;
    @SerializedName("companyList")
    @Expose
    private List<CompanyList> companyList = null;
    private final static long serialVersionUID = -3152268277868079986L;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<FlightList> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<FlightList> flightList) {
        this.flightList = flightList;
    }

    public Airports getAirports() {
        return airports;
    }

    public void setAirports(Airports airports) {
        this.airports = airports;
    }

    public List<CompanyList> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<CompanyList> companyList) {
        this.companyList = companyList;
    }

}
