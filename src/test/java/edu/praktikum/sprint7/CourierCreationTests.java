package edu.praktikum.sprint7;

import edu.praktikum.sprint7.clients.CourierClient;
import edu.praktikum.sprint7.models.Courier;
import edu.praktikum.sprint7.models.CourierCreds;
import edu.praktikum.sprint7.models.CourierLoginResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
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
    @DisplayName("Check courier creation with valid credentials")
    @Description("Checking status code and body response for valid credentials")
    public void CreateValidCourierReturnsOKTrue201() {
        Response response = sendPostRequestV1Courier();
        compareStatus201WithResponse(response);
        compareParameterOkWithResponse(response);
    }

    // Тест на текст ошибки падает, реализован другой ответ
    @Test
    @DisplayName("Check courier creation with existing login")
    @Description("Checking status code and body response for creation a courier with existing login in database")
    public void CreateSimilarCourierReturnsError409() {
        createCourier();
        Response duplicateResponse = sendPostRequestV1Courier();
        compareStatus409WithResponse(duplicateResponse);
        compareParameterMessageFor409WithResponse(duplicateResponse);
    }

    //Тест на статус ошибки падает, по документации поле обязательное, фактически - нет
    @Test
    @DisplayName("Check courier creation without firstname")
    @Description("Checking status code and body response for creation a courier without firstname")
    public void CreateCourierWithoutFirstnameReturnsError400() {
        Response response = sendPostRequestV1CouriersWithoutFirstname();
        compareStatus400WithResponse(response);
        compareParameterMessageFor400WithResponse(response);
    }

    @Test
    @DisplayName("Check courier creation without login")
    @Description("Checking status code and body response for creation a courier without login")
    public void CreateCourierWithoutLoginReturnsError400() {
        Response response = sendPostRequestV1CourierWithoutLogin();
        compareStatus400WithResponse(response);
        compareParameterMessageFor400WithResponse(response);
    }

    @Test
    @DisplayName("Check courier creation without password")
    @Description("Checking status code and body response for creation a courier without password")
    public void CreateCourierWithoutPasswordReturnsError400() {
        Response response = sendPostRequestV1CourierWithoutPassword();
        compareStatus400WithResponse(response);
        compareParameterMessageFor400WithResponse(response);
    }

    @Step("Send POST request to /api/v1/courier with valid creds")
    public Response sendPostRequestV1Courier() {
        Response response = courierClient.create(courier);
        return response;
    }

    @Step("Compare status code 201 with response")
    public void compareStatus201WithResponse(Response response) {
        response.then().assertThat().statusCode(201);
    }

    @Step("Compare body parameter 'ok' for 201 status with response")
    public void compareParameterOkWithResponse(Response response) {
        response.then().assertThat().body("ok", equalTo(true));
    }

    @Step("Create courier")
    public void createCourier() {
        courierClient.create(courier);
    }

    @Step("Compare status code 201 with response")
    public void compareStatus409WithResponse(Response duplicateResponse) {
        duplicateResponse.then().assertThat().statusCode(409);
    }

    @Step("Compare body parameter 'message' for 409 status with response")
    public void compareParameterMessageFor409WithResponse(Response response) {
        response.then().assertThat().body("message", equalTo("Этот логин уже используется"));
    }

    @Step("Send POST request to /api/v1/courier without firstname")
    public Response sendPostRequestV1CouriersWithoutFirstname() {
        Courier courierWithoutFirstname = new Courier()
                .setLogin(randomString())
                .setPassword(randomString());
        Response response = courierClient.create(courierWithoutFirstname);
        return response;
    }

    @Step("Compare status code 400 with response")
    public void compareStatus400WithResponse(Response response) {
        response.then().assertThat().statusCode(400);
    }

    @Step("Compare body parameter 'message' for 400 status with response")
    public void compareParameterMessageFor400WithResponse(Response response) {
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    @Step("Send POST request to /api/v1/courier without password")
    public Response sendPostRequestV1CourierWithoutPassword() {
        Courier courierWithoutPassword = new Courier()
                .setFirstName(randomString())
                .setLogin(randomString());
        Response response = courierClient.create(courierWithoutPassword);
        return response;
    }

    @Step("Send POST request to /api/v1/courier without firstname")
    public Response sendPostRequestV1CourierWithoutLogin() {
        Courier courierWithoutLogin = new Courier()
                .setFirstName(randomString())
                .setPassword(randomString());
        Response response = courierClient.create(courierWithoutLogin);
        return response;
    }

    @After
    public void tearTest() {
        Response loginResponse = courierClient.login(CourierCreds.credsFromCourier(courier));
        id = loginResponse.as(CourierLoginResponse.class).getId();
        courierClient.delete(id);
    }
}
