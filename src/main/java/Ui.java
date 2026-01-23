import java.util.*;

//Responsible for reading user input and printing responses.
public class Ui {
    private Scanner scanner = new Scanner(System.in);

    //prints welcome message at the start of chatbot.
    public void showWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Spark!");
        System.out.println("How can i help you today?");
        System.out.println("____________________________________________________________");
    }

    //prints a confirmation message after a task was added.
    public void showAdded(String task) {
        System.out.println("____________________________________________________________");
        System.out.println("added: " + task);
        System.out.println("____________________________________________________________");
    }

    //prints the current list of tasks after command "list" is given.
    public void showList(TaskList tasks) {
        System.out.println("____________________________________________________________");
        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.println((i + 1) + ". " + tasks.getTask(i));
        }
        System.out.println("____________________________________________________________");
    }

    //prints exit message after command "bye" is given.
    public void showBye() {
        System.out.println("____________________________________________________________");
        System.out.println("It was a pleasure assisting you! Goodbye!");
        System.out.println("____________________________________________________________");
    }

    public String readCommand() {
        return scanner.nextLine();
    }
}
