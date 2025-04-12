package edu.praktikum.sprint7.models;

public class Order {
    private String customerFirstName;
    private String customerLastName;
    private String customerAddress;
    private String customerMetroStation;
    private String customerPhone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public Order setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
        return this;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public Order setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
        return this;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public Order setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
        return this;
    }

    public String getCustomerMetroStation() {
        return customerMetroStation;
    }

    public Order setCustomerMetroStation(String customerMetroStation) {
        this.customerMetroStation = customerMetroStation;
        return this;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public Order setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
        return this;
    }

    public int getRentTime() {
        return rentTime;
    }

    public Order setRentTime(int rentTime) {
        this.rentTime = rentTime;
        return this;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public Order setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Order setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String[] getColor() {
        return color;
    }

    public Order setColor(String[] color) {
        this.color = color;
        return this;
    }
}
