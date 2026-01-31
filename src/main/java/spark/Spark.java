package spark;

import java.nio.file.Paths;
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
        String[] parsedInput = Parser.parse(input);
        String command = parsedInput[0];
        String rest = parsedInput[1];

        if (command.equals("list")) {
            ui.showList(tasks);

        } else if (command.equals("mark")) {
            int index = Parser.parseIndex(rest, "Mark format: mark <taskNumber>");
            Task marked = tasks.mark(index);
            ui.showMark(marked);
            storage.save(tasks);

        } else if (command.equals("unmark")) {
            int index = Parser.parseIndex(rest, "Unmark format: unmark <taskNumber>");
            Task unmarked = tasks.unmark(index);
            ui.showUnmark(unmarked);
            storage.save(tasks);

        } else if (command.equals("todo")) {
            Task todo = Parser.parseTodo(rest);
            tasks.addTask(todo);
            int totalTasks = tasks.getSize();
            ui.showAdded(todo, totalTasks);
            storage.save(tasks);

        } else if (command.equals("deadline")) {
            Task deadline = Parser.parseDeadline(rest);
            tasks.addTask(deadline);
            int totalTasks = tasks.getSize();
            ui.showAdded(deadline, totalTasks);
            storage.save(tasks);

        } else if (command.equals("event")) {
            Task event = Parser.parseEvent(rest);
            tasks.addTask(event);
            int totalTasks = tasks.getSize();
            ui.showAdded(event, totalTasks);
            storage.save(tasks);

        } else if (command.equals("delete")) {
            int index = Parser.parseIndex(rest, "Delete format: delete <taskNumber>");
            Task deleted = tasks.deleteTask(index);
            int totalTasks = tasks.getSize();
            ui.showDeleted(deleted, totalTasks);
            storage.save(tasks);

        } else {
            throw new SparkException("The input you provided is invalid");
        }

    }
}
