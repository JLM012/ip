
//Contains the main chatbot logic.
public class SparkApp {
    private Ui ui = new Ui();
    private TaskList tasks = new TaskList();

    public void start() {
        ui.showWelcome();

        // reads user input and adds to tasklist.
        // command "list" displays the current list.
        // command "mark" or "unmark" marks/unmark task at specified index
        // Tasks can be added using command "todo", "deadline", "event"
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

        } else if (command.equals("unmark")) {
            int index = Integer.parseInt(args[1]) - 1;
            Task unmarked = tasks.unmark(index);
            ui.showUnmark(unmarked);

        } else if (command.equals("todo")) {
            if (rest.isEmpty()) {
                throw new SparkException("Please provide a descrioption for todo. Example: todo read book");
            }
            Task todo = new Todo(rest);
            tasks.addTask(todo);
            int totalTasks = tasks.getSize();
            ui.showAdded(todo, totalTasks);

        } else if (command.equals("deadline")) {
            String[] deadlineArgs = rest.split(" /by ", 2);
            String description = deadlineArgs[0];
            String by = deadlineArgs[1];
            Task deadline = new Deadline(description, by);
            tasks.addTask(deadline);
            int totalTasks = tasks.getSize();
            ui.showAdded(deadline, totalTasks);

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
        } else if (command.equals("delete")) {
            int index = Integer.parseInt(args[1]) - 1;
            Task deleted = tasks.deleteTask(index);
            int totalTasks = tasks.getSize();
            ui.showDeleted(deleted, totalTasks);
        } else {
            throw new SparkException("The input you provided is invalid");
        }

    }

}
