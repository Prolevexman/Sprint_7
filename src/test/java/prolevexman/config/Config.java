package prolevexman.config;

import io.restassured.RestAssured;

public class Config {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    public static final String BASE_API = "/api/v1";

    public static void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = BASE_API;
    }
}
