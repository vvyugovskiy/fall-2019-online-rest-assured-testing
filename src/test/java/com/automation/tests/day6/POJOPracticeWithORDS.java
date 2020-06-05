package com.automation.tests.day6;

import com.automation.pojos.Employee;
import com.automation.pojos.Link;
import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
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
public class POJOPracticeWithORDS {

    @BeforeAll
    public static void setup (){
        baseURI = ConfigurationReader.getProperty("ORDS.URI");
    }

    public void getEmployeeTest () {

        Response response = get("/employees/{id}",100).prettyPeek();
        Employee employee = response.as(Employee.class);
        System.out.println(employee);

        List<Link> link = employee.getLinks();
        System.out.println(link);
    }

}
