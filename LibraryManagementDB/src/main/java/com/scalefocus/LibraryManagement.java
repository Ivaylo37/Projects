package com.scalefocus;

import com.scalefocus.author.AuthorPresenter;
import com.scalefocus.book.BookPresenter;
import com.scalefocus.client.ClientPresenter;
import com.scalefocus.order.OrderPresenter;
import com.scalefocus.util.ConsoleRangeReader;

public class LibraryManagement {

    private static final BookPresenter bookPresenter = new BookPresenter();
    private static final AuthorPresenter authorPresenter = new AuthorPresenter();
    private static final OrderPresenter orderPresenter = new OrderPresenter();
    private static final ClientPresenter clientPresenter = new ClientPresenter();
    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 5;

    private static final String GREETING_MESSAGE =
            "   ****** Choose option: ******* \n" +
            "   -----------------------------\n" +
            "   |   1: Book management:     |\n" +
            "   |   2: Authors management:  |\n" +
            "   |   3: Orders management:   |\n" +
            "   |   4: Clients management:  |\n" +
            "   |   5: Exit                 |\n" +
            "   -----------------------------";

    public static void main(String[] args) {
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