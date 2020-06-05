package com.automation.tests.day7;

import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class BasicAuthentication {

    @Test
    public void spartanAuthentication() {

        // in the given part, we  provide request specification
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");

        given().
                auth().basic("admin","admin").
        when().
                get("/spartans").prettyPeek().
        then().
                assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Add a new Spartan")
    public void authenticationTest() {

        // in the given part, we  provide request specification
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");

        Spartan spartan = new Spartan ("Araz","Male",3445678902L);

        given().
                auth().basic("user","user").
                body(spartan).
                contentType(ContentType.JSON). // must provide if adding a new record
        when().
                post("/spartans").prettyPeek().
                then().
                assertThat().statusCode(403); // -> Forbidden
        /**
         *  user does not have a privilege to add, delete or edit users. Only read
         *  admin can add new users
         *  403 - Forbidden access. You logged in, but you are trying to do something that you are not allowed.
         *  Authentication problem - you didn't login
         *  Authorization problem - you logged in but cannot do some actions.
         */
    }

    @Test
    public void authenticationTest2(){
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
        //if don't provide credentials, we must get 401 status code
        get("/spartans").prettyPeek().then().statusCode(401); //-> Unauthorized. Need to log in
    }

    @Test
    public void authenticationTest3(){

        baseURI = "http://practice.cybertekschool.com";

        given().
                auth().
                basic("admin","admin").
        when().
                get("/basic_auth").prettyPeek().
        then().
                statusCode(200).
                contentType(ContentType.HTML);
    }
}
