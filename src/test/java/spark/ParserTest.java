package spark;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parseDeadline_validInput_returnsDeadline() throws SparkException {
        Deadline d = Parser.parseDeadline("return book /by 2025-01-25 1200");

        assertEquals("return book", d.getDescription(), "Description should be parsed correctly.");
        assertFalse(d.getIsDone(), "New tasks should be not done by default.");
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
}
