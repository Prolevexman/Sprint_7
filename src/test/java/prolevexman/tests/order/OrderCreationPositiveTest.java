package prolevexman.tests.order;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import prolevexman.client.OrderClient;
import prolevexman.config.Config;
import prolevexman.data.OrderColor;
import prolevexman.data.OrderGenerator;
import prolevexman.model.request.Order;

import java.util.List;
import java.util.stream.Stream;

import static prolevexman.assertions.OrderAssertions.assertOrderCreation;

public class OrderCreationPositiveTest {

    private OrderClient orderClient = new OrderClient();

    @BeforeAll
    public static void setUp() {
        Config.setUp();
    }

    @DisplayName("Создание заказа | валидные данные | разные цвета")
    @Description("Проверка, что заказ возможно создать с любым доступным цветом, обоими цветами и без цвета")
    @ParameterizedTest(name = "Тест заказа для цвета {0}")
    @MethodSource("positiveOrderTests")
    public void testCreateOrderSuccessfully(Order order) {
        Response response = orderClient.createOrder(order);
        assertOrderCreation(response);
    }

    static Stream<Arguments> positiveOrderTests() {
        return Stream.of(
                Arguments.of(
                        OrderGenerator.randomOrderWithOneColor(OrderColor.GREY.getColor())
                ),
                Arguments.of(
                        OrderGenerator.randomOrderWithOneColor(OrderColor.BLACK.getColor())
                ),
                Arguments.of(
                        OrderGenerator.randomOrderWithColors(List.of(OrderColor.BLACK.getColor(), OrderColor.GREY.getColor()))
                ),
                Arguments.of(
                        OrderGenerator.randomOrderWithoutColors()
                )
        );
    }
}
