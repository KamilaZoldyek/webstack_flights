
package io.swagger.client.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Total implements Serializable
{

    @SerializedName("miles")
    @Expose
    public Integer miles;

    public Integer getMiles() {
        return miles;
    }

    public Float getMoney() {
        return money;
    }

    @SerializedName("money")
    @Expose
    public Float money;
    private final static long serialVersionUID = -6657858450548066731L;

}
