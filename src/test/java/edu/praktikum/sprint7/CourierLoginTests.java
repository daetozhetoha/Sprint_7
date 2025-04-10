package edu.praktikum.sprint7;

import edu.praktikum.sprint7.clients.CourierClient;
import edu.praktikum.sprint7.models.Courier;
import edu.praktikum.sprint7.models.CourierCreds;
import edu.praktikum.sprint7.models.CourierLoginResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static edu.praktikum.sprint7.generators.CourierGenerator.randomCourier;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTests {
    private final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    private CourierClient courierClient;
    private int id;
    private Courier courier;
    private Response loginResponse;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        courierClient = new CourierClient();
        courier = randomCourier();
        courierClient.create(courier);
        loginResponse = courierClient.login(CourierCreds.credsFromCourier(courier));
    }

    @Test
    public void ExistingCourierLoginReturnsOk200() {
        loginResponse
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @After
    public void tearTest() {
        id = loginResponse.as(CourierLoginResponse.class).getId();
        courierClient.delete(id);
    }
}
