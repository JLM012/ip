package spark;

import java.nio.file.Paths;
import java.util.List;

/**
 * Main chatbot class that controls the UI, Task list and Storage.
 */
public class Spark {
    private final Ui ui = new Ui();
    private TaskList tasks;
    private final Storage storage = new Storage(Paths.get("data", "spark.txt"));
    private boolean didLoadFail = false;
    private String loadFailedMessage = "";
    private boolean isExit = false;


    /**
     * Checks if the chatbot should exit.
     *
     * @return {@code true} if the user has entered the {@code bye} command, otherwise {@code false}.
     */
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

        try {
            String[] parsedInput = Parser.parse(input.trim());
            String command = parsedInput[0];
            String rest = parsedInput[1];

            switch (command) {
            case "bye":
                isExit = true;
                return ui.getByeMessage();

            case "list":
                return ui.getListMessage(tasks);

            case "mark": {
                return markTask(rest);
            }

            case "unmark": {
                return unmarkTask(rest);
            }

            case "todo": {
                return addTask(Parser.parseTodo(rest));
            }

            case "deadline": {
                return addTask(Parser.parseDeadline(rest));
            }

            case "event": {
                return addTask(Parser.parseEvent(rest));
            }

            case "delete": {
                return deleteTask(rest);
            }

            case "find": {
                return findTasks(rest);
            }

            case "sort": {
                return sortTasks();
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

        while (!isExit) {
            String input = ui.readCommand();
            String response = getResponse(input);

            System.out.println(response);
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

    /**
     * Adds a task to the task list and saves it to disk.
     *
     * @param task The task to be added.
     * @throws SparkException If saving to disk fails.
     */
    private String addTask(Task task) throws SparkException {
        tasks.addTask(task);
        storage.save(tasks);
        return ui.getAddedMessage(task, tasks.getSize());
    }

    /**
     * Marks a task as done based on the provided index and saves it to disk.
     *
     * @param rest The argument string containing the task index.
     * @throws SparkException If the index is invalid or saving fails.
     */
    private String markTask(String rest) throws SparkException {
        int index = Parser.parseIndex(rest, "Mark format: mark <taskNumber>");
        Task marked = tasks.mark(index);
        storage.save(tasks);
        return ui.getMarkMessage(marked);
    }


    /**
     * Marks a task as not done based on the provided index and saves it to disk.
     *
     * @param rest The argument string containing the task index.
     * @throws SparkException If the index is invalid or saving fails.
     */
    private String unmarkTask(String rest) throws SparkException {
        int index = Parser.parseIndex(rest, "Unmark format: unmark <taskNumber>");
        Task unmarked = tasks.unmark(index);
        storage.save(tasks);
        return ui.getUnmarkMessage(unmarked);
    }


    /**
     * Deletes a task based on the provided index and saves changes to disk.
     *
     * @param rest The argument string containing the task index.
     * @throws SparkException If the index is invalid or saving fails.
     */
    private String deleteTask(String rest) throws SparkException {
        int index = Parser.parseIndex(rest, "Delete format: delete <taskNumber>");
        Task deleted = tasks.deleteTask(index);
        storage.save(tasks);
        return ui.getDeletedMessage(deleted, tasks.getSize());
    }

    /**
     * Finds tasks matching the keyword and returns the response.
     */
    private String findTasks(String rest) throws SparkException {
        String keyword = Parser.parseFind(rest);
        return ui.getFindMessage(tasks.find(keyword));
    }

    /**
     * Sorts tasks by date/time, saves to disk, and returns the response.
     */
    private String sortTasks() throws SparkException {
        tasks.sortByDateTime();
        storage.save(tasks);
        return "Sorted tasks.\n" + ui.getListMessage(tasks);
    }
}
