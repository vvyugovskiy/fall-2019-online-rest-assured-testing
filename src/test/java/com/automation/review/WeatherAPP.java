package com.automation.review;

import io.restassured.response.Response;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static io.restassured.RestAssured.*;

public class WeatherAPP {

    static {
        baseURI = "https://www.metaweather.com/api/location";
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter city name: ");
        String city = scan.nextLine();
        String woeid = getWOEID(city);

        printWeatherInfo(woeid);
    }

    public static String getWOEID(String city) {
        Response response = given().queryParam("query", city).get("/search");
        String woeid = response.jsonPath().getString("woeid");
        System.out.println("WOEID = " + woeid);
        return woeid;
    }

    public static void printWeatherInfo(String woeid) {
        woeid = woeid.replaceAll("\\D", "");  // delete all non-digits [^0-9]
        Response response = get("{woeid}", woeid);

        List<String> weatherStateName = response.jsonPath().getList("consolidated_weather.weather_state_name");
        List<Double> temp = response.jsonPath().getList("consolidated_weather.the_temp");
        List<String> dates = response.jsonPath().getList("consolidated_weather.applicable_date");
//        System.out.println("weatherStateName = " + weatherStateName);
//        System.out.println("temp = " + temp);
//        System.out.println("dates = " + dates);

        for (int i = 0; i < weatherStateName.size(); i++) {
            String date = dates.get(i);
            String tem = new DecimalFormat("#").format(temp.get(i));
            date = LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
            System.out.printf("Date : %s, Weather state : %s, Temperature (C) : %s\n", date, weatherStateName.get(i), tem);
        }

    }


}
