
//Contains the main chatbot logic.
public class SparkApp {
    private Ui ui = new Ui();
    private TaskList tasks = new TaskList();

    public void start() {
        ui.showWelcome();

        // reads user input and adds to tasklist.
        // command "list" displays the current list.
        // command "mark" or "unmark" marks/unmark task at specified index
        // command "bye" exits the chatbot
        while (true) {
            String input = ui.readCommand();
            String command = (input.split(" "))[0];

            if (command.equals("bye")) {
                ui.showBye();
                break;
            } else if (command.equals("list")) {
                ui.showList(tasks);
            } else if (command.equals("mark")) {
                int index = Integer.parseInt((input.split(" "))[1]) - 1;
                Task marked = tasks.mark(index);
                ui.showMark(marked);
            } else if (command.equals("unmark")) {
                int index = Integer.parseInt((input.split(" "))[1]) - 1;
                Task unmarked = tasks.unmark(index);
                ui.showUnmark(unmarked);
            } else {
                tasks.addTask(input);
                ui.showAdded(input);

            }
        }
    }

}
