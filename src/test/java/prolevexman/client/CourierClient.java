package prolevexman.client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import prolevexman.model.request.Courier;
import prolevexman.model.request.CourierDelete;
import prolevexman.model.request.CourierLogin;
import prolevexman.model.response.CourierLoginResponse;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;

public class CourierClient {

    @Step("Отправка POST запроса на /courier")
    public Response createCourier(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/courier");
    }

    @Step("Отправка POST запроса на /courier/login")
    public Response loginCourier(CourierLogin courierLogin) {
        return given()
                .contentType(ContentType.JSON)
                .body(courierLogin)
                .when()
                .post("/courier/login");
    }

    @Step("Отправка DELETE запроса на /courier/:id")
    public Response deleteCourier(int id) {
        CourierDelete courierDelete = new CourierDelete(id);
        return given()
                .contentType(ContentType.JSON)
                .body(courierDelete)
                .when()
                .delete("/courier/" + id);
    }

    @Step("Получение id курьера")
    public int loginAndGetId(Courier courier) {
        CourierLogin courierLogin = CourierLogin.getFrom(courier);
        Response response = loginCourier(courierLogin);

        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            return 0;
        }

        CourierLoginResponse loginResponse = response.as(CourierLoginResponse.class);
        return loginResponse.getId();
    }

    @Step("Проверка создания курьера")
    public boolean isCourierCreated(Response response) {
        return response.statusCode() == HttpURLConnection.HTTP_CREATED;
    }

}
