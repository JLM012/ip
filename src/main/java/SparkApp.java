
//Contains the main chatbot logic.
public class SparkApp {
    private Ui ui = new Ui();
    private TaskList tasks = new TaskList();

    public void start() {
        ui.showWelcome();

        // reads user input and adds to tasklist.
        // If command "list" is given, displays the current list.
        // exits when the command "bye" is given.
        while (true) {
            String command = ui.readCommand();
            if (command.equals("bye")) {
                ui.showBye();
                break;
            } else if (command.equals("list")) {
                ui.showList(tasks);
            } else {

                tasks.addTask(command);
                ui.showAdded(command);
            }
        }
    }

}
