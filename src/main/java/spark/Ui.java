package spark;

import java.util.List;
import java.util.Scanner;

/**
 * Handles all user-facing input and output for the Spark chatbot.
 */
public class Ui {
    private Scanner scanner = new Scanner(System.in);

    private static final String SEPARATOR =
            "_______________________________________________________";
    private static final String LS = System.lineSeparator();

    private String box(String... lines) {
        StringBuilder sb = new StringBuilder();
        sb.append(SEPARATOR).append(LS);
        for (String line : lines) {
            sb.append(line).append(LS);
        }
        sb.append(SEPARATOR);
        return sb.toString();
    }


    /**
     * Prints the welcome message shown when the chatbot starts.
     */
    public void showWelcome() {
        System.out.println(getWelcomeMessage());
    }

    public String getWelcomeMessage() {
        return box("Hello! I'm Spark!", "How can i help you today?");
    }

    /**
     * Prints a confirmation message after a task has been added.
     * @param task Task that was added
     * @param totalTasks The total number of tasks after the addition.
     */
    public void showAdded(Task task, int totalTasks) {
        System.out.println(getAddedMessage(task, totalTasks));
    }

    public String getAddedMessage(Task task, int totalTasks) {
        return box(
                "Done. I've added this task:",
                "   " + task,
                "Now you have " + totalTasks + " tasks in the list"
        );
    }

    /**
     * Prints the current list of tasks.
     * @param tasks The {@link TaskList} containing tasks to print.
     */
    public void showList(TaskList tasks) {
        System.out.println(getListMessage(tasks));
    }

    public String getListMessage(TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append(SEPARATOR).append(LS);
        sb.append("Here are the tasks in your list:").append(LS);
        for (int i = 0; i < tasks.getSize(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.getTask(i)).append(LS);
        }
        sb.append(SEPARATOR);
        return sb.toString();
    }


    /**
     * Prints the goodbye message when the user exits the chatbot.
     */
    public void showBye() {
        System.out.println(getByeMessage());
    }

    public String getByeMessage() {
        return box("It was a pleasure assisting you! Goodbye!");
    }

    /**
     * Prints a confirmation message after a task has been marked as done.
     * @param task The task that was marked as done
     */
    public void showMark(Task task) {
        System.out.println(getMarkMessage(task));
    }

    public String getMarkMessage(Task task) {
        return box("Nice! I've marked this task as done:", "   " + task);
    }

    /**
     * Prints a confirmation message after a task has been marked as not done.
     * @param task The task that was marked as not done
     */
    public void showUnmark(Task task) {
        System.out.println(getUnmarkMessage(task));
    }

    public String getUnmarkMessage(Task task) {
        return box("OK, I've marked this task as not done yet:", "   " + task);
    }

    /**
     * Prints an error message to the user.
     * @param message message to be printed
     */
    public void showError(String message) {
        System.out.println(getErrorMessage(message));
    }

    public String getErrorMessage(String message) {
        return box(message);
    }

    /**
     * Prints a confirmation message after a task has been deleted.
     * @param task The task that was deleted
     * @param totalTasks The total number of tasks after deletion.
     */
    public void showDeleted(Task task, int totalTasks) {
        System.out.println(getDeletedMessage(task, totalTasks));
    }

    public String getDeletedMessage(Task task, int totalTasks) {
        return box(
                "Done. I've deleted this task:",
                "   " + task,
                "Now you have " + totalTasks + " tasks in the list"
        );
    }

    /**
     * Prints the list of tasks matching with the keyword
     * @param matches List of tasks containing the keyword
     */
    public void showFind(List<Task> matches) {
        System.out.println(getFindMessage(matches));
    }

    public String getFindMessage(List<Task> matches) {
        StringBuilder sb = new StringBuilder();
        sb.append(SEPARATOR).append(LS);

        if (matches.isEmpty()) {
            sb.append("No matching tasks found.").append(LS);
        } else {
            sb.append("Here are the matching tasks in your list:").append(LS);
            for (int i = 0; i < matches.size(); i++) {
                sb.append((i + 1)).append(". ").append(matches.get(i)).append(LS);
            }
        }

        sb.append(SEPARATOR);
        return sb.toString();
    }

    /**
     * Prints a horizontal separator line
     */
    public void printSeparator() {
        System.out.println(SEPARATOR);
    }

    /**
     * Reads the next full line of input from the user.
     * @return The user input line
     */
    public String readCommand() {
        return scanner.nextLine();
    }
}
