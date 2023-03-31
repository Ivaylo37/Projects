package com.scalefocus.order;

import com.scalefocus.author.Author;
import com.scalefocus.book.Book;
import com.scalefocus.client.Client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private static final OrderAccessor orderAccessor = new OrderAccessor();
    private static final OrderMapper orderMapper = new OrderMapper();

    public List<Order> getAllOrders(){
        List<String> orderStrings = orderAccessor.readAllOrders();
        List<Order> orders = new ArrayList<>();
        for (String orderString : orderStrings) {
            Order order = orderMapper.mapStringToOrder(orderString);
            orders.add(order);
        }
        return orders;
    }
    public void addOrder(Order order){
        String orderToString = orderMapper.mapOrderToString(order);
        orderAccessor.addOrder(orderToString);
    }

    public Order findOrderByClient(String nameToLookFor){
        List<Order> orders = getAllOrders();
        boolean found = false;
        for(Order order : orders){
            if (nameToLookFor.equalsIgnoreCase(order.getClient().getName())){
                found = true;
                return order;
            }
        }
        return null;
    }

    public void deleteOrder(Order orderToBeDeleted){
        String toDelete = orderMapper.mapOrderToString(orderToBeDeleted).trim();
        List<String> orderStrings = orderAccessor.readAllOrders();
        if (orderStrings.contains(toDelete)){
            orderStrings.remove(toDelete);
        }
        String newString = new String();
        for(String string : orderStrings){
            newString = newString.concat(string);
            newString = newString.concat("\n");
        }
        orderAccessor.deleteOrder(newString);
    }
}
