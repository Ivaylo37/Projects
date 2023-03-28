package com.scalefocus;

import com.scalefocus.book.BookPresenter;
import com.scalefocus.util.ConsoleReader;

public class LibraryManagement {

    private static final BookPresenter bookPresenter= new BookPresenter();


    private static final String GREETING_MESSAGE = "Choose option:\n" +
            "1: Books :\n" +
            "2: Authors :\n" +
            "3: Orders : \n";

    public static void main(String[] args) {
        while (true) {
            System.out.println(GREETING_MESSAGE);
            int choice = ConsoleReader.readInt();
            switch (choice){
                case 1:
                    bookPresenter.showBookMenu();
                    break;
                case 2:
                    System.out.println("2");
            }
            System.out.println(choice);
        }
    }
}
