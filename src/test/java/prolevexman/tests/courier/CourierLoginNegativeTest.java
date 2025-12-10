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
import prolevexman.data.LoginError;
import prolevexman.model.request.Courier;
import prolevexman.model.request.CourierLogin;

import java.util.Random;
import java.util.stream.Stream;

import static prolevexman.assertions.CourierAssertions.assertCourierLoginWithInvalidData;

public class CourierLoginNegativeTest {
    private CourierClient courierClient = new CourierClient();
    private CourierLogin courierLogin;
    private Courier courier;
    private boolean isCourierCreated;

    @BeforeAll
    public static void setUp() {
        Config.setUp();
    }

    @BeforeEach
    public void createCourier() {
        courier = CourierGenerator.randomCourierAllFields();
        courierLogin = CourierLogin.getFrom(courier);
    }

    @DisplayName("Авторизация с одним полем невозможна")
    @Description("Проверка, что невозможно авторизоваться с одним обязательным полем в запросе")
    @ParameterizedTest(name = "{index} => {1}")
    @MethodSource("negativeLoginTests")
    public void testLoginWithMissingField(CourierLogin courierLogin, LoginError error) {
        Response response = courierClient.loginCourier(courierLogin);
        assertCourierLoginWithInvalidData(response, error.getStatus(), error.getMessage());
    }

    public static Stream<Arguments> negativeLoginTests() {
        Courier courier = CourierGenerator.randomCourierAllFields();
        CourierLogin courierLogin = CourierLogin.getFrom(courier);
        return Stream.of(
                Arguments.of(
                        new CourierLogin(null, courierLogin.getPassword()), LoginError.EMPTY_LOGIN
                ),
                Arguments.of(
                        new CourierLogin(courierLogin.getLogin(), null), LoginError.EMPTY_PASSWORD
                )
        );
    }

    @DisplayName("Авторизация с несуществующей учетной записью невозможна")
    @Description("Проверка, что невозможно авторизоваться с несуществующими в системе учетными данными")
    @Test
    public void testLoginWithNonExistingCredentials() {
        Response response = courierClient.loginCourier(courierLogin);
        assertCourierLoginWithInvalidData(response, LoginError.NONEXISTING_CREDENTIALS.getStatus(), LoginError.NONEXISTING_CREDENTIALS.getMessage());
    }

    @DisplayName("Авторизация с некорректным логином или паролем невозможна")
    @Description("Проверка, что невозможно авторизоваться в существующую учетную запись с некоректным логином или паролем")
    @ParameterizedTest(name = "{index} => {1}")
    @MethodSource("invalidLoginData")
    public void testLoginWithInvalidCredentials(Courier courier, CourierLogin courierLogin, LoginError error) {
        this.courier = courier;
        Response response = courierClient.createCourier(courier);
        isCourierCreated = courierClient.isCourierCreated(response);

        Response responseLogin = courierClient.loginCourier(courierLogin);
        assertCourierLoginWithInvalidData(responseLogin, error.getStatus(), error.getMessage());
    }

    public static Stream<Arguments> invalidLoginData() {
        Courier courier = CourierGenerator.randomCourierAllFields();
        CourierLogin courierValidLogin = CourierLogin.getFrom(courier);
        Random random = new Random();
        int randomNum = random.nextInt(100);
        return Stream.of(
                Arguments.of(
                        courier,
                        new CourierLogin(courierValidLogin.getLogin() + randomNum, courierValidLogin.getPassword()),
                        LoginError.NONEXISTING_CREDENTIALS
                ),
                Arguments.of(courier,
                        new CourierLogin(courierValidLogin.getLogin(), courierValidLogin.getPassword() + randomNum),
                        LoginError.NONEXISTING_CREDENTIALS
                )
        );
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
