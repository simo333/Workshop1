package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static String[][] tasks = null;
    static final Path DATA_DIR = Paths.get("tasks.csv");

    public static void main(String[] args) {
        loadData();
        menu();
    }

    public static void menu() {
        Scanner inputScanner = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println(ConsoleColors.BLUE + "Please select an option: " + ConsoleColors.RESET);
            System.out.println("add");
            System.out.println("remove");
            System.out.println("list");
            System.out.println("exit");
            String userInput = inputScanner.nextLine();

            //Using enhanced switch statement
            switch (userInput.toLowerCase()) {
                case "add" -> addTask();
                case "remove" -> removeTask();
                case "list" -> showTaskList();
                case "exit" -> {
                    saveToFile();
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    System.exit(0);
                }
                default -> System.out.println(ConsoleColors.RED_BOLD_BRIGHT +
                        "ERROR: Please select a correct option." + ConsoleColors.RESET);
            }
        }

    }

    public static void loadData() {
        List<String> readData = new ArrayList<>();
        try {
            readData = Files.readAllLines(DATA_DIR);
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED_BACKGROUND_BRIGHT + "ERROR: File could not be loaded: " + e.getMessage());
            System.exit(-1);
        }
        tasks = new String[readData.size()][3];

        for (int i = 0; i < tasks.length; i++) {
            System.arraycopy(readData.get(i).split(","), 0, tasks[i], 0, tasks[i].length);
        }
    }

    public static void showTaskList() {
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void saveToFile() {
        String[] tasksToSave = new String[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            tasksToSave[i] = String.join(",", tasks[i]);
        }
        try {
            Files.write(DATA_DIR, Arrays.asList(tasksToSave));
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED_BACKGROUND_BRIGHT + "ERROR: Could not save files: " + e.getMessage());
        }
    }

    public static void addTask() {
        String[] newTask = new String[3];
        System.out.println("Please add task description:");
        newTask[0] = InputValidator.getDescriptionInput();
        System.out.println("Please add task due date:");
        newTask[1] = InputValidator.getDateInput();
        System.out.println("Is your task important:");
        newTask[2] = InputValidator.getBooleanInput();

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = newTask;
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "* Task added. *" + ConsoleColors.RESET);
    }

    public static void removeTask() {
        if (tasks.length == 0) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "ERROR: Task list is empty." + ConsoleColors.RESET);
        }
        if (tasks.length > 0) {
            while (true) {
                Scanner inputScanner = new Scanner(System.in);
                System.out.println("Select task to remove (0 - " + (tasks.length - 1 + "):"));
                String input = inputScanner.nextLine();
                try {
                    int taskIndex = Integer.parseInt(input);
                    tasks = ArrayUtils.remove(tasks, taskIndex);
                    System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "* Task removed. *" + ConsoleColors.RESET);
                    break;
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "ERROR: Enter valid task index."
                            + ConsoleColors.RESET);
                }
            }
        }

    }
}