package prolevexman.assertions;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import prolevexman.model.response.OrderResponse;
import prolevexman.model.response.OrdersListResponse;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderAssertions {

    @Step("Проверка статуса и наличия track в теле ответа")
    public static void assertOrderCreation(Response response) {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_CREATED);

        OrderResponse orderResponse = response.as(OrderResponse.class);
        assertNotNull(orderResponse.getTrack(), () -> "Ответ должен содержать track");
    }

    @Step("Проверка статуса и наличия списка заказов в теле ответа")
    public static void assertGetOrdersList(Response response) {
        response
                .then()
                .statusCode(HttpURLConnection.HTTP_OK);

        OrdersListResponse ordersListResponse = response.as(OrdersListResponse.class);
        assertNotNull(ordersListResponse.getOrders(), () -> "Список заказов не должен быть null");
    }


}
