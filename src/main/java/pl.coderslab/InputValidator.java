package pl.coderslab;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class InputValidator {
    public static String getDescriptionInput() {
        Scanner inputScanner = new Scanner(System.in);
        while (true) {
            String input = inputScanner.nextLine();
            for (int i = 0; i < input.length(); ++i) {
                if (Character.isLetter(input.charAt(i))) {
                    return input;
                }
            }
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "ERROR: Task description must contain at least one letter."
                    + ConsoleColors.RESET);
        }
    }

    public static String getDateInput() {
        Scanner inputScanner = new Scanner(System.in);
        int[] dateArray = new int[3];
        LocalDate inputtedDate;
        while (true) {
            String input = inputScanner.nextLine();

            try {
                String[] dateStringArray = input.split("-");
                for (int i = 0; i < dateStringArray.length; i++) {
                    dateArray[i] = Integer.parseInt(dateStringArray[i]);
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "ERROR: Enter correct date in format: yyyy-mm-dd."
                        + ConsoleColors.RESET);
                continue;
            }

            try {
                inputtedDate = LocalDate.of(dateArray[0], dateArray[1], dateArray[2]);
                if (inputtedDate.compareTo(LocalDate.now()) < 0) {
                    throw new DateTimeException("ERROR: Your task's date must be related to the future");
                }
                return input;
            } catch (DateTimeException e) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + e.getMessage() +
                        ". Enter correct date in format: yyyy-mm-dd." + ConsoleColors.RESET);
            }
        }

    }

    public static String getBooleanInput() {
        Scanner inputScanner = new Scanner(System.in);
        while (true) {
            String input = inputScanner.nextLine().toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                return input;
            }
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "ERROR: Invalid value. Type 'true' or 'false'."
                    + ConsoleColors.RESET);
        }
    }
}