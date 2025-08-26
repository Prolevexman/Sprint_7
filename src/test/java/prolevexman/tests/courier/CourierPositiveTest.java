package prolevexman.tests.courier;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import prolevexman.client.CourierClient;
import prolevexman.config.Config;
import prolevexman.data.CourierGenerator;
import prolevexman.model.request.Courier;

import static prolevexman.assertions.CourierAssertions.assertCourierCreation;

public class CourierPositiveTest {
    private CourierClient courierClient = new CourierClient();
    private Courier courier;
    private int courierId;

    @BeforeAll
    public static void setUp() {
        Config.setUp();
    }

    @BeforeEach
    public void createCourier(){
        courier = CourierGenerator.randomCourier();
    }

    @Test
    @DisplayName("Создание курьера | валидные данные | обязательные поля")
    public void testCreateCourierSuccessfully() {
        Response response = courierClient.createCourier(courier);
        assertCourierCreation(response);
    }

    @AfterEach
    public void deleteCourier() {
        if (courierId > 0) {
            courierClient.deleteCourier(courierId);
        }
    }
}
