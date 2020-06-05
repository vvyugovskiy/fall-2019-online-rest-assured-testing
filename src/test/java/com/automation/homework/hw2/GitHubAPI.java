package com.automation.homework.hw2;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GitHubAPI {
//    GitHub API testing
//    In this assignment, you will test GitHub API. Full documentation for GitHub API can be found here:
//    https://developer.github.com/v3/.
//    Automate the given test cases written based on couple of the endpoints from the GitHub API. You can
//    use any existing project. You can automate all test cases in same class or different classes.
//    Base Path: https://api.github.com

    @BeforeAll
    public static void beforeAll() {
        baseURI = "https://api.github.com";
    }

//    TEST CASES
//    Verify organization information
//1. Send a get request to /orgs/:org. Request includes :
//            • Path param org with value cucumber
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify value of the login field is cucumber
//4. Verify value of the name field is cucumber
//5. Verify value of the id field is 320565
    @Test
    @DisplayName("Verify organization information")
    public void organizationInfo() {
        Response response = given().
                pathParam("org","cucumber").
                when().
                get("/orgs/{org}").prettyPeek();
        response.then().
                assertThat().statusCode(200).
                contentType("application/json; charset=utf-8").
                body("login",is("cucumber")).
                body("name",equalToIgnoringCase("cucumber")).
                body("id",is(320565));
    }

    //    Verify error message
//1. Send a get request to /orgs/:org. Request includes :
//    • Header Accept with value application/xml
//    • Path param org with value cucumber
//2. Verify status code 415, content type application/json; charset=utf-8
//3. Verify response status line include message Unsupported Media Type
    @Test
    @DisplayName("Verify error message")
    public void errorMessage (){
        Response response = given().
                header("Accept","application/xml").
                when().
                get("/orgs/{org}","cucumber").prettyPeek();
        response.then().
                assertThat().statusCode(415).
                contentType("application/json; charset=utf-8").
                statusLine(containsString("Unsupported Media Type"));
    }
//    Number of repositories
//1. Send a get request to /orgs/:org. Request includes :
//   • Path param org with value cucumber
//2. Grab the value of the field public_repos
//3. Send a get request to /orgs/:org/repos. Request includes :
//    • Path param org with value cucumber
//4. Verify that number of objects in the response is equal to value from step 2
    @Test
    @DisplayName("Number of repositories")
    public void numberOfRepo (){
        Response response =
                given().
                        pathParam("org","cucumber").
                        queryParam("per_page",100).
                        when().
                        get("/orgs/{org}");
        int numberRepos = response.jsonPath().getInt("public_repos");

        Response response1 = given().
                pathParam("org","cucumber").
                queryParam("per_page",100).
                when().
                get("/orgs/{org}/repos");
        response1.then().
                assertThat().body("size()",is(numberRepos));
        System.out.println("numberRepos = " + numberRepos);
    }
    //    Repository id information
//1. Send a get request to /orgs/:org/repos. Request includes :
//    • Path param org with value cucumber
//2. Verify that id field is unique in every object in the response
//3. Verify that node_id field is unique in every object in the response
    @Test
    @DisplayName("Repository id information")
    public void idInfo (){
        Response response = given().
                queryParam("per_page",100).
                pathParam("org","cucumber").
                when().
                get("/orgs/{org}/repos");
        List<Integer> idList = response.jsonPath().getList("id");
        List<String> nodeIdList = response.jsonPath().getList("node_id");

        Set<Integer> idSet = new HashSet<>(idList);
        Set<String> nodeIdSet = new HashSet<>(nodeIdList);

        assertEquals(idList.size(),nodeIdSet.size());
        assertEquals(nodeIdList.size(),nodeIdSet.size());
    }

    //    Repository owner information
//1. Send a get request to /orgs/:org. Request includes :
//      • Path param org with value cucumber
//2. Grab the value of the field id
//3. Send a get request to /orgs/:org/repos. Request includes :
//      • Path param org with value cucumber
//4. Verify that value of the id inside the owner object in every response is equal to value from step 2
    @Test
    @DisplayName("Repository owner information")
    public void repoOwnerInfo (){
        Response response = given().
                queryParam("per_page",100).
                pathParam("org","cucumber").
                when().
                get("/orgs/{org}");

        int ownerId = response.jsonPath().getInt("id");

        Response response1 = given().
                pathParam("org","cucumber").
                queryParam("per_page",100).
                when().
                get("/orgs/{org}/repos");
        List<Object> ownerList = response1.jsonPath().getList("owner.id");
//        System.out.println("ownerList = " + ownerList);
        response1.then().assertThat().body("owner.id",everyItem(is(ownerId)));
    }

    //    Ascending order by full_name sort
//1. Send a get request to /orgs/:org/repos. Request includes :
//      • Path param org with value cucumber
//      • Query param sort with value full_name
//2. Verify that all repositories are listed in alphabetical order based on the value of the field name
    @Test
    @DisplayName("Ascending order by full name")
    public void ascOrderByFullName (){
        Response response = given().
                pathParam("org","cucumber").
                queryParam("sort","full_name").
                when().
                get("/orgs/{org}/repos");

        List<String> fullNames = response.jsonPath().getList("full_name");
        List<String> sortedFullNames = new ArrayList<>(fullNames);
        Collections.sort(sortedFullNames);
    }
    //    Descending order by full_name sort
//1. Send a get request to /orgs/:org/repos. Request includes :
// • Path param org with value cucumber
// • Query param sort with value full_name
// • Query param direction with value desc
//2. Verify that all repositories are listed in reverser alphabetical order based on the value of the field name
    @Test
    @DisplayName("Descending order sort by full name")
    public void descOrderByFullName (){
        Response response = given().
                pathParam("org","cucumber").
                queryParam("sort","full_name").
                queryParam("direction","desc").
                when().
                get("/orgs/{org}/repos");

        List<String> fullNames = response.jsonPath().getList("full_name");
        List<String> sortedFullNames = new ArrayList<>(fullNames);
        Collections.sort(sortedFullNames,Collections.reverseOrder());

        assertEquals(sortedFullNames,fullNames);
    }

    @Test
    @DisplayName("Default sort")
    public void defaultSort (){
        Response response = given().
                    pathParam("org","cucumber").
                when().
                    get("/orgs/{org}/repos");

        List<String> dateCreatedLst = response.jsonPath().getList("created_at");
        List<String> dateSortedLst = new ArrayList<>(dateCreatedLst);
        Collections.sort(dateSortedLst);

        assertEquals(dateCreatedLst,dateSortedLst);


    }
}
