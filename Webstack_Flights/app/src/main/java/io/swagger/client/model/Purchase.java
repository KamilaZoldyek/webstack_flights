/*
 * Webstack Flights API
 * # Introduction Mocked flights responses for an e-commerce system  # Overview This is a REST JSON API for developing new Frontend apps at Rethink  # Authentication Some endpoints require authentication. You should use the \"Authorization\" header in POST requests, containing the token received from the /login request.  # Error Codes 400 - Bad input 401 - Not logged in 404 - Not found 500 - Server error Response body will be an explanation  # Usage Saved data will be cleared after 1 hour Server is on Heroku Hobby so it sleeps most of the time
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.client.model.PurchaseTotal;
import java.io.IOException;

/**
 * Purchase
 */

public class Purchase {
  @SerializedName("flight1")
  private String flight1 = null;

  @SerializedName("fare1")
  private String fare1 = null;

  @SerializedName("flight2")
  private String flight2 = null;

  @SerializedName("fare2")
  private String fare2 = null;

  @SerializedName("passengers")
  private Integer passengers = null;

  @SerializedName("total")
  private PurchaseTotal total = null;

  public Purchase flight1(String flight1) {
    this.flight1 = flight1;
    return this;
  }

   /**
   * Get flight1
   * @return flight1
  **/
  @ApiModelProperty(required = true, value = "")
  public String getFlight1() {
    return flight1;
  }

  public void setFlight1(String flight1) {
    this.flight1 = flight1;
  }

  public Purchase fare1(String fare1) {
    this.fare1 = fare1;
    return this;
  }

   /**
   * Get fare1
   * @return fare1
  **/
  @ApiModelProperty(required = true, value = "")
  public String getFare1() {
    return fare1;
  }

  public void setFare1(String fare1) {
    this.fare1 = fare1;
  }

  public Purchase flight2(String flight2) {
    this.flight2 = flight2;
    return this;
  }

   /**
   * Get flight2
   * @return flight2
  **/
  @ApiModelProperty(value = "")
  public String getFlight2() {
    return flight2;
  }

  public void setFlight2(String flight2) {
    this.flight2 = flight2;
  }

  public Purchase fare2(String fare2) {
    this.fare2 = fare2;
    return this;
  }

   /**
   * Get fare2
   * @return fare2
  **/
  @ApiModelProperty(value = "")
  public String getFare2() {
    return fare2;
  }

  public void setFare2(String fare2) {
    this.fare2 = fare2;
  }

  public Purchase passengers(Integer passengers) {
    this.passengers = passengers;
    return this;
  }

   /**
   * Get passengers
   * minimum: 1
   * maximum: 9
   * @return passengers
  **/
  @ApiModelProperty(required = true, value = "")
  public Integer getPassengers() {
    return passengers;
  }

  public void setPassengers(Integer passengers) {
    this.passengers = passengers;
  }

  public Purchase total(PurchaseTotal total) {
    this.total = total;
    return this;
  }

   /**
   * Get total
   * @return total
  **/
  @ApiModelProperty(required = true, value = "")
  public PurchaseTotal getTotal() {
    return total;
  }

  public void setTotal(PurchaseTotal total) {
    this.total = total;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Purchase purchase = (Purchase) o;
    return Objects.equals(this.flight1, purchase.flight1) &&
        Objects.equals(this.fare1, purchase.fare1) &&
        Objects.equals(this.flight2, purchase.flight2) &&
        Objects.equals(this.fare2, purchase.fare2) &&
        Objects.equals(this.passengers, purchase.passengers) &&
        Objects.equals(this.total, purchase.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flight1, fare1, flight2, fare2, passengers, total);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Purchase {\n");
    
    sb.append("    flight1: ").append(toIndentedString(flight1)).append("\n");
    sb.append("    fare1: ").append(toIndentedString(fare1)).append("\n");
    sb.append("    flight2: ").append(toIndentedString(flight2)).append("\n");
    sb.append("    fare2: ").append(toIndentedString(fare2)).append("\n");
    sb.append("    passengers: ").append(toIndentedString(passengers)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

