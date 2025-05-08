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
    }

    @Test
    @DisplayName("Check courier login with valid credentials")
    @Description("Checking status code and body response for login with valid login and password")
    public void existingCourierLoginReturnsOk200() {
        Response loginResponse = sendPostRequestV1CourierLogin();
        compareStatus200WithResponse(loginResponse);
        compareParameterIdFor200WithResponse(loginResponse);
    }

    @Test
    @DisplayName("Check courier login with wrong password")
    @Description("Checking status code and body response for login with wrong password")
    public void loginCourierWithWrongPasswordReturnsError404() {
        Response wrongPassResponse = sendPostRequestV1CourierLoginWithWrongPassword();
        compareStatus404WithResponse(wrongPassResponse);
        compareParameterMessageFor404WithResponse(wrongPassResponse);
    }

    @Test
    @DisplayName("Check courier login with wrong login")
    @Description("Checking status code and body response for login with wrong login")
    public void loginCourierWithWrongLoginReturnsError404() {
        Response wrongLoginResponse = sendPostRequestV1CourierLoginWithWrongLogin();
        compareStatus404WithResponse(wrongLoginResponse);
        compareParameterMessageFor404WithResponse(wrongLoginResponse);
    }

    @Test
    @DisplayName("Check courier login without password")
    @Description("Checking status code and body response for login without password")
    public void loginCourierWithNullPasswordReturnsError400() {
        Response nullPassResponse = sendPostRequestV1CourierLoginWithNullPassword();
        compareStatus400WithResponse(nullPassResponse);
        compareParameterMessageFor400WithResponse(nullPassResponse);
    }

    @Test
    @DisplayName("Check courier login without login")
    @Description("Checking status code and body response for login without login")
    public void loginWithNullLoginReturnsError400() {
        Response nullLoginResponse = sendPostRequestV1CourierLoginWithNullLogin();
        compareStatus400WithResponse(nullLoginResponse);
        compareParameterMessageFor400WithResponse(nullLoginResponse);
    }

    @Step("Send POST request to api/v1/courier/login with valid creds")
    public Response sendPostRequestV1CourierLogin() {
        Response loginResponse = courierClient.login(CourierCreds.credsFromCourier(courier));
        return loginResponse;
    }

    @Step("Compare status code 200 with response")
    public void compareStatus200WithResponse(Response loginResponse) {
        loginResponse.then().assertThat().statusCode(200);
    }

    @Step("Compare body parameter 'id' for 200 status with response")
    public void compareParameterIdFor200WithResponse(Response response) {
        response.then().assertThat().body("id", notNullValue());
    }

    @Step("Send POST request to api/v1/courier/login with wrong password")
    public Response sendPostRequestV1CourierLoginWithWrongPassword() {
        Response wrongPassResponse = courierClient.login(CourierCreds.credsWithWrongPassword(courier));
        return wrongPassResponse;
    }

    @Step("Compare status code 404 with response")
    public void compareStatus404WithResponse(Response error404Response) {
        error404Response.then().assertThat().statusCode(404);
    }

    @Step("Compare body parameter 'message' for 404 status with response")
    public void compareParameterMessageFor404WithResponse(Response error404Response) {
        error404Response.then().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Step("Send POST request to api/v1/courier/login with wrong login")
    public Response sendPostRequestV1CourierLoginWithWrongLogin() {
        Response wrongLoginResponse = courierClient.login(CourierCreds.credsWithWrongLogin(courier));
        return wrongLoginResponse;
    }

    @Step("Send POST request to api/v1/courier/login with wrong password")
    public Response sendPostRequestV1CourierLoginWithNullLogin() {
        Response nullPassResponse = courierClient.login(CourierCreds.credsWithNullLogin(courier));
        return nullPassResponse;
    }

    @Step("Send POST request to api/v1/courier/login with wrong password")
    public Response sendPostRequestV1CourierLoginWithNullPassword() {
        Response nullPassResponse = courierClient.login(CourierCreds.credsWithNullPassword(courier));
        return nullPassResponse;
    }

    @Step("Compare status code 400 with response")
    public void compareStatus400WithResponse(Response error400Response) {
        error400Response.then().assertThat().statusCode(400);
    }

    @Step("Compare body parameter 'message' for 400 status with response")
    public void compareParameterMessageFor400WithResponse(Response error400Response) {
        error400Response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @After
    public void tearTest() {
        loginResponse = courierClient.login(CourierCreds.credsFromCourier(courier));
        id = loginResponse.as(CourierLoginResponse.class).getId();
        courierClient.delete(id);
    }
}
