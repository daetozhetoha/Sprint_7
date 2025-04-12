package edu.praktikum.sprint7;

import edu.praktikum.sprint7.clients.OrderClient;
import edu.praktikum.sprint7.models.Order;
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

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        orderclient = new OrderClient();
        order = scooterOrderOnlyRequiredFields();
        orderclient.create(order);
        order = blackScooterOrder();
        orderclient.create(order);
        order = greyScooterOrder();
        orderclient.create(order);
    }

    @Test
    public void GetOrdersReturnsListOfOrders() {
        Response response = orderclient.get();
        response
                .then()
                .statusCode(200)
                .and()
                .body("orders", isA(List.class))
                .and()
                .body("orders.size()", greaterThan(2))
                .and()
                .body("pageInfo", notNullValue())
                .body("availableStations", isA(List.class));
    }
}
