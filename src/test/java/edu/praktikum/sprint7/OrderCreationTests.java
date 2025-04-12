package edu.praktikum.sprint7;

import edu.praktikum.sprint7.clients.OrderClient;
import edu.praktikum.sprint7.models.Order;
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
    public void BlackScooterOrdersReturns201() {
        order = blackScooterOrder();
        Response response = orderclient.create(order);

        response
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }

    @Test
    public void GreyScooterOrdersReturns201() {
        order = greyScooterOrder();
        Response response = orderclient.create(order);

        response
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }

    @Test
    public void BothColorsScootersOrderReturns201() {
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
        response
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }

    @Test
    public void OrderScooterWithoutColorReturns201() {
        order = scooterOrderOnlyRequiredFields();
        Response response = orderclient.create(order);
        response
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }

}
