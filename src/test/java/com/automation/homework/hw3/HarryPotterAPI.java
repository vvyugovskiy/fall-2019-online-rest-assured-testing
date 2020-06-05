package com.automation.homework.hw3;

import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HarryPotterAPI {

    private final String APIKEY = "$2a$10$HIOtM4dx5hg62e9VofOqRe7nvbHgR5p7QeS5s3lP57V66/RZJ5e7e";

    @BeforeAll
    public static void setup (){
        baseURI = "https://potterapi.com/v1";
    }


}
