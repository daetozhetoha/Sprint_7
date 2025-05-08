package edu.praktikum.sprint7.generators;

import edu.praktikum.sprint7.models.Order;

import static edu.praktikum.sprint7.utils.Utils.*;

public class OrderGenerator {

    public static Order scooterOrder(String color) {
        return new Order()
                .setCustomerFirstName(randomString())
                .setCustomerLastName(randomString())
                .setCustomerAddress(randomString())
                .setCustomerMetroStation(randomMetroStation())
                .setCustomerPhone(getRandomPhone())
                .setRentTime(randomRentTime())
                .setDeliveryDate(getFutureDate(5))
                .setComment(randomString())
                .setColor(new String[]{color});
    }
}
