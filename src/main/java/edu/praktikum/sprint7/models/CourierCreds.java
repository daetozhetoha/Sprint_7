package edu.praktikum.sprint7.models;

import edu.praktikum.sprint7.utils.Utils;

public class CourierCreds {
    private String login;

    private String password;

    private CourierCreds(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCreds credsFromCourier(Courier courier) {
        return new CourierCreds(courier.getLogin(), courier.getPassword());
    }

    public static CourierCreds credsWithWrongPassword(Courier courier) {
        return new CourierCreds(courier.getLogin(), Utils.randomString());
    }

    public static CourierCreds credsWithWrongLogin(Courier courier) {
        return new CourierCreds(Utils.randomString(), courier.getPassword());
    }

    public static CourierCreds credsWithNullPassword(Courier courier) {
        return new CourierCreds(courier.getLogin(), "");
    }

    public static CourierCreds credsWithNullLogin(Courier courier) {
        return new CourierCreds("", courier.getPassword());
    }
}
