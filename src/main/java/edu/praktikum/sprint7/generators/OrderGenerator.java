package edu.praktikum.sprint7.generators;

import edu.praktikum.sprint7.models.Order;

import static edu.praktikum.sprint7.utils.Utils.*;

public class OrderGenerator {

    public static Order scooterOrderOnlyRequiredFields() {
        return new Order()
                .setCustomerFirstName(randomString())
                .setCustomerLastName(randomString())
                .setCustomerAddress(randomString())
                .setCustomerMetroStation(randomMetroStation())
                .setCustomerPhone(getRandomPhone())
                .setRentTime(randomRentTime())
                .setDeliveryDate(getFutureDate(5))
                .setComment(randomString());
    }

    public static Order blackScooterOrder() {
        return new Order()
                .setCustomerFirstName(randomString())
                .setCustomerLastName(randomString())
                .setCustomerAddress(randomString())
                .setCustomerMetroStation(randomMetroStation())
                .setCustomerPhone(getRandomPhone())
                .setRentTime(randomRentTime())
                .setDeliveryDate(getFutureDate(5))
                .setComment(randomString())
                .setColor(new String[]{"BLACK"});
    }

    public static Order greyScooterOrder() {
        return new Order()
                .setCustomerFirstName(randomString())
                .setCustomerLastName(randomString())
                .setCustomerAddress(randomString())
                .setCustomerMetroStation(randomMetroStation())
                .setCustomerPhone(getRandomPhone())
                .setRentTime(randomRentTime())
                .setDeliveryDate(getFutureDate(5))
                .setComment(randomString())
                .setColor(new String[]{"GREY"});
    }

}
