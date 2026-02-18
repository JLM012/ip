package spark;

public class Todo extends Task {

    /**
     * Constructs a new todo task with the given description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toSaveString() {
        int done = isDone ? 1 : 0;
        return "T | " + done + " | " + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
