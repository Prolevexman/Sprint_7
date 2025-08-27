package prolevexman.assertions;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import prolevexman.model.response.CourierLoginResponse;
import prolevexman.model.response.CourierResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class CourierAssertions {

    @Step("Проверка статуса и наличия 'ok' в теле ответа")
    public static void assertCourierCreation(Response response) {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_CREATED);

        CourierResponse courierResponse = response.as(CourierResponse.class);
        assertTrue(courierResponse.isOk(), () -> "Курьер должен был успешно создасться");
    }

    @Step("Проверка статуса и сообщения об ошибке")
    public static void assertCourierCreationFail(Response response, int expectedStatusCode, String expectedMessage) {
        response
                .then()
                .statusCode(expectedStatusCode);

        CourierResponse courierResponse = response.as(CourierResponse.class);
        assertEquals(expectedMessage, courierResponse.getMessage(), () -> "Сообщение об ошибке не совпадает с ожидаемым");
    }

    @Step("Проверка статуса и наличия id в теле ответа")
    public static void assertCourierLogin(Response response) {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_OK);

        CourierLoginResponse courierLoginResponse = response.as(CourierLoginResponse.class);
        assertNotNull(courierLoginResponse.getId(), () -> "В ответе нет поля Id");
    }

    @Step("Проверка статуса и сообщения об ошибке")
    public static void assertCourierLoginWithInvalidData(Response response, int expectedStatusCode, String expectedMessage) {
        response
                .then()
                .statusCode(expectedStatusCode);

        CourierLoginResponse courierLoginResponse = response.as(CourierLoginResponse.class);
        assertEquals(expectedMessage, courierLoginResponse.getMessage(), () -> "Сообщение об ошибке не совпадает с ожидаемым");
    }
}
