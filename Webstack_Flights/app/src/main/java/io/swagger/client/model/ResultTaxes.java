
package io.swagger.client.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultTaxes implements Serializable
{

    @SerializedName("total")
    @Expose
    public Total total;

    public Total getTotal() {
        return total;
    }

    public Integer getPassengers() {
        return passengers;
    }

    public Fares getFares() {
        return fares;
    }

    public Taxes getTaxes() {
        return taxes;
    }

    @SerializedName("passengers")
    @Expose
    public Integer passengers;
    @SerializedName("fares")
    @Expose
    public Fares fares;
    @SerializedName("taxes")
    @Expose
    public Taxes taxes;
    private final static long serialVersionUID = 3482012722241653943L;

}
