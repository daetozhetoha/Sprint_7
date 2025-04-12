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

    @Test
    public void LoginCourierWithWrongPasswordReturnsError404() {
        Response wrongPassResponse = courierClient.login(CourierCreds.credsWithWrongPassword(courier));
        wrongPassResponse
                .then()
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void LoginCourierWithWrongLoginReturnsError404() {
        Response wrongLoginResponse = courierClient.login(CourierCreds.credsWithWrongLogin(courier));
        wrongLoginResponse
                .then()
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void LoginCourierWithNullPasswordReturnsError400() {
        Response nullPassResponse = courierClient.login(CourierCreds.credsWithNullPassword(courier));
        nullPassResponse
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void LoginWithNullLoginReturnsError400() {
        Response nullLoginResponse = courierClient.login(CourierCreds.credsWithNullLogin(courier));
        nullLoginResponse
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @After
    public void tearTest() {
        id = loginResponse.as(CourierLoginResponse.class).getId();
        courierClient.delete(id);
    }
}
