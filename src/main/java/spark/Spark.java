package spark;

import java.nio.file.Paths;
import java.util.List;

/**
 * Main chatbot class that controls the UI, Task list and Storage.
 */
public class Spark {
    private Ui ui = new Ui();
    private TaskList tasks;
    private final Storage storage = new Storage(Paths.get("data", "spark.txt"));
    private boolean didLoadFail = false;
    private String loadFailedMessage = "";
    private boolean isExit = false;


    public boolean isExit() {
        return isExit;
    }

    /**
     * Constructs a Spark chatbot instance.
     * <p>
     * Loads tasks from disk via {@link Storage#load()}.
     * If loading fails, initializes an empty {@link TaskList} and stores a warning message
     * to be shown when the chatbot starts.
     */
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

    public String getResponse(String input) {
        isExit = false;

        if (input == null || input.trim().isEmpty()) {
            return "";
        }

        String trimmed = input.trim();

        try {
            String[] parsedInput = Parser.parse(trimmed);
            String command = parsedInput[0];
            String rest = parsedInput[1];

            switch (command) {
            case "bye":
                isExit = true;
                return ui.getByeMessage();

            case "list":
                return ui.getListMessage(tasks);

            case "mark": {
                int index = Parser.parseIndex(rest, "Mark format: mark <taskNumber>");
                Task marked = tasks.mark(index);
                storage.save(tasks);
                return ui.getMarkMessage(marked);
            }

            case "unmark": {
                int index = Parser.parseIndex(rest, "Unmark format: unmark <taskNumber>");
                Task unmarked = tasks.unmark(index);
                storage.save(tasks);
                return ui.getUnmarkMessage(unmarked);
            }

            case "todo": {
                Task todo = Parser.parseTodo(rest);
                tasks.addTask(todo);
                storage.save(tasks);
                return ui.getAddedMessage(todo, tasks.getSize());
            }

            case "deadline": {
                Task deadline = Parser.parseDeadline(rest);
                tasks.addTask(deadline);
                storage.save(tasks);
                return ui.getAddedMessage(deadline, tasks.getSize());
            }

            case "event": {
                Task event = Parser.parseEvent(rest);
                tasks.addTask(event);
                storage.save(tasks);
                return ui.getAddedMessage(event, tasks.getSize());
            }

            case "delete": {
                int index = Parser.parseIndex(rest, "Delete format: delete <taskNumber>");
                Task deleted = tasks.deleteTask(index);
                storage.save(tasks);
                return ui.getDeletedMessage(deleted, tasks.getSize());
            }

            case "find": {
                String keyword = Parser.parseFind(rest);
                return ui.getFindMessage(tasks.find(keyword));
            }

            case "sort": {
                tasks.sortByDateTime();
                storage.save(tasks);
                return "Sorted tasks.\n" + ui.getListMessage(tasks);
            }

            default:
                throw new SparkException("The input you provided is invalid");
            }
        } catch (SparkException e) {
            return ui.getErrorMessage(e.getMessage());
        }
    }

    /**
     * Starts the main chatbot interaction loop.
     * <p>
     * Prints the welcome message, show warning if loading failed,
     * then repeatedly reads user input and executes commands until {@code bye} is entered
     */
    public void start() {
        ui.showWelcome();

        if (didLoadFail) {
            ui.showError("Saved tasks could not be loaded," +
                    " reverting to empty list \nReason: " + loadFailedMessage);
        }

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

    /**
     * Main entry point
     * @param args (not used)
     */
    public static void main(String[] args) {
        new Spark().start();
    }

    /**
     * Executes a single user command.
     *
     * @param input The raw user input line
     * @throws SparkException If the command is invalid or required arguments are missing/incorrect.
     */
    private void handleInput(String input) throws SparkException {
        String[] parsedInput = Parser.parse(input);
        String command = parsedInput[0];
        String rest = parsedInput[1];

        switch (command) {
        case "list":
            ui.showList(tasks);
            return;

        case "mark":
            markTask(rest);
            return;

        case "unmark":
            unmarkTask(rest);
            return;

        case "delete":
            deleteTask(rest);
            return;

        case "todo":
            addTask(Parser.parseTodo(rest));
            return;

        case "deadline":
            addTask(Parser.parseDeadline(rest));
            return;

        case "event":
            addTask(Parser.parseEvent(rest));
            return;

        case "find":
            ui.showFind(tasks.find(Parser.parseFind(rest)));
            return;

        default:
            throw new SparkException("The input you provided is invalid");
        }

    }

    private void addTask(Task task) throws SparkException {
        tasks.addTask(task);
        ui.showAdded(task, tasks.getSize());
        storage.save(tasks);
    }

    private void markTask(String rest) throws SparkException {
        int index = Parser.parseIndex(rest, "Mark format: mark <taskNumber>");
        Task marked = tasks.mark(index);
        ui.showMark(marked);
        storage.save(tasks);
    }

    private void unmarkTask(String rest) throws SparkException {
        int index = Parser.parseIndex(rest, "Unmark format: unmark <taskNumber>");
        Task unmarked = tasks.unmark(index);
        ui.showUnmark(unmarked);
        storage.save(tasks);
    }

    private void deleteTask(String rest) throws SparkException {
        int index = Parser.parseIndex(rest, "Delete format: delete <taskNumber>");
        Task deleted = tasks.deleteTask(index);
        ui.showDeleted(deleted, tasks.getSize());
        storage.save(tasks);
    }
}
