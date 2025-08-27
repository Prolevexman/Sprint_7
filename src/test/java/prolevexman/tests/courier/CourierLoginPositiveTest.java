package prolevexman.tests.courier;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import prolevexman.client.CourierClient;
import prolevexman.config.Config;
import prolevexman.data.CourierGenerator;
import prolevexman.model.request.Courier;
import prolevexman.model.request.CourierLogin;

import static prolevexman.assertions.CourierAssertions.assertCourierLogin;

public class CourierLoginPositiveTest {
    private CourierClient courierClient = new CourierClient();
    private CourierLogin courierLogin;
    private Courier courier;

    @BeforeAll
    public static void setUp() {
        Config.setUp();
    }

    @BeforeEach
    public void createCourier() {
        courier = CourierGenerator.randomCourierAllFields();
        courierClient.createCourier(courier);
        courierLogin = CourierLogin.getFrom(courier);
    }

    @DisplayName("Успешная авторизация")
    @Description("Проверка успешной авторизации с валидными полями")
    @Test
    public void testSuccessfulLogin() {
        Response response = courierClient.loginCourier(courierLogin);
        assertCourierLogin(response);
    }

    @AfterEach
    public void deleteCourier() {
        int courierId = courierClient.loginAndGetId(courier);
        if (courierId > 0) {
            courierClient.deleteCourier(courierId);
        }
    }

}
