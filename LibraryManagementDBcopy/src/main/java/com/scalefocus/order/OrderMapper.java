package com.scalefocus.order;

import org.springframework.stereotype.Component;
import com.scalefocus.book.Book;
import com.scalefocus.book.BookAccessor;
import com.scalefocus.client.Client;
import com.scalefocus.client.ClientAccessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    private final ClientAccessor clientAccessor;
    private final BookAccessor bookAccessor;

    public OrderMapper(ClientAccessor clientAccessor, BookAccessor bookAccessor) {
        this.clientAccessor = clientAccessor;
        this.bookAccessor = bookAccessor;
    }

    public List<Order> mapResultSetToOrder(ResultSet orderResultSet){
        List<Order> orderList = new ArrayList<>();
        try (orderResultSet){
            while (orderResultSet.next()){
                Client client = clientAccessor.getClientByID(orderResultSet.getInt(2));
                Book book = bookAccessor.getBookByID(orderResultSet.getInt(3));
                LocalDate fromDate = orderResultSet.getDate(4).toLocalDate();
                LocalDate dueDate = orderResultSet.getDate(5).toLocalDate();
                Order order = new Order(client, book, fromDate, dueDate);
                orderList.add(order);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return orderList;
    }

    public int mapResultSetToInt(ResultSet resultSet){
        int id = 0;
        try (resultSet){
            while (resultSet.next()){
                id = resultSet.getInt(1);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return id;
    }
}