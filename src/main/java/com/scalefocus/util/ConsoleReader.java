package com.scalefocus.util;

import java.util.Scanner;

public final class ConsoleReader {

    private static final String INVALID_INPUT = "%s is not a valid option. Try again : ";
    private static final Scanner scanner = new Scanner(System.in);

    public static String readString() {
        return scanner.nextLine();
    }

    public static int readInt() {
        while (!scanner.hasNextInt()) {
            String input = scanner.next();
            System.out.printf(INVALID_INPUT, input);
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }
}
