package com.scalefocus.constants;

public class GlobalConstants {

    public static final int LMS_MIN_MENU_OPTION = 1;
    public static final int LMS_MAX_MENU_OPTION = 5;

    public static final String LMS_GREETING_MESSAGE =
            "   ****** Choose option: ******* \n" +
                    "   -----------------------------\n" +
                    "   |   1: Book management:     |\n" +
                    "   |   2: Authors management:  |\n" +
                    "   |   3: Orders management:   |\n" +
                    "   |   4: Clients management:  |\n" +
                    "   |   5: Exit                 |\n" +
                    "   -----------------------------";

    public static final int BP_MIN_MENU_OPTION = 1;
    public static final int BP_MAX_MENU_OPTION = 5;
    public static final String BP_BOOK_NAME_INSERT = "Please enter the book's name :";
    public static final String BP_BOOK_AUTHOR_INSERT = "Choose from the existing authors :";
    public static final String BP_BOOK_DATE_OF_CREATION_INSERT = "Please enter a date of creation :";
    public static final String BP_ENTER_BOOK_TO_EDIT = "Choose which book to edit by name";
    public static final String BP_BOOK_EDIT_CHOICE = "Choose what to edit : \n" +
            "1.Name \n" +
            "2.Author \n" +
            "3.Date of creation";
    public static final int BP_MIN_EDIT_OPTION = 1;
    public static final int BP_MAX_EDIT_OPTION = 5;
    public static final String BP_EDIT_OPTION_NAME = "Enter new name";
    public static final String BP_EDIT_OPTION_DATE = "Enter new date";
    public static final String BP_DELETE_BOOK = "Choose book to delete by name";

    public static final String BP_OPTIONS = "Choose what to do with the Books : " +
            "\n ---------------------" +
            "\n   1:Show all books" +
            "\n   2:Add book" +
            "\n   3:Edit book" +
            "\n   4:Remove book" +
            "\n   5:Back" +
            "\n ---------------------";

    public static final String BS_BOOK_AUTHOR_INSERT = "Enter the author's name :";

    public static final int AP_MIN_MENU_OPTION = 1;
    public static final int AP_MAX_MENU_OPTION = 5;
    public static final String AP_AUTHOR_NAME_INSERT = "Please enter the author's name :";
    public static final String AP_AUTHOR_TO_EDIT = "Author to edit";
    public static final String AP_AUTHOR_NAME_EDIT = "Enter new name";
    public static final String AP_OPTIONS = "Choose what to do with the Authors : " +
            "\n ---------------------" +
            "\n 1:Show all authors" +
            "\n 2:Add an author" +
            "\n 3:Edit author's name" +
            "\n 4:Remove an author" +
            "\n 5:Back \n" +
            "---------------------";
    public static final int CP_MIN_MENU_OPTION = 1;
    public static final int CP_MAX_MENU_OPTION = 5;
    public static final String CP_CLIENT_NAME_INSERT = "Please insert the client's name : ";
    public static final String CP_EDIT_NAME = "Please enter the new name : ";
    public static final String CP_DELETE_CLIENT = "Please enter a client to delete : ";
    public static final String CP_CLIENT_MENU_OPTIONS = "Choose what to do with the clients : " +
            "\n ---------------------" +
            "\n 1:Show all clients" +
            "\n 2:Add a client" +
            "\n 3:Edit client" +
            "\n 4:Remove client" +
            "\n 5:Back" +
            "\n ---------------------";
    public static final String OP_BOOK_NAME_DELETE = "Enter book name :";
    public static final String OP_CLIENT_NAME_INSERT = "Please enter a client's name from the list";
    public static final String OP_BOOK_NAME_INSERT = "Please enter a book's name from the list";
    public static final String OP_FILTER_ORDERS_OPTIONS = "Choose how to filter the orders :\n" +
            "1. By client \n" +
            "2. By issued date \n" +
            "3. By due date \n";
    public static final int OP_MIN_MENU_OPTION = 1;
    public static final int OP_MAX_MENU_OPTION = 6;
    public static final int OP_MIN_EDIT_OPTION = 1;
    public static final int OP_MAX_EDIT_OPTION = 5;
    public static final int OP_MIN_EDIT_DATE_OPTION = 1;
    public static final int OP_MAX_EDIT_DATE_OPTION = 3;
    public static final int OP_MIN_FILTER_OPTION = 1;
    public static final int OP_MAX_FILTER_OPTION = 3;
    public static final String OP_ON_BEFORE_AFTER_ISSUED = "Please choose \n" +
            "1.On \n" +
            "2.Before \n" +
            "3.After \n" +
            "the issued date";
    public static final String OP_ON_BEFORE_AFTER_DUE = "Please choose \n" +
            "1.On \n" +
            "2.Before \n" +
            "3.After \n" +
            "the due date";
    public static final String OP_DELETE_ORDER = "Choose which order to delete by client and book" + "\n" +
            "Enter client's name";;
    public static final String OP_OPTIONS = "Choose what to do with the Orders : " +
            "\n ---------------------" +
            "\n  1:Show all orders" +
            "\n  2:Filter orders" +
            "\n  3:Add order" +
            "\n  4:Edit order" +
            "\n  5:Remove order" +
            "\n  6:Back" +
            "\n ---------------------";

    public static final String OP_EDIT_OPTIONS = "Choose what do you want to edit : " +
            "\n 1:Edit the client" +
            "\n 2:Edit the book" +
            "\n 3:Extend the due date";
    public static final String OP_EDIT_ORDER_CHOOSE = "Choose order to edit by client name and book" + "\n" +
            "Enter client's name";


    public static final String OP_EDIT_OPTION_CLIENT_NAME = "Enter the new client's name";
    public static final String OP_EDIT_OPTION_BOOK_NAME = "Enter the new book's name";
    public static final String OP_EDIT_OPTION_EXTEND_DATE = "How do you want to extend the date? :" +
            "\n 1.Extend by days" +
            "\n 2.Extend by months" +
            "\n 3.Extend by years";

    public static final String OP_EXTEND_BY_DAYS = "How many days to extend with?";
    public static final String OP_EXTEND_BY_MONTHS = "How many months to extend with?";
    public static final String OP_EXTEND_BY_YEARS = "How many years to extend with?";
}