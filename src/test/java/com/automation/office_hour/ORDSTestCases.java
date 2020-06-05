package com.automation.office_hour;

import com.automation.utilities.ConfigurationReader;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class ORDSTestCases {

    // http://jsonviewer.stack.hu/
    // http://jsonpath.com/


    /**
     * get all the employees and their department ids
     * verify that dep_id points to the existing record in the departments table;
     * verify response 200
     * verify department name is not empty
     */

    @BeforeAll
    public static void setup (){
        baseURI = ConfigurationReader.getProperty("ORDS.URI");
    }

    /**
     * get all records from the employees table using the /employees
     * verify that number of employees is more than 100
     */

    @Test
    public void employeesTest (){
        // turns out we get only 25 empl per page so we need to make extra request
        // using limit to get all at once
        Response response = given().
                    queryParam("limit",200).
                when().
                    get("/employees");

        response.then().statusCode(200);

        List <Integer> arr = response.jsonPath().getList("items.department_id");
        System.out.println("arr = " + arr);

        Set<Integer> uniqueIDs = new HashSet<>();
        uniqueIDs.addAll(arr);
        System.out.println("uniqueIDs = " + uniqueIDs);

        // get all employees into a List of Maps. each map represents one employee
        List<Map<String,Object>> employees = response.jsonPath().getList("items");
//        System.out.println(employees.size());
        System.out.println(employees.get(1));
        assertThat(employees.size(),greaterThan(100));

        for (Integer depId : uniqueIDs){
            // call the department/:id to get the specific department
            // verify 200 code, verify name is not null
            given().
                    pathParam("id",depId).
            when().
                    get("/departments/{id}").prettyPeek().
            then().statusCode(200).and().body("department_name",not(emptyOrNullString()));
        }
    }
}
/**

 {

 id:12
 name:nnn
 },
 {
 id:13
 name:mmmm
 }
 */