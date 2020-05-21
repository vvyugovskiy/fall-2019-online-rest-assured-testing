package com.automation.tests.day3;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;

public class ORDSTestsDay3 {

    @BeforeAll
    public static void setup() {
        baseURI = "http://54.167.107.90:1000/ords/hr";
    }

    /**
     * given path parameter is "/regions/{id}"
     * when user makes get request
     * then assert that status code is 200
     * and assert that region name is Europe
     * and assert that region id is 1
     */

    @Test
    public void verifyRegion() {
        given().
                pathParam("id", 1).
                when().
                get("/regions/{id}").prettyPeek().
                then().assertThat().statusCode(200).
                body("region_name", is("Europe")).
                body("region_id", is(1)).
                time(lessThan(5L), TimeUnit.SECONDS);  // verify that response time is less than 5 second
    }

    @Test
    public void verifyEmployee() {

        Response response =
                given().
                        contentType(ContentType.JSON).
                        accept(ContentType.JSON).
                        when().
                        get("/employees");

        /**
         * JsonPath is an alternative to using XPath for easily getting values from a Object document. It follows the
         *  * Groovy <a href="http://docs.groovy-lang.org/latest/html/documentation/#_gpath">GPath</a>
         *  syntax when getting an object from the document. You can regard it as an alternative to XPath for JSON.
         */
        JsonPath jsonPath = response.jsonPath();

        //items - name of the array where all employees are stored
        //GPath, something like XPath bit different. GPath use Groovy syntax
        String nameOfFirstEmployee = jsonPath.getString("items[0].first_name");
        String nameOfLastEmployee = jsonPath.get("items[-1].first_name"); // -1, -2, -3 to go from the back

        System.out.println("nameOfFirstEmployee = " + nameOfFirstEmployee);
        System.out.println("nameOfLastEmployee = " + nameOfLastEmployee);


        Map<String, ?> firstEmployee = jsonPath.get("items[0]");
        System.out.print("firstEmployee = " + firstEmployee);
    }

}
