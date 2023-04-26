package com.scalefocus.book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookAccessor {

    private static final String FILE_NOT_FOUND_MESSAGE = "File not found";
    private static final String BOOK_FILE_PATH = "src/Books_Authors_Published.txt";
    private static final BufferedReader reader;
    private static final BufferedWriter writer;

    static {
        try {
            reader = new BufferedReader(new FileReader(BOOK_FILE_PATH));
            writer = new BufferedWriter(new FileWriter(BOOK_FILE_PATH, true));
        } catch (IOException e) {
            throw new RuntimeException("File not found with path : " + BOOK_FILE_PATH, e);
        }
    }

    public List<String> readAllBooks() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(BOOK_FILE_PATH));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return reader.lines().collect(Collectors.toList());
    }

    public void addBook(String string) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE_PATH, true))) {
            writer.append(string);
        } catch (IOException e) {
            throw new RuntimeException(FILE_NOT_FOUND_MESSAGE, e);
        }
    }

    public void deleteBook(String fileWithoutTheDeleted) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE_PATH, false))) {
            writer.write(fileWithoutTheDeleted);
        } catch (IOException e) {
            throw new RuntimeException(FILE_NOT_FOUND_MESSAGE, e);
        }
    }


}