public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toFileString() {
        int done = isDone ? 1 : 0;
        return "T | " + done + " | " + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
