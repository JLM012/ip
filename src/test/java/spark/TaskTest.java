package spark;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    @Test
    public void constructor_validDescription_createsTask() {
        Task task = new Task("buy groceries");
        assertEquals("buy groceries", task.getDescription());
        assertFalse(task.isDone());
    }

    @Test
    public void markAsDone_unmarkedTask_marksTaskDone() {
        Task task = new Task("buy groceries");
        task.markAsDone();
        assertTrue(task.isDone());
    }

    @Test
    public void markAsNotDone_markedTask_marksTaskNotDone() {
        Task task = new Task("buy groceries");
        task.markAsDone();
        task.markAsNotDone();
        assertFalse(task.isDone());
    }

    @Test
    public void getStatusIcon_unmarkedTask_returnsSpace() {
        Task task = new Task("buy groceries");
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void getStatusIcon_markedTask_returnsX() {
        Task task = new Task("buy groceries");
        task.markAsDone();
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void toSaveString_unmarkedTask_correctFormat() {
        Task task = new Task("buy groceries");
        assertEquals("T | 0 | buy groceries", task.toSaveString());
    }

    @Test
    public void toSaveString_markedTask_correctFormat() {
        Task task = new Task("buy groceries");
        task.markAsDone();
        assertEquals("T | 1 | buy groceries", task.toSaveString());
    }

    @Test
    public void toString_unmarkedTask_correctFormat() {
        Task task = new Task("buy groceries");
        assertEquals("[ ] buy groceries", task.toString());
    }

    @Test
    public void toString_markedTask_correctFormat() {
        Task task = new Task("buy groceries");
        task.markAsDone();
        assertEquals("[X] buy groceries", task.toString());
    }

    @Test
    public void getDateTime_task_returnsNull() {
        Task task = new Task("buy groceries");
        assertNull(task.getDateTime());
    }
}
