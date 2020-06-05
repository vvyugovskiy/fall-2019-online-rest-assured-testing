package com.automation.tests.day5;

import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
public class POJOPractice {

    @BeforeAll
    public static void beforeAll () {

        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
    }

    @Test
    public void getUser () {
        Response response  = given().
                auth().
                basic("admin", "admin").
                when().
                get("/spartans/{id}",99);

        Spartan spartan = response.as(Spartan.class);
        System.out.println(spartan);

        Map<String, ?> spartanAsMap = response.as(Map.class);
        System.out.println(spartanAsMap);

        assertEquals(99,spartan.getId());
        assertEquals("Adair", spartan.getName());
    }

    @Test
    public void addUser (){
        Spartan spartan = new Spartan("Hasan Jan","Male", 2132435465L);

        Gson gson = new Gson();
        String pojoAsJson = gson.toJson(spartan);
        System.out.println(pojoAsJson);

        Response response = given().auth().basic("admin", "admin").
                contentType(ContentType.JSON).
                body(spartan).
                when().post("/spartans").prettyPeek();

        response.then().statusCode(201); // make sure user was created

//         id of new user
        int userId = response.jsonPath().getInt("data.id");
        System.out.println(userId);

        System.out.println("#####DELETE######");

        given().auth().basic("admin","admin").
                when().delete("/spartans/{id}",101).prettyPeek().
                then().assertThat().statusCode(204);  // to ensure user was deleted
    }
}
