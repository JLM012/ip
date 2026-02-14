package spark;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses raw user input into their different components.
 */
public class Parser {

    /**
     * Splits a raw input line into a command word and the remaining arguments.
     * @param input The raw user input line.
     * @return A two-element array containing {@code [command, rest]}, where {@code rest} is
     * the remaining input after the command
     * @throws SparkException If the input is empty
     */
    public static String[] parse(String input) throws SparkException {
        if (input.trim().isEmpty()) throw new SparkException("Please type a command.");

        String[] inputSections = input.trim().split(" ", 2);
        assert inputSections.length >= 1 : "split should produce at least one token";
        assert !inputSections[0].isEmpty() : "command word should not be empty";

        String command = inputSections[0];
        String rest = (inputSections.length == 2) ? inputSections[1].trim() : "";
        return new String[] { command, rest };
    }

    /**
     * Parses a 1-based index from a command argument string and converts it to 0-based.
     * @param rest The argument string that contains the index.
     * @param formatMessage The error message to use if parsing fails.
     * @return The parsed index in 0-based form.
     * @throws SparkException If {@code rest} is empty or does not contain a valid integer index.
     */
    public static int parseIndex(String rest, String formatMessage) throws SparkException {
        if (rest.isEmpty()) throw new SparkException(formatMessage);
        try {
            return Integer.parseInt(rest.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new SparkException(formatMessage);
        }
    }

    /**
     * Parses a {@code todo} command argument into a {@link Todo} task
     * @param rest The description portion of the todo command.
     * @return A {@link Todo} task created with the provided description.
     * @throws SparkException If the description is missing.
     */
    public static Todo parseTodo(String rest) throws SparkException {
        if (rest.isEmpty()) {
            throw new SparkException("Todo format: todo <description>");
        }
        return new Todo(rest);
    }

    /**
     * Parses a {@code deadline} command argument into a {@link Deadline} task.
     * @param rest The arguments following the {@code deadline} command word.
     * @return A {@link Deadline} task created from the parsed description and date/time.
     * @throws SparkException If the format is invalid or required parts are missing
     */
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

    /**
     * Parses an {@code event} command argument into an {@link Event} task.
     * @param rest The arguments following the {@code event} command word.
     * @return A {@link Event} task created from the parsed description, from and to strings.
     * @throws SparkException If the format is invalid or required parts are missing
     */
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

        String fromTime = fromTo[0].trim();
        String toTime = fromTo[1].trim();

        if (description.isEmpty() || fromTime.isEmpty() || toTime.isEmpty()) {
            throw new SparkException("Event format: event <desc> /from <start> /to <end>");
        }

        return new Event(description, fromTime, toTime);
    }

    /**
     * Parses an {@code find} command argument into a String {@code keyword}.
     * @param rest The keyword from the command
     * @return A string {@code keyword}
     * @throws SparkException If the keyword is missing
     */
    public static String parseFind(String rest) throws SparkException {
        String keyword = rest.trim();
        if (keyword.isEmpty()) {
            throw new SparkException("Find format: find <keyword>");
        }
        return keyword;
    }



}
