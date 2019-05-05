
package io.swagger.client.model.search;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchTrip implements Serializable
{

    @SerializedName("requestedFlightSegmentList")
    @Expose
    private List<RequestedFlightSegmentList> requestedFlightSegmentList = null;
    private final static long serialVersionUID = 4215413428120321983L;

    public List<RequestedFlightSegmentList> getRequestedFlightSegmentList() {
        return requestedFlightSegmentList;
    }

    public void setRequestedFlightSegmentList(List<RequestedFlightSegmentList> requestedFlightSegmentList) {
        this.requestedFlightSegmentList = requestedFlightSegmentList;
    }

}
