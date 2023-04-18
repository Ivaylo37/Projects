package com.scalefocus;

import com.scalefocus.author.AuthorPresenter;
import com.scalefocus.book.BookPresenter;
import com.scalefocus.client.ClientPresenter;
import com.scalefocus.order.OrderPresenter;
import com.scalefocus.util.ConsoleRangeReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.scalefocus.constants.GlobalConstants.*;
@SpringBootApplication
public class LibraryManagementSpring implements CommandLineRunner {

    private final BookPresenter bookPresenter;
    private final AuthorPresenter authorPresenter;
    private final OrderPresenter orderPresenter;
    private final ClientPresenter clientPresenter;

    public LibraryManagementSpring(BookPresenter bookPresenter, AuthorPresenter authorPresenter, OrderPresenter orderPresenter, ClientPresenter clientPresenter) {
        this.bookPresenter = bookPresenter;
        this.authorPresenter = authorPresenter;
        this.orderPresenter = orderPresenter;
        this.clientPresenter = clientPresenter;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSpring.class, args);
    }

    @Override
    public void run(String... args){
        while (true) {
            System.out.println(LMS_GREETING_MESSAGE);
            int choice = ConsoleRangeReader.readInt(LMS_MIN_MENU_OPTION, LMS_MAX_MENU_OPTION);
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