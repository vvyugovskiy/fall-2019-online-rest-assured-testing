package com.automation.tests.day6;

import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class POJOPracticeWithSpartainApp {

    @BeforeAll
    public static void beforeAll() {
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
        authentication = basic("admin", "admin");
//        config = config().objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON));
    }

    @Test
    public void assSpartanTest() {

        Map<String, String> spartan = new HashMap<>();
        spartan.put("gender", "Male");
        spartan.put("name", "Alexander");
        spartan.put("phone", "1234567890");

        RequestSpecification requestSpecification = given().auth().basic("admin", "admin").contentType(ContentType.JSON).body(spartan);

        Response response = given().
                auth().basic("admin", "admin").
                contentType(ContentType.JSON).
                body(spartan).
                when().
                post("/spartans").prettyPeek();

        response.then().statusCode(201);
        response.then().body("success", is("A Spartan is Born!"));
        //DESERIALIZATION
        Spartan spartanResponse = response.jsonPath().getObject("data", Spartan.class);
        // spartanResponse is a Spartan
        System.out.println(spartanResponse instanceof Spartan); // must be true

    }

    @Test
    @DisplayName("Retrieve existing user, update his name and verify that name was updated successfully")
    public void updateSpartan() {

        int userToUpdate = 106;
        String name = "Pablo";
        // HTTP PUT request to update existing recors, for example existing spartan
        // PUT - request to provide ALL parameters in body
        Spartan spartan = new Spartan(name, "Male", 1234567654L);

        Spartan spartanToUpdate = given().
                auth().basic("admin", "admin").
                contentType(ContentType.JSON).
                when().

                get("/spartans/{id}", userToUpdate).as(Spartan.class);

        System.out.println("Before update :: " + spartanToUpdate);
        // update property that we need without affecting other properties
        spartanToUpdate.setName(name);
        System.out.println("After update :: " + spartanToUpdate);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        // request to update existing user
        Response response = given().
                auth().basic("admin", "admin").
                contentType(ContentType.JSON).
                body(spartan).
                when().
                put("/spartans/{id}", userToUpdate).prettyPeek();

        // verify that statusCode is 204 after update
        response.then().statusCode(204);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");

        // to get user with id 106, the one that we've just updated
        given().
                auth().basic("admin", "admin").
                when().
                get("/spartans/{id}", userToUpdate).prettyPeek().
                then().
                statusCode(200).body("name", is(name));
        // verify that name is correct after change
    }

    @Test
    @DisplayName("Verify that user can perform PATCH request")
    public void patchUserTest1 (){

        //PATCH - partial update of existing record
        int userId = 1; // user ID to update. Make user with id exist

        // let's put the code to take random user
        // get all spartans
        Response response0 = given().accept(ContentType.JSON).when().get("/spartans");
        // I can save then all in array list
        List<Spartan> allSpartans = response0.jsonPath().getList("",Spartan.class);
        //Spartan.class - data type of collection
        //getList - get JSON body as a list

        // generate random number
        Random random = new Random();
        int randomNum = random.nextInt(allSpartans.size());

        int randomUserID = allSpartans.get(randomNum).getId();
        System.out.println("NAME BEFORE :: " + allSpartans.get(randomNum).getName());
        System.out.println("randomUserID = " + randomUserID);

        userId = randomUserID; // to assign random user id
        System.out.println(allSpartans);

        Map<String, String> update = new HashMap<>();
        update.put("name","Pedro");
        // this is a request to update user
        Response response = given().contentType(ContentType.JSON).body(update).when().patch("/spartans/{id}",userId);

        response.then().assertThat().statusCode(204);

        // after we send PATCH request, let's make sure that name  is updated
        // this is a request to verify that name was updated and status code is correct as well
        given().
                accept(ContentType.JSON).
        when().
                get("/spartans/{id}", userId).prettyPeek().
         then().
                assertThat().statusCode(200).body("name",is("Pedro"));
    }

}
