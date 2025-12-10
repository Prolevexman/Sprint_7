package prolevexman.tests.order;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import prolevexman.client.OrderClient;
import prolevexman.config.Config;

import static prolevexman.assertions.OrderAssertions.assertGetOrdersList;

public class GetOrdersListTest {

    private OrderClient orderClient = new OrderClient();

    @BeforeAll
    public static void setUp() {
        Config.setUp();
    }

    @DisplayName("Получение списка заказов")
    @Description("поверка, что GET запос успешно возвращает список заказов")
    @Test
    public void testOrdersListIsReturned() {
        Response response = orderClient.getOrdersList();
        assertGetOrdersList(response);
    }
}
