package edu.praktikum.sprint7;

import edu.praktikum.sprint7.clients.OrderClient;
import edu.praktikum.sprint7.models.Order;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static edu.praktikum.sprint7.generators.OrderGenerator.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isA;

public class GetOrdersTests {
    private final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    private OrderClient orderclient;
    private Order order;
    private String color;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        orderclient = new OrderClient();
        order = scooterOrder(color);
        orderclient.create(order);
        orderclient.create(order);
        orderclient.create(order);
    }

    @Test
    @DisplayName("Check getting list of orders")
    @Description("Checking status code and body response structure")
    public void getOrdersReturnsListOfOrders() {
        Response response = sendPostRequestV1Orders();
        compareStatus200WithResponse(response);
        checkThatOrdersParameterIsArray(response);
        checkThatOrdersSizeMoreThan2(response);
        checkThatIdInFirstOrderInfoNotNull(response);
        checkThatIdInSecondOrderInfoNotNull(response);
        checkThatIdInThirdOrderInfoNotNull(response);
        checkThatTrackInFirstOrderInfoNotNull(response);
        checkThatTrackInSecondOrderInfoNotNull(response);
        checkThatTrackInThirdOrderInfoNotNull(response);
        checkThatPageInfoNotNull(response);
        checkThatAvailableStationsParameterIsArray(response);
    }

    @Step("Send GET request to api/v1/orders")
    public Response sendPostRequestV1Orders() {
        Response response = orderclient.get();
        return response;
    }

    @Step("Compare status code 200 with response")
    public void compareStatus200WithResponse(Response response) {
        response.then().assertThat().statusCode(200);
    }

    @Step("Check that 'orders' is an array")
    public void checkThatOrdersParameterIsArray(Response response) {
        response.then().assertThat().body("orders", isA(List.class));
    }

    @Step("Check that 'orders' size > 2")
    public void checkThatOrdersSizeMoreThan2(Response response) {
        response.then().assertThat().body("orders.size()", greaterThan(2));
    }

    @Step("Check that 'pageInfo' not null")
    public void checkThatIdInFirstOrderInfoNotNull(Response response) {
        response.then().assertThat().body("orders.id[0]", notNullValue());
    }
    @Step("Check that 'pageInfo' not null")
    public void checkThatIdInSecondOrderInfoNotNull(Response response) {
        response.then().assertThat().body("orders.id[1]", notNullValue());
    }

    @Step("Check that 'pageInfo' not null")
    public void checkThatIdInThirdOrderInfoNotNull(Response response) {
        response.then().assertThat().body("orders.id[2]", notNullValue());
    }

    @Step("Check that 'pageInfo' not null")
    public void checkThatTrackInFirstOrderInfoNotNull(Response response) {
        response.then().assertThat().body("orders.track[0]", notNullValue());
    }
    @Step("Check that 'pageInfo' not null")
    public void checkThatTrackInSecondOrderInfoNotNull(Response response) {
        response.then().assertThat().body("orders.track[1]", notNullValue());
    }

    @Step("Check that 'pageInfo' not null")
    public void checkThatTrackInThirdOrderInfoNotNull(Response response) {
        response.then().assertThat().body("orders.track[2]", notNullValue());
    }

    @Step("Check that 'pageInfo' not null")
    public void checkThatPageInfoNotNull(Response response) {
        response.then().assertThat().body("pageInfo", notNullValue());
    }

    @Step("Check that 'availableStations' is an array")
    public void checkThatAvailableStationsParameterIsArray(Response response) {
        response.then().assertThat().body("availableStations", isA(List.class));
    }
}
