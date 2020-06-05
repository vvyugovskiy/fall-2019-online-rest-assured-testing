package com.automation.office_hour;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiKeyExample {


    @BeforeAll
    public static void setup() {
        baseURI = "https://www.zipcodeapi.com/";

    }

    /**
     * get distance between 2 zip codes
     * verify status 200
     * verify distance field not empty
     * /rest/:api_key/distance.json/:zip_code1/:zip_code2/:units
     */


    @Test
    public void testDistance() {
        given().
                pathParam("api_key", "5dsKkfk6heQJbK0tJTkQpIIsX1OmHPAy40uLtt53mFyP0ZYWfsThZ0LQMCJTvCQK").
                pathParam("zip_code1", "20005").
                pathParam("zip_code2", "20001").
                pathParam("units", "miles").
                when().
                get("/rest/{api_key}/distance.json/{zip_code1}/{zip_code2}/{units}").
                prettyPeek().
                then().
                assertThat().
                statusCode(200).
                body("distance", not(emptyOrNullString()));

    }

    /**
     * test zipcode 29577
     * verify city Myrtle Beach
     */

    @Test
    public void testMB() {

        given().
                pathParam("api_key", "5dsKkfk6heQJbK0tJTkQpIIsX1OmHPAy40uLtt53mFyP0ZYWfsThZ0LQMCJTvCQK").
                pathParam("zip_code", "29577").
                pathParam("units", "degrees").
        when().
                log().all().
                get("/rest/{api_key}/info.json/{zip_code}/{units}").
                prettyPeek().
        then().
                assertThat().statusCode(is(200)).
        and().
                assertThat().body("city", is("Myrtle Beach"));

    }
}
