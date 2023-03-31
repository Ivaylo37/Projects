package com.scalefocus.order;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class OrderAccessor {

    private static final String FILE_NOT_FOUND_MESSAGE = "File not found";
    private static final String ORDER_FILE_PATH = "src/Orders.txt";
    private static final BufferedReader reader;
    private static final BufferedWriter writer;

    static {
        try {
            reader = new BufferedReader(new FileReader(ORDER_FILE_PATH));
            writer = new BufferedWriter(new FileWriter(ORDER_FILE_PATH, true));
        }  catch (IOException e) {
            throw new RuntimeException("File not found with path : " + ORDER_FILE_PATH, e);
        }
    }

    public List<String> readAllOrders(){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(ORDER_FILE_PATH));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return reader.lines().collect(Collectors.toList());
    }

    public void addOrder(String order){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_FILE_PATH, true))){
            writer.append(order);
        }catch (IOException e) {
            throw new RuntimeException(FILE_NOT_FOUND_MESSAGE, e);
        }
    }
    public void deleteOrder(String fileWithoutDeleted){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_FILE_PATH, false))){
            writer.write(fileWithoutDeleted);
        }catch (IOException e) {
            throw new RuntimeException(FILE_NOT_FOUND_MESSAGE, e);
        }
    }
}
