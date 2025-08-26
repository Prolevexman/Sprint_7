package prolevexman.assertions;

import io.restassured.response.Response;
import prolevexman.model.response.CourierResponse;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;


public class CourierAssertions {

    public static void assertCourierCreation(Response response) {
        response.then().statusCode(HttpURLConnection.HTTP_CREATED);

        CourierResponse courierResponse = response.as(CourierResponse.class);
        assertTrue(courierResponse.isOk(), () -> "Курьер должен был успешно создасться");
    }
}
