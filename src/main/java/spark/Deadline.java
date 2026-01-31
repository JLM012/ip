package spark;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime deadlineDateTime;

    public Deadline(String description, LocalDateTime deadlineDateTime) {
        super(description);
        this.deadlineDateTime = deadlineDateTime;
    }

    public LocalDateTime getBy() {
        return this.deadlineDateTime;
    }

    @Override
    public String toSaveString() {
        int done = isDone ? 1 : 0;
        return "D | " + done + " | " + description + " | " + getDeadlineString();
    }

    public String getDeadlineString() {
        DateTimeFormatter deadlineFormat = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
        return deadlineFormat.format(this.deadlineDateTime);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getDeadlineString() + ")";
    }
}
