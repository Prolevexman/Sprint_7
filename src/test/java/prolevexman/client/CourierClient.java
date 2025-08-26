package prolevexman.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import prolevexman.model.request.Courier;
import prolevexman.model.response.CourierResponse;

import static io.restassured.RestAssured.given;

public class CourierClient {

    public Response createCourier(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/courier");
    }

    public Response deleteCourier(int id) {
        return given()
                .contentType(ContentType.JSON)
                .body(id)
                .when()
                .delete("/courier/" + id);
    }
}
