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


import static edu.praktikum.sprint7.generators.OrderGenerator.*;
import static edu.praktikum.sprint7.utils.Utils.*;
import static edu.praktikum.sprint7.utils.Utils.randomString;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderCreationTests {
    private final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    private OrderClient orderclient;
    private Order order;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        orderclient = new OrderClient();
    }

    @Test
    @DisplayName("Check black scooter order")
    @Description("Checking status code and body response for ordering black scooter")
    public void BlackScooterOrdersReturns201() {
        Response response = sendPostRequestV1OrdersForBlackScooter();
        compareStatus201WithResponse(response);
        checkParameterTrackFor201NotNull(response);
    }

    @Test
    @DisplayName("Check grey scooter order")
    @Description("Checking status code and body response for ordering grey scooter")
    public void GreyScooterOrdersReturns201() {
        Response response = sendPostRequestV1OrdersForGreyScooter();
        compareStatus201WithResponse(response);
        checkParameterTrackFor201NotNull(response);
    }

    @Test
    @DisplayName("Check black and grey scooter order")
    @Description("Checking status code and body response for ordering black and grey scooter")
    public void BothColorsScootersOrderReturns201() {
        Response response = sendPostRequestV1OrdersForBothScooterColors();
        compareStatus201WithResponse(response);
        checkParameterTrackFor201NotNull(response);
    }

    @Test
    @DisplayName("Check scooter order without color choosing")
    @Description("Checking status code and body response for ordering scooter without color choosing")
    public void OrderScooterWithoutColorReturns201() {
        Response response = sendPostRequestV1OrdersForScooterWithoutColor();
        compareStatus201WithResponse(response);
        checkParameterTrackFor201NotNull(response);
    }

    @Step("Send POST request to /api/v1/orders for black scooter")
    public Response sendPostRequestV1OrdersForBlackScooter() {
        order = blackScooterOrder();
        Response response = orderclient.create(order);
        return response;
    }

    @Step("Compare status code 201 with response")
    public void compareStatus201WithResponse(Response response) {
        response.then().assertThat().statusCode(201);
    }

    @Step("Check that body parameter 'track' for 201 status not null")
    public void checkParameterTrackFor201NotNull(Response response) {
        response.then().assertThat().body("track", notNullValue());
    }

    @Step("Send POST request to /api/v1/orders for grey scooter")
    public Response sendPostRequestV1OrdersForGreyScooter() {
        order = greyScooterOrder();
        Response response = orderclient.create(order);
        return response;
    }

    @Step("Send POST request to /api/v1/orders for grey scooter")
    public Response sendPostRequestV1OrdersForBothScooterColors() {
        Order bothColorOrder = new Order()
                .setCustomerFirstName(randomString())
                .setCustomerLastName(randomString())
                .setCustomerAddress(randomString())
                .setCustomerMetroStation(randomMetroStation())
                .setCustomerPhone(getRandomPhone())
                .setRentTime(randomRentTime())
                .setDeliveryDate(getFutureDate(5))
                .setComment(randomString())
                .setColor(new String[]{"BLACK", "GREY"});

        Response response = orderclient.create(bothColorOrder);
        return response;
    }

    @Step("Send POST request to /api/v1/orders for black scooter")
    public Response sendPostRequestV1OrdersForScooterWithoutColor() {
        order = scooterOrderOnlyRequiredFields();
        Response response = orderclient.create(order);
        return response;
    }

}
