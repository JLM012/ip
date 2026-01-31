package spark;

import java.util.Scanner;

/**
 * Handles all user-facing input and output for the Spark chatbot.
 */
public class Ui {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Prints the welcome message shown when the chatbot starts.
     */
    public void showWelcome() {
        printSeparator();
        System.out.println("Hello! I'm Spark!");
        System.out.println("How can i help you today?");
        printSeparator();
    }

    /**
     * Prints a confirmation message after a task has been added.
     * @param task Task that was added
     * @param totalTasks The total number of tasks after the addition.
     */
    public void showAdded(Task task, int totalTasks) {
        printSeparator();
        System.out.println("Done. I've added this task:" );
        System.out.println("   " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list" );
        printSeparator();
    }

    /**
     * Prints the current list of tasks.
     * @param tasks The {@link TaskList} containing tasks to print.
     */
    public void showList(TaskList tasks) {
        printSeparator();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.println((i + 1) + ". " + tasks.getTask(i));
        }
        printSeparator();
    }

    /**
     * Prints the goodbye message when the user exits the chatbot.
     */
    public void showBye() {
        printSeparator();
        System.out.println("It was a pleasure assisting you! Goodbye!");
        printSeparator();
    }

    /**
     * Prints a confirmation message after a task has been marked as done.
     * @param task The task that was marked as done
     */
    public void showMark(Task task) {
        printSeparator();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("   " + task);
        printSeparator();
    }

    /**
     * Prints a confirmation message after a task has been marked as not done.
     * @param task The task that was marked as not done
     */
    public void showUnmark(Task task) {
        printSeparator();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
        printSeparator();
    }

    /**
     * Prints an error message to the user.
     * @param message message to be printed
     */
    public void showError(String message) {
        printSeparator();
        System.out.println(message);
        printSeparator();
    }

    /**
     * Prints a confirmation message after a task has been deleted.
     * @param task The task that was deleted
     * @param totalTasks The total number of tasks after deletion.
     */
    public void showDeleted(Task task, int totalTasks) {
        printSeparator();
        System.out.println("Done. I've deleted this task:" );
        System.out.println("   " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list" );
        printSeparator();
    }

    /**
     * Prints a horizontal separator line
     */
    public void printSeparator() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Reads the next full line of input from the user.
     * @return The user input line
     */
    public String readCommand() {
        return scanner.nextLine();
    }
}
