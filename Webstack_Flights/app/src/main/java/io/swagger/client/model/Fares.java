
package io.swagger.client.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fares implements Serializable
{

    @SerializedName("miles")
    @Expose
    public Integer miles;
    @SerializedName("money")
    @Expose
    public Integer money;
    private final static long serialVersionUID = -271150176663882731L;

    public Integer getMiles() {
        return miles;
    }

    public Integer getMoney() {
        return money;
    }
}
