package spark;

public class Event extends Task {
    protected String from;
    protected  String to;


    /**
     * Constructs a new event task with the given description and time range.
     *
     * @param description The description of the event.
     * @param from The start time or date of the event.
     * @param to The end time or date of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toSaveString() {
        int done = isDone ? 1 : 0;
        return "E | " + done + " | " + description + " | " + from + " | " +to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

}
