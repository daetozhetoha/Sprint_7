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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import java.util.Arrays;
import java.util.Collection;
import static edu.praktikum.sprint7.utils.Utils.*;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTests {
    private final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    private OrderClient orderclient;
    private final String[] colors;

    public OrderCreationTests(String[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "{index}: colors={0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {null}
        });
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        orderclient = new OrderClient();
    }

    @Test
    @DisplayName("Check scooter order with parameterized colors")
    @Description("Checking status code and body response for ordering scooter with different colors")
    public void testScooterOrder() {
        Response response = sendPostRequestV1Orders(colors);
        compareStatus201WithResponse(response);
        checkParameterTrackFor201NotNull(response);
    }

    @Step("Send POST request to /api/v1/orders with color: {0}")
    public Response sendPostRequestV1Orders(String[] colors) {
        Order order = new Order()
                .setCustomerFirstName(randomString())
                .setCustomerLastName(randomString())
                .setCustomerAddress(randomString())
                .setCustomerMetroStation(randomMetroStation())
                .setCustomerPhone(getRandomPhone())
                .setRentTime(randomRentTime())
                .setDeliveryDate(getFutureDate(5))
                .setComment(randomString());

        if (colors != null) {
            order.setColor(colors);
        }

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
}
