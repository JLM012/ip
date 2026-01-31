package spark;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    @Test
    public void mark_validIndex_marksTaskDone() throws SparkException {
        TaskList list = new TaskList();
        list.addTask(new Todo("buy groceries"));

        Task t = list.mark(0);
        assertTrue(t.getIsDone(), "Task should be marked done.");
        assertEquals("buy groceries", t.getDescription(),
                "Description should match the task added.");
    }

    @Test
    public void mark_invalidIndex_throwsSparkException() {
        TaskList list = new TaskList();
        list.addTask(new Todo("watch movie"));

        assertThrows(SparkException.class, () -> list.mark(-1));

        // size is 1, so index 1 is invalid. mark(index) is 0-base.
        assertThrows(SparkException.class, () -> list.mark(1)); // size is 1, so index 1 is invalid
    }

}
