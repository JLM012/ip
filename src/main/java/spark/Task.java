package spark;

/**
 * Represents a basic task with a description and a completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new task with the given description
     * The task is initially marked as not done.
     * @param description The description of a task (e.g. read book).
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); //mark done task with an X
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Changes this task into a standard string for saving to storage.
     * <p>
     * Format: {@code "T | <doneFlag> | <description>"} where {@code doneFlag} is {@code 1} if done,
     * otherwise {@code 0}.
     * @return A suitable string representation of task to be saved
     */
    public String toSaveString() {
        int done = isDone ? 1 : 0;
        return "T | " + done + " | " + description;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the user-facing string representation of this task.
     * <p>
     * Format: {@code "[<status>] <description>"} where {@code status} is {@code X} or a space.
     *
     * @return A formatted string for display.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
