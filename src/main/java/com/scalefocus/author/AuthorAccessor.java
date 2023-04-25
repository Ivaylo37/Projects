package com.scalefocus.author;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorAccessor {

    private static final String FILE_AUTHORS_PATH = "src/Authors.txt";

    private static final String FILE_NOT_FOUND_MESSAGE = "File not found";

    private static final BufferedReader reader;
    private static final BufferedWriter writer;

    static {
        try {
            reader = new BufferedReader(new FileReader(FILE_AUTHORS_PATH));
            writer = new BufferedWriter(new FileWriter(FILE_AUTHORS_PATH, true));
        } catch (IOException e) {
            throw new RuntimeException("File not found with path : " + FILE_AUTHORS_PATH, e);
        }
    }

    public List<String> readAllAuthors() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(FILE_AUTHORS_PATH));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return reader.lines().collect(Collectors.toList());
    }

    public void addAuthor(String author) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_AUTHORS_PATH, true))) {
            writer.append(author);
            writer.append("\n");
        } catch (IOException e) {
            throw new RuntimeException(FILE_NOT_FOUND_MESSAGE, e);
        }
    }

    public void deleteAuthor(String withoutDeleted) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_AUTHORS_PATH, false))) {
            writer.write(withoutDeleted);
        } catch (IOException e) {
            throw new RuntimeException(FILE_NOT_FOUND_MESSAGE, e);
        }
    }
}