package com.automation.homework.hw1;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UINames {

//    private final List<String> genders = Arrays.asList("male", "female");

//    UI names API testing
//    In this assignment, you will test api uinames. This is a free api used to create test users. Documentation
//    for this api is available at https://uinames.com. You can import Postman collection for this API using link:
//    https://www.getpostman.com/collections/e1338b73a8be7a5500e6. Automate the given test cases. You
//    can use any existing project. You can automate all test cases in same class or different classes.

    @BeforeAll
    public static void beforeAll() {
        baseURI = "https://cybertek-ui-names.herokuapp.com/api/";
    }

//    TEST CASES
//    No params test
//   1. Send a get request without providing any parameters
//   2. Verify status code 200, content type application/json; charset=utf-8
//   3. Verify that name, surname, gender, region fields have value

    @Test
    @DisplayName("No param test")
    public void noParam() {
        Response response = when().get().prettyPeek();
        response.then().
                assertThat().
                statusCode(200).
                and().
                contentType("application/json; charset=utf-8").
                and().
                body("name", notNullValue()).
                body("surname", notNullValue()).
                body("gender", notNullValue()).
                body("region", notNullValue());
    }

    //    Gender test
//1. Create a request by providing query parameter: gender, male or female
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that value of gender field is same from step 1
    @Test
    @DisplayName("Gender test")
    public void genderTest() {
        Collections.shuffle(genders); //to change order of our list
        String gender = genders.get(0); //because of shuffling our index can change and we can pick random gender
        Response response = given().
                queryParam("gender", gender).
                when().
                get().prettyPeek();
        response.
                then().assertThat().statusCode(200).
                and().
                contentType("application/json; charset=utf-8").
                and().
                body("gender", is(gender));
    }

//2 params test
//1. Create a request by providing query parameters: a valid region and gender
//    NOTE: Available region values are given in the documentation
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that value of gender field is same from step 1
//4. Verify that value of region field is same from step 1

    @Test
    @DisplayName("2 params test")
    public void twoParamsTest() {
        Response response = given().
                queryParam("region", "Denmark").
                queryParam("gender", "female").
                when().get().prettyPeek();
        response.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8").
                and().
                body("region", is("Denmark")).
                body("gender", is("female"));
    }

    //    Invalid gender test
//1. Create a request by providing query parameter: invalid gender
//2. Verify status code 400 and status line contains Bad Request
//3. Verify that value of error field is Invalid gender
    @Test
    @DisplayName("Invalid gender test")
    public void invalidGenderTest() {
        Response response = given().
                queryParam("gender", "dog").
                when().get().prettyPeek();
        response.then().assertThat().statusCode(400).
                and().
                statusLine(containsString("Bad Request")).
                body("error", is("Invalid gender"));
    }

//    Invalid region test
//1. Create a request by providing query parameter: invalid region
//2. Verify status code 400 and status line contains Bad Request
//3. Verify that value of error field is Region or language not found

    @Test
    @DisplayName("invalid region test")
    public void invalidRegionTest() {
        Response response = given().
                queryParam("region", "Africa").
                when().get().prettyPeek();
        response.then().assertThat().statusCode(400).
                and().
                statusLine(containsString("Bad Request")).
                body("error", is("Region or language not found"));
    }

    //    Amount and regions test
//1. Create request by providing query parameters: a valid region and amount (must be bigger than 1)
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that all objects have different name+surname combination
    @Test
    @DisplayName("amount and regions test")
    public void amountAndRegionTest() {
        Response response =
                given().
                        queryParam("region", "Germany").
                        queryParam("amount", 300).
                        when().
                        get().prettyPeek();

        List<User> userList = response.jsonPath().getList("", User.class);
//        System.out.println("userList = " + userList);

        Set<String> fullNames = new HashSet<>();
        for (User user : userList) {
            String fullName = user.getName() + " " + user.getSurname();
            fullNames.add(fullName);
        }
//        System.out.println("fullNames = " + fullNames);
        response.then().assertThat().statusCode(200).
                and().
                header("Content-Type", "application/json; charset=utf-8").
                body("size()", is(fullNames.size()));

    }
//3 params test
//1. Create a request by providing query parameters: a valid region, gender and amount (must be bigger than 1)
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that all objects the response have the same region and gender passed in step 1

    //nextInt () method from Random class will create numbers between 0-499
    //That's why we add + 1
    //in this case my random number will be between 1-500
    int randomAmount = new Random().nextInt(500) + 1;

    List<String> genders = Arrays.asList("male", "female"); //We will pick random gender for each execution
    public String getRandomGender() {
        Collections.shuffle(genders);
        return genders.get(0);
    }
    //getProperty("user.dir") will provide project path: C:\Users\1\Desktop\bugbusters\APIHomeworks
    File namesJson = new File(System.getProperty("user.dir") + File.separator + "/src/test/java/com/automation/homework/hw1/names.json");

    JsonPath jsonPath = new JsonPath(namesJson);
    List<String> regions = jsonPath.getList("region");

    public String getRandomRegion() {
        Collections.shuffle(regions);
        return regions.get(0);
    }

    @Test
    @DisplayName("3 param test")
    public void threeParamTest() {

        String randomGender = getRandomGender();
        String randomRegion = getRandomRegion();

        System.out.println("randomRegion = " + randomRegion);
        System.out.println("randomGender = " + randomGender);
        System.out.println("randomAmount = " + randomAmount);

        Response response =
                given().
                    queryParam("region", randomRegion).
                    queryParam("gender", randomGender).
                    queryParam("amount", randomAmount).
                when().
                    get().prettyPeek();

        response.then().
                assertThat().
                    statusCode(200).
                and().
                    contentType("application/json; charset=utf-8").
                and().
                    body("size()", is(randomAmount)).
                    body("gender", everyItem(is(randomGender))). // List
                    body("region", everyItem(is(randomRegion))); // List
    }
//    Amount count test
//1. Create a request by providing query parameter: amount (must be bigger than 1)
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that number of objects returned in the response is same as the amount passed in step 1

    @Test
    @DisplayName("Amount count test")
    public void amountCountTest() {
        Response response = given().
                    queryParam("amount",randomAmount).
                when().
                    get().prettyPeek();
        response.then().
                    assertThat().statusCode(200).
                and().
                    contentType("application/json; charset=utf-8").
                body("size()",is(randomAmount));
    }
}
