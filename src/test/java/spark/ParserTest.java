package spark;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    //Copilot suggested additonal test cases when I prompted for more tests for Parser.java
    @Test
    public void parse_validInput_splitCorrectly() throws SparkException {
        String[] result = Parser.parse("todo buy groceries");
        assertEquals("todo", result[0], "Command should be 'todo'");
        assertEquals("buy groceries", result[1], "Rest should be 'buy groceries'");
    }

    @Test
    public void parse_emptyInput_throwsSparkException() {
        assertThrows(SparkException.class, () -> Parser.parse(""));
        assertThrows(SparkException.class, () -> Parser.parse("   "));
    }

    @Test
    public void parseIndex_validInput_returnsZeroBasedIndex() throws SparkException {
        assertEquals(0, Parser.parseIndex("1", "Error"), "Index 1 should convert to 0");
        assertEquals(4, Parser.parseIndex("5", "Error"), "Index 5 should convert to 4");
    }

    @Test
    public void parseIndex_invalidInput_throwsSparkException() {
        assertThrows(SparkException.class, () -> Parser.parseIndex("", "Error"));
        assertThrows(SparkException.class, () -> Parser.parseIndex("abc", "Error"));
    }

    @Test
    public void parseTodo_validInput_returnsTodo() throws SparkException {
        Todo t = Parser.parseTodo("buy groceries");
        assertEquals("buy groceries", t.getDescription());
        assertFalse(t.isDone());
    }

    @Test
    public void parseTodo_emptyInput_throwsSparkException() {
        assertThrows(SparkException.class, () -> Parser.parseTodo(""));
    }

    @Test
    public void parseDeadline_validInput_returnsDeadline() throws SparkException {
        Deadline d = Parser.parseDeadline("return book /by 2025-01-25 1200");

        assertEquals("return book", d.getDescription(), "Description should be parsed correctly.");
        assertFalse(d.isDone(), "New tasks should be not done by default.");
    }

    @Test
    public void parseDeadline_missingBy_throwsSparkException() {
        assertThrows(SparkException.class, () -> Parser.parseDeadline("return book"));
    }

    @Test
    public void parseDeadline_invalidDate_throwsSparkException() {
        assertThrows(SparkException.class, () -> Parser.parseDeadline("return book /by tomorrow"));
        assertThrows(SparkException.class, () -> Parser.parseDeadline("return book /by 2025-01-25")); // missing time
    }

    @Test
    public void parseDeadline_emptyDescription_throwsSparkException() {
        assertThrows(SparkException.class, () -> Parser.parseDeadline("/by 2025-01-25 1200"));
    }

    @Test
    public void parseEvent_validInput_returnsEvent() throws SparkException {
        Event e = Parser.parseEvent("team meeting /from 2025-01-25 1000 /to 2025-01-25 1100");
        assertEquals("team meeting", e.getDescription());
        assertFalse(e.isDone());
    }

    @Test
    public void parseEvent_missingFrom_throwsSparkException() {
        assertThrows(SparkException.class, () -> Parser.parseEvent("team meeting /to 2025-01-25 1100"));
    }

    @Test
    public void parseEvent_missingTo_throwsSparkException() {
        assertThrows(SparkException.class, () -> Parser.parseEvent("team meeting /from 2025-01-25 1000"));
    }

    @Test
    public void parseEvent_emptyDescription_throwsSparkException() {
        assertThrows(SparkException.class, () -> Parser.parseEvent("/from 1000 /to 1100"));
    }

    @Test
    public void parseFind_validInput_returnsKeyword() throws SparkException {
        String keyword = Parser.parseFind("book");
        assertEquals("book", keyword);
    }

    @Test
    public void parseFind_emptyInput_throwsSparkException() {
        assertThrows(SparkException.class, () -> Parser.parseFind(""));
    }
}


