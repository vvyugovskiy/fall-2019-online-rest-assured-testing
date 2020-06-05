package com.automation.office_hour.B12Marufjon;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class PathAndQueryParameters {

    // github.com/openratesapi/openrates

    /**
     * given --> create a request with parameter 2020-01-02
     * whenI send my request to http://api.openrates.io/{date}
     * then the response should contain 2020-01-02
     */
    @Test
    public void pathParamTest() {
        Response response = given().
                pathParam("date", "2020-01-02"). // OR latest
                when().
                get("http://api.openrates.io/{date}").prettyPeek();
        response.then().statusCode(200);
        assertTrue(response.asString().contains("2020-01-02"));
    }

    /**
     * given --> create a request with wrong parameter 2020-30-02
     * whenI send my request to http://api.openrates.io/{date}
     * then the status code should be 400
     */
    @Test
    public void pathParamTestNegative() {
        Response response = given().
                    pathParam("date", "2020-30-02"). // OR latest
                when().
                    get("http://api.openrates.io/{date}");
        response.then().statusCode(400);
    }

    /**
     * given --> I create request with query parameter base=USD
     * when --> I send my request to  http://api.openrates.io/latest
     * then --> the response should contain "base":"USD"
     */
    @Test
    public void queryParam() {
        // queryParam --> ?base=USD
        Response response =
                given().
                        queryParam("base", "USD").
                 when().
                        get("http://api.openrates.io/latest");
        response.prettyPeek();
        assertTrue(response.asString().contains("\"base\":\"USD\""));
    }

    /**
     * given --> create request with query parameter base=USD and symbols=MYR
     * when --> I send my code to http://api.openrates.io/latest
     * then --> the response should contain "base":"USD"
     * and --> body should contain MYR
     * but should not contain EUR
     */
    @Test
    public void test2QueryParams() {
        Response response =
                given().
                        queryParam("base", "USD").
                        queryParam("symbols", "MYR").
                        when().
                        get("http://api.openrates.io/latest");
        response.prettyPeek();
        String responseStr = response.asString();

        assertTrue(responseStr.contains("USD") && responseStr.contains("MYR"));
        assertFalse(responseStr.contains("EUR"));
    }
    @Test
    public void test3QueryParams() {
        Response response = given().
                                    queryParam("query", "Moscow").
                            when().get("http://www.metaweather.com/api/location/search/");
        assertTrue(response.asString().contains("Moscow"));
    }

    @Test
    public void test3QueryParamsNegative() {
        Response response = given().
                queryParam("query", "sdgfhjnk").
                when().get("http://www.metaweather.com/api/location/search/");
        response.prettyPeek(); // returns 200 code anyway while should be 400 range --> BUG
    }

}
