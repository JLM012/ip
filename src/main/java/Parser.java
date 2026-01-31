import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    public static String[] parse(String input) throws SparkException {
        if (input.trim().isEmpty()) throw new SparkException("Please type a command.");

        String[] inputSections = input.trim().split(" ", 2);
        String command = inputSections[0];
        String rest = (inputSections.length == 2) ? inputSections[1].trim() : "";
        return new String[] { command, rest };
    }

    public static int parseIndex(String rest, String formatMessage) throws SparkException {
        if (rest.isEmpty()) throw new SparkException(formatMessage);
        try {
            return Integer.parseInt(rest.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new SparkException(formatMessage);
        }
    }


    public static Todo parseTodo(String rest) throws SparkException {
        if (rest.isEmpty()) {
            throw new SparkException("Todo format: todo <description>");
        }
        return new Todo(rest);
    }

    public static Deadline parseDeadline(String rest) throws SparkException {
        String[] deadlineArgs = rest.split("\\s*/by\\s*", 2);
        if (deadlineArgs.length < 2) {
            throw new SparkException("Deadline format: deadline <desc> /by <yyyy-MM-dd HHmm>");
        }

        String description = deadlineArgs[0].trim();
        String byString = deadlineArgs[1].trim();
        if (description.isEmpty() || byString.isEmpty()) {
            throw new SparkException("Deadline format: deadline <desc> /by <yyyy-MM-dd HHmm>");
        }

        try {
            DateTimeFormatter deadlineFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            LocalDateTime deadlineDateTime = LocalDateTime.parse(byString, deadlineFormat);
            return new Deadline(description, deadlineDateTime);
        } catch (DateTimeParseException e) {
            throw new SparkException("Invalid deadline date/time format. " +
                    "Please use yyyy-MM-dd HHmm, e.g. 2025-01-25 1200");
        }
    }

    public static Event parseEvent(String rest) throws SparkException {
        String[] eventArgs = rest.split("\\s*/from\\s*", 2);
        if (eventArgs.length < 2) {
            throw new SparkException("Event format: event <desc> /from <start> /to <end>");
        }
        String description = eventArgs[0].trim();
        String[] fromTo = eventArgs[1].trim().split("\\s*/to\\s*", 2);

        if (fromTo.length < 2) {
            throw new SparkException("Event format: event <desc> /from <start> /to <end>");
        }

        String from = fromTo[0].trim();
        String to = fromTo[1].trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new SparkException("Event format: event <desc> /from <start> /to <end>");
        }

        return new Event(description, from, to);
    }


}
