
package io.swagger.client.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Taxes implements Serializable
{

    @SerializedName("money")
    @Expose
    public Float money;
    private final static long serialVersionUID = 4874254806019910481L;

    public Float getMoney() {
        return money;
    }
}
