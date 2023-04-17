package com.scalefocus;

import com.scalefocus.author.AuthorPresenter;
import com.scalefocus.book.BookPresenter;
import com.scalefocus.client.ClientPresenter;
import com.scalefocus.order.OrderPresenter;
import com.scalefocus.util.ConsoleRangeReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.scalefocus.constants.GlobalConstants.*;
@SpringBootApplication
public class LibraryManagementSpring {

    private static final BookPresenter bookPresenter = new BookPresenter();
    private static final AuthorPresenter authorPresenter = new AuthorPresenter();
    private static final OrderPresenter orderPresenter = new OrderPresenter();
    private static final ClientPresenter clientPresenter = new ClientPresenter();

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSpring.class, args);
        printOptions();
    }

    private static void printOptions(){
        while (true) {
            System.out.println(GREETING_MESSAGE);
            int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
            switch (choice) {
                case 1:
                    bookPresenter.showBookMenu();
                    break;
                case 2:
                    authorPresenter.showAuthorMenu();
                    break;
                case 3:
                    orderPresenter.showOrderMenu();
                    break;
                case 4:
                    clientPresenter.showClientMenu();
                    break;
                case 5:
                    return;
            }
        }
    }
}