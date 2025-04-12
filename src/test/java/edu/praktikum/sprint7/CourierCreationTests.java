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
import static edu.praktikum.sprint7.utils.Utils.randomString;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreationTests {
    private final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    private CourierClient courierClient;
    private int id;
    private Courier courier;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        courierClient = new CourierClient();
        courier = randomCourier();
    }

    @Test
    public void CreateValidCourierReturnsOKTrue201() {
        Response response = courierClient.create(courier);
        response
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    // Тест на текст ошибки падает, реализован другой ответ
    public void CreateSimilarCourierReturnsError409() {
        courierClient.create(courier);
        Response duplicateResponse = courierClient.create(courier);
        duplicateResponse
                .then()
                .assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется"));

    }

    @Test
    //Тест на статус ошибки падает, по документации поле обязательное, фактически - нет
    public void CreateCourierWithoutFirstNameReturnsError400() {
        Courier courierWithoutFirstName = new Courier()
                .setLogin(randomString())
                .setPassword(randomString());
        Response response = courierClient.create(courierWithoutFirstName);
        response
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void CreateCourierWithoutLoginReturnsError400() {
        Courier courierWithoutLogin = new Courier()
                .setFirstName(randomString())
                .setPassword(randomString());
        Response response = courierClient.create(courierWithoutLogin);
        response
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void CreateCourierWithoutPasswordReturnsError400() {
        Courier courierWithoutPassword = new Courier()
                .setFirstName(randomString())
                .setLogin(randomString());
        Response response = courierClient.create(courierWithoutPassword);
        response
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearTest() {
        Response loginResponse = courierClient.login(CourierCreds.credsFromCourier(courier));
        id = loginResponse.as(CourierLoginResponse.class).getId();
        courierClient.delete(id);
    }
}
