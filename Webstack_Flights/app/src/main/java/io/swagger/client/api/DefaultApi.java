package io.swagger.client.api;

import io.swagger.client.model.Location;
import io.swagger.client.model.search.SearchTrip;
import retrofit2.Call;
import retrofit2.http.*;

import io.swagger.client.model.InlineResponse200;
import io.swagger.client.model.Login;
import io.swagger.client.model.Purchase;

import java.util.List;

public interface DefaultApi {

  /**
   * Purchase flight(s)
   * After flights &amp; fares are selected, passengers set and taxes known, a logged in User can request a checkout of desired UIDs and acknowledge total value to make a purchase
   * @param purchase  (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("checkout")
  Call<Void> checkoutPost(
    @retrofit2.http.Body Purchase purchase
  );

  /**
   * User login
   * Some actions require a user to be authenticated, and use a session token to request data. Any valid username will do, and password is (username + @123).
   * @param login username and password (required)
   * @return Call&lt;InlineResponse200&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("login")
  Call<InlineResponse200> loginPost(
    @retrofit2.http.Body Login login
  );

  /**
   * My Flights
   * A logged in User can request a list of purchased flights.
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("my-flights")
  Call<Void> myFlightsGet();



  /**
   * get all airports
   * Every airport code available as an itinerary.
   * @return Call&lt;Void&gt;
   */
  @GET("locations")
  Call<List<Location>> locationsGet();

  /**
   * search for flights
   * Search will return a list of possible flights for requested itinerary. Every flight has detailed info about it, including every leg and possible fares, both in miles (points) and money, or a combination of both.
   * @param origin Airport code of origin (required)
   * @param destination Airport code of destination (required)
   * @param departure1 Date of outbound departure. YYYY-MM-DD (required)
   * @param passengers Number of passengers. 1 - 9 (required)
   * @param departure2 Date of return departure, in case of roundtrip search. YYYY-MM-DD (optional)
   * @return Call&lt;Void&gt;
   */
  @Headers({"Content-Type:application/json"})
  @GET("search")
  Call<SearchTrip> searchGet(
    @Query("origin") String origin,
    @Query("destination") String destination,
    @Query("departure1") String departure1,
    @Query("passengers") Integer passengers,
    @Query("departure2") String departure2
  );

  /**
   * get taxes for selected flights
   * A flight UID and a fare UID, combined, will be needed to calculate both fare costs and airport taxes, which must be paid upon purchase.
   * @param flight1 Flight UID of first selected flight (outbound) (required)
   * @param fare1 One of the Fares UID from first selected flight (required)
   * @param passengers Number of passengers. 1 - 9 (required)
   * @param flight2 Flight UID of second selected flight (return) (optional)
   * @param fare2 One of the Fares UID from second selected flight (optional)
   * @return Call&lt;Void&gt;
   */
  @Headers({"Content-Type:application/json"})
  @GET("taxes")
  Call<Void> taxesGet(
    @Query("flight1") String flight1,
    @Query("fare1") String fare1,
    @Query("passengers") Integer passengers,
    @Query("flight2") String flight2,
    @Query("fare2") String fare2
  );
}
