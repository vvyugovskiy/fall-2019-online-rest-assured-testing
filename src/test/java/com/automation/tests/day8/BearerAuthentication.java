package com.automation.tests.day8;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class BearerAuthentication {

    @BeforeAll
    public static void setup() {

        baseURI = "https://cybertek-reservation-api-qa.herokuapp.com";
    }

    @Test
    public void loginTest() {
        Response response = given().
                queryParam("email", "teacherva5@gmail.com").
                queryParam("password", "maxpayne").
                when().
                get("/sign").prettyPeek();

        String token = response.jsonPath().getString("accessToken");
        System.out.println("Token :: " + token);
    }

    @Test
    @DisplayName("Negative test: attempt to retrieve list of rooms without token")
    public void getRoomsTest() {
        // 422 is Ok, because we did not get any data
        // but suppose to get 401 code
        get("/api/rooms").prettyPeek().then().statusCode(401);
    }

    @Test
    public void getRoomsTest2() {

        Response response = given().
                queryParam("email", "teacherva5@gmail.com").
                queryParam("password", "maxpayne").
                when().
                get("/sign").prettyPeek();

        String token = response.jsonPath().getString("accessToken");

        Response response2 = given().
                auth().oauth2(token).
                when().
                get("/api/rooms").prettyPeek();

        response.then().statusCode(200).and().contentType(ContentType.JSON);
    }

    @Test
    public void getAllTeamsTest() {
        Response response = given().
                                    header("Authorization", "Bearer " + getToken()).
                            when().
                                    get("/api/teams").prettyPeek();

        response.then().statusCode(200);
    }


    public String getToken() {
        Response response = given().
                queryParam("email", "teacherva5@gmail.com").
                queryParam("password", "maxpayne").
                when().
                get("/sign");
        response.then().log().ifError();

        String token = response.jsonPath().getString("accessToken");
        System.out.println("Token :: " + token);
        return token;
    }

    public String getToken(String email, String password) {
        Response response = given().
                queryParam("email", email).
                queryParam("password", password).
                when().
                get("/sign");
        response.then().log().ifError();

        String token = response.jsonPath().getString("accessToken");
        System.out.println("Token :: " + token);
        return token;
    }

}
