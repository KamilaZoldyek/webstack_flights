
package io.swagger.client.model.search;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FareList implements Serializable
{

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("money")
    @Expose
    private Integer money;
    @SerializedName("miles")
    @Expose
    private Integer miles;
    @SerializedName("loadFactor")
    @Expose
    private Float loadFactor;
    @SerializedName("baseMiles")
    @Expose
    private Integer baseMiles;
    @SerializedName("type")
    @Expose
    private String type;
    private final static long serialVersionUID = -1179696882765817288L;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getMiles() {
        return miles;
    }

    public void setMiles(Integer miles) {
        this.miles = miles;
    }

    public Float getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(Float loadFactor) {
        this.loadFactor = loadFactor;
    }

    public Integer getBaseMiles() {
        return baseMiles;
    }

    public void setBaseMiles(Integer baseMiles) {
        this.baseMiles = baseMiles;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
