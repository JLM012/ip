package spark;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime deadlineDateTime;

    /**
     * Constructs a new deadline task with the given description and deadline date/time.
     *
     * @param description The description of the deadline task.
     * @param deadlineDateTime The date and time when the task is due.
     */
    public Deadline(String description, LocalDateTime deadlineDateTime) {
        super(description);
        this.deadlineDateTime = deadlineDateTime;
    }

    /**
     * Returns the deadline date/time for this task.
     *
     * @return The deadline as a {@link LocalDateTime}.
     */
    public LocalDateTime getBy() {
        return this.deadlineDateTime;
    }

    @Override
    public String toSaveString() {
        int done = isDone ? 1 : 0;
        return "D | " + done + " | " + description + " | " + getDeadlineString();
    }

    /**
     * Returns the deadline date/time as a formatted string.
     *
     * @return The deadline formatted as {@code "MMM d yyyy, h:mma"} (e.g., "Jan 25 2025, 12:00PM").
     */
    public String getDeadlineString() {
        DateTimeFormatter deadlineFormat = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
        return deadlineFormat.format(this.deadlineDateTime);
    }

    @Override
    public LocalDateTime getDateTime() {
        return deadlineDateTime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getDeadlineString() + ")";
    }
}
