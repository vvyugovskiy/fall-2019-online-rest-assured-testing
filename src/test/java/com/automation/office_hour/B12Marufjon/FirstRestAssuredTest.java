package com.automation.office_hour.B12Marufjon;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class FirstRestAssuredTest {

    // github.com/openratesapi/openrates

    /**
     * when I send request to http://api.openrates.io/latest
     * Then status code must be 200
     */
    @Test
    public void verifyStatusCode (){
        // response --> what is sent by server as result of our request
        // get --> send the request to given url
        Response response = when().get("http://api.openrates.io/latest");
        response.prettyPrint();
        // verify the StatusCode is 200
        response.then().statusCode(200);
    }
    /**
     * when I send request to http://api.openrates.io/latest
     * Then body should contain "base":"EUR"
     */

    @Test
    public void verifyBodyContains (){
        Response response = when().get("http://api.openrates.io/latest");
        // asString() --> returns the body as a single string
        String bodyStr = response.asString();
        System.out.println(bodyStr);
        assertTrue(bodyStr.contains("\"base\":\"EUR\""));
    }
    /**
     * when I send request to http://api.openrates.io/latest
     * Then then response header should contain application/json
     */
    @Test
    public void verifyHeader1(){
        Response response = when().get("http://api.openrates.io/latest");
        // response.header() --> returns the value of the provided header
        String contentType = response.header("Content-Type");
        String date = response.header("date");

        System.out.println(contentType);
        System.out.println(date);

        assertEquals("application/json",contentType);
        assertTrue(date.contains("2020"));
    }
    /**
     * when I send request to http://api.openrates.io/latest
     * Then then response header should contain application/json
     */
    @Test
    public void verifyContentType (){
        Response response = when().get("http://api.openrates.io/latest");
        // response.getContentType --> returns the content Type of the response
        String contentType = response.getContentType();
        System.out.println(contentType);
        // response.getStatusCode() --> returns the status of the response
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);

        assertEquals("application/json",contentType);
        // this line will print
        response.prettyPeek();
    }

    /**
     * When i send request to http://api.zippopotam.us/us/22031
     * Then the status Code must be 200
     * And verify that response contains Fairfax
     */
    @Test
    public void verifyZippoStatusCode (){
        Response response = when().get("http://api.zippopotam.us/us/22031").prettyPeek();
        int statusCode = response.statusCode();
        System.out.println(statusCode);
        assertEquals(200,statusCode);
        assertTrue(response.asString().contains("Fairfax"));
        // or we can do 1 lines
        given().accept(ContentType.JSON).when().get("http://api.zippopotam.us/us/22031").then().assertThat().statusCode(200);
    }
    /**
     * When i send request to http://api.zippopotam.us/us/22031111
     * Then the status Code must be 400
     */
    @Test
    public void verifyZippoStatusCode2 (){
        Response response = when().get("http://api.zippopotam.us/us/22031111");
        int responseCode = response.getStatusCode();
        response.then().statusCode(404);
        assertEquals(404,responseCode);
    }
}
