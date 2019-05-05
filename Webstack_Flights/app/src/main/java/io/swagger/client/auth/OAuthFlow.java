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


package io.swagger.client.auth;

public enum OAuthFlow {
    accessCode, implicit, password, application
}