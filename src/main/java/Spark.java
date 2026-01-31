import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

//Entry point of the chatbot.
public class Spark {
    private Ui ui = new Ui();
    private TaskList tasks;
    private final Storage storage = new Storage(Paths.get("data", "spark.txt"));
    private boolean didLoadFail = false;
    private String loadFailedMessage = "";


    public Spark() {
        try {
            List<Task> loadedTasks = storage.load();
            this.tasks = new TaskList(loadedTasks);
        } catch (SparkException e) {
            this.tasks = new TaskList();
            didLoadFail = true;
            loadFailedMessage = e.getMessage();
        }
    }

    public void start() {
        ui.showWelcome();

        if (didLoadFail) {
            ui.showError("Saved tasks could not be loaded," +
                    " reverting to empty list \nReason: " + loadFailedMessage);
        }

        // reads user input and adds to tasklist.
        // command "list" displays the current list.
        // command "mark" or "unmark" marks/unmark task at specified index
        // Tasks can be added using command "todo", "by", "event"
        // command "bye" exits the chatbot
        while (true) {
            String input = ui.readCommand();

            if (input.equals("bye")) {
                ui.showBye();
                break;
            }
            try {
                handleInput(input);
            } catch (SparkException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Spark().start();
    }

    private void handleInput(String input) throws SparkException {
        String[] args = input.split(" ", 2);
        String command = args[0];
        String rest = (args.length == 2) ? args[1] : "";

        if (command.equals("list")) {
            ui.showList(tasks);

        } else if (command.equals("mark")) {
            int index = Integer.parseInt(args[1]) - 1;
            Task marked = tasks.mark(index);
            ui.showMark(marked);
            storage.save(tasks);

        } else if (command.equals("unmark")) {
            int index = Integer.parseInt(args[1]) - 1;
            Task unmarked = tasks.unmark(index);
            ui.showUnmark(unmarked);
            storage.save(tasks);

        } else if (command.equals("todo")) {
            if (rest.isEmpty()) {
                throw new SparkException("Please provide a description for todo. Example: todo read book");
            }
            Task todo = new Todo(rest);
            tasks.addTask(todo);
            int totalTasks = tasks.getSize();
            ui.showAdded(todo, totalTasks);
            storage.save(tasks);

        } else if (command.equals("deadline")) {
            String[] deadlineArgs = rest.split(" /by ", 2);
            if (deadlineArgs.length < 2) {
                throw new SparkException("Deadline format: deadline <desc> /by <yyyy-MM-dd HHmm>");
            }
            String description = deadlineArgs[0].trim();
            String byString = deadlineArgs[1].trim();
            if (description.isEmpty() || byString.isEmpty()) {
                throw new SparkException("Deadline format: deadline <desc> /by <yyyy-MM-dd HHmm>");
            }

            LocalDateTime by;
            try {
                DateTimeFormatter deadlineFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                by = LocalDateTime.parse(byString, deadlineFormat);
            } catch (DateTimeParseException e) {
            throw new SparkException("Invalid deadline date/time format. " +
                    "Please use yyyy-MM-dd HHmm, e.g. 2025-01-25 1200");
            }
            
            Task deadline = new Deadline(description, by);
            tasks.addTask(deadline);
            int totalTasks = tasks.getSize();
            ui.showAdded(deadline, totalTasks);
            storage.save(tasks);

        } else if (command.equals("event")) {
            String[] eventArgs = rest.split(" /from ", 2);
            String description = eventArgs[0];
            String[] fromTo = eventArgs[1].split(" /to ", 2);
            String from = fromTo[0];
            String to = fromTo[1];
            Task event = new Event(description, from, to);
            tasks.addTask(event);
            int totalTasks = tasks.getSize();
            ui.showAdded(event, totalTasks);
            storage.save(tasks);

        } else if (command.equals("delete")) {
            int index = Integer.parseInt(args[1]) - 1;
            Task deleted = tasks.deleteTask(index);
            int totalTasks = tasks.getSize();
            ui.showDeleted(deleted, totalTasks);
            storage.save(tasks);

        } else {
            throw new SparkException("The input you provided is invalid");
        }

    }
}
