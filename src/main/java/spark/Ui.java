package spark;

import java.util.Scanner;

//Responsible for reading user input and printing responses.
public class Ui {
    private Scanner scanner = new Scanner(System.in);

    //prints welcome message at the start of chatbot.
    public void showWelcome() {
        printSeparator();
        System.out.println("Hello! I'm Spark!");
        System.out.println("How can i help you today?");
        printSeparator();
    }

    //prints a confirmation message after a task was added.
    public void showAdded(Task task, int totalTasks) {
        printSeparator();
        System.out.println("Done. I've added this task:" );
        System.out.println("   " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list" );
        printSeparator();
    }

    //prints the current list of tasks after command "list" is given.
    public void showList(TaskList tasks) {
        printSeparator();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.println((i + 1) + ". " + tasks.getTask(i));
        }
        printSeparator();
    }

    //prints exit message after command "bye" is given.
    public void showBye() {
        printSeparator();
        System.out.println("It was a pleasure assisting you! Goodbye!");
        printSeparator();
    }

    //prints marked tast specified
    public void showMark(Task task) {
        printSeparator();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("   " + task);
        printSeparator();
    }

    //prints unmarked task specified
    public void showUnmark(Task task) {
        printSeparator();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
        printSeparator();
    }

    //print error messages
    public void showError(String message) {
        printSeparator();
        System.out.println(message);
        printSeparator();
    }

    //prints a confirmation message after a task was deleted.
    public void showDeleted(Task task, int totalTasks) {
        printSeparator();
        System.out.println("Done. I've deleted this task:" );
        System.out.println("   " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list" );
        printSeparator();
    }

    public void printSeparator() {
        System.out.println("____________________________________________________________");
    }

    public String readCommand() {
        return scanner.nextLine();
    }
}
