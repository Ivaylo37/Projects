package com.scalefocus.client;

import com.scalefocus.order.Order;

import java.util.ArrayList;
import java.util.List;

public class Client {

    private String name;
    private List<Order> orders = new ArrayList<>();

    public Client(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", orders=" + orders +
                '}';
    }

    void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
