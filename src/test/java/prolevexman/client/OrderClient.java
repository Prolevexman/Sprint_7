package prolevexman.client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import prolevexman.model.request.Order;
import prolevexman.model.request.OrdersList;

import static io.restassured.RestAssured.*;

public class OrderClient {

    @Step("Отправка POST запроса на /orders")
    public Response createOrder(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/orders");
    }

    @Step("Отправка GET запроса на /orders")
    public Response getOrdersList() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/orders");
    }
}
