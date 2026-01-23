import java.io.*;
import java.util.Scanner;

public class Spark {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Spark!");
        System.out.println("How can i help you today?");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if (command.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("It was a pleasure assisting you! Goodbye!");
                System.out.println("____________________________________________________________");
                break;
            }
            System.out.println("____________________________________________________________");
            System.out.println(command);
            System.out.println("____________________________________________________________");
        }
    }
}
