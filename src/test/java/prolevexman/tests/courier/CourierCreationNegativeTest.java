package prolevexman.tests.courier;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import prolevexman.client.CourierClient;
import prolevexman.config.Config;
import prolevexman.data.CourierError;
import prolevexman.data.CourierGenerator;
import prolevexman.model.request.Courier;

import java.util.stream.Stream;

import static prolevexman.assertions.CourierAssertions.assertCourierCreationFail;

public class CourierCreationNegativeTest {
    private CourierClient courierClient = new CourierClient();
    private Courier courier;
    private boolean isCourierCreated;

    @BeforeAll
    public static void setUp() {
        Config.setUp();
    }

    @DisplayName("Создание курьера без обязательных полей")
    @Description("Проверка, что курьера невозможно создать без какого-либо обязательного поля в запросе")
    @ParameterizedTest(name = "{index} => {1}")
    @MethodSource("negativeCourierTests")
    public void testCreateCourierFails(Courier courier, CourierError error) {
        this.courier = courier;
        Response response = courierClient.createCourier(courier);
        isCourierCreated = courierClient.isCourierCreated(response);
        assertCourierCreationFail(response, error.getStatus(), error.getMessage());
    }

    static Stream<Arguments> negativeCourierTests() {
        return Stream.of(
                Arguments.of(
                        CourierGenerator.courierWithoutLogin(),
                        CourierError.EMPTY_LOGIN
                ),
                Arguments.of(
                        CourierGenerator.courierWithoutPassword(),
                        CourierError.EMPTY_PASSWORD
                )
        );
    }

    @DisplayName("Невозможно создать одинаковых курьеров")
    @Description("Проверка, что невозможно создать двух одинаковых курьеров")
    @Test
    public void testCreateDuplicateCourierFails () {
        courier = CourierGenerator.randomCourierAllFields();
        Response response = courierClient.createCourier(courier);
        isCourierCreated = courierClient.isCourierCreated(response);
        Response responseSecond = courierClient.createCourier(courier);
        assertCourierCreationFail(responseSecond, CourierError.DUPLICATE_LOGIN.getStatus(), CourierError.DUPLICATE_LOGIN.getMessage());
    }


    @AfterEach
    public void deleteCourier() {
        if (isCourierCreated) {
            int courierId = courierClient.loginAndGetId(courier);
            if (courierId > 0) {
               courierClient.deleteCourier(courierId);
            }
        }
    }
}
