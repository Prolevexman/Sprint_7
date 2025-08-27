package prolevexman.tests.courier;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import prolevexman.client.CourierClient;
import prolevexman.config.Config;
import prolevexman.data.CourierGenerator;
import prolevexman.model.request.Courier;


import java.util.stream.Stream;

import static prolevexman.assertions.CourierAssertions.assertCourierCreation;

public class CourierCreationPositiveTest {
    private CourierClient courierClient = new CourierClient();
    private Courier courier;
    private int courierId;

    @BeforeAll
    public static void setUp() {
        Config.setUp();
    }

    @DisplayName("Создание курьера | валидные данные")
    @Description("Проверка, что курьер успешно создается при использовани валидных данных и обязательных полей")
    @ParameterizedTest(name = "Тест логина для {0}")
    @MethodSource("positiveCourierTests")
    public void testCreateCourierSuccessfully(Courier courier) {
        this.courier = courier;
        Response response = courierClient.createCourier(courier);
        assertCourierCreation(response);
    }

    static Stream<Arguments> positiveCourierTests() {
        return Stream.of(
                Arguments.of(
                        CourierGenerator.randomCourierAllFields()
                ),
                Arguments.of(
                        CourierGenerator.courierWithoutFirstName()
                )
        );
    }

    @AfterEach
    public void deleteCourier() {
        courierId = courierClient.loginAndGetId(courier);
        if (courierId > 0) {
            courierClient.deleteCourier(courierId);
        }
    }
}
