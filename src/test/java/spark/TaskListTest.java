package spark;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    @Test
    public void mark_validIndex_marksTaskDone() throws SparkException {
        TaskList list = new TaskList();
        list.addTask(new Todo("buy groceries"));

        Task t = list.mark(0);
        assertTrue(t.isDone(), "Task should be marked done.");
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

    //Copilot suggested additonal test cases when I prompted for more tests for TaskList.java
    @Test
    public void unmark_validIndex_marksTaskNotDone() throws SparkException {
        TaskList list = new TaskList();
        Task t = new Todo("buy groceries");
        t.markAsDone();
        list.addTask(t);

        Task unmarked = list.unmark(0);
        assertFalse(unmarked.isDone(), "Task should be marked not done.");
    }

    @Test
    public void unmark_invalidIndex_throwsSparkException() {
        TaskList list = new TaskList();
        list.addTask(new Todo("watch movie"));

        assertThrows(SparkException.class, () -> list.unmark(-1));
        assertThrows(SparkException.class, () -> list.unmark(1));
    }

    @Test
    public void addTask_multipleItems_increasesSize() {
        TaskList list = new TaskList();
        assertEquals(0, list.getSize());

        list.addTask(new Todo("task 1"));
        assertEquals(1, list.getSize());

        list.addTask(new Todo("task 2"));
        assertEquals(2, list.getSize());
    }

    @Test
    public void deleteTask_validIndex_removesTask() throws SparkException {
        TaskList list = new TaskList();
        list.addTask(new Todo("task 1"));
        list.addTask(new Todo("task 2"));

        Task deleted = list.deleteTask(0);
        assertEquals("task 1", deleted.getDescription());
        assertEquals(1, list.getSize());
        assertEquals("task 2", list.getTask(0).getDescription());
    }

    @Test
    public void deleteTask_invalidIndex_throwsSparkException() {
        TaskList list = new TaskList();
        list.addTask(new Todo("task 1"));

        assertThrows(SparkException.class, () -> list.deleteTask(-1));
        assertThrows(SparkException.class, () -> list.deleteTask(1));
    }

    @Test
    public void find_keywordMatch_returnsMatches() {
        TaskList list = new TaskList();
        list.addTask(new Todo("buy book"));
        list.addTask(new Todo("read book"));
        list.addTask(new Todo("buy pen"));

        List<Task> matches = list.find("book");
        assertEquals(2, matches.size(), "Should find 2 tasks with 'book'");
    }

    @Test
    public void find_noMatch_returnsEmptyList() {
        TaskList list = new TaskList();
        list.addTask(new Todo("buy groceries"));

        List<Task> matches = list.find("phone");
        assertEquals(0, matches.size(), "Should find no tasks");
    }

    @Test
    public void find_caseInsensitive_findsMatches() {
        TaskList list = new TaskList();
        list.addTask(new Todo("Buy Book"));
        list.addTask(new Todo("READ BOOK"));

        List<Task> matches = list.find("book");
        assertEquals(2, matches.size(), "Find should be case-insensitive");
    }

    @Test
    public void getTask_validIndex_returnsTask() {
        TaskList list = new TaskList();
        Todo todo = new Todo("test task");
        list.addTask(todo);

        assertEquals(todo.getDescription(), list.getTask(0).getDescription());
    }

    @Test
    public void constructor_emptyList_createsEmptyTaskList() {
        TaskList list = new TaskList();
        assertEquals(0, list.getSize());
    }

    @Test
    public void constructor_withInitialTasks_initializesCorrectly() {
        TaskList list1 = new TaskList();
        list1.addTask(new Todo("task 1"));
        list1.addTask(new Todo("task 2"));

        TaskList list2 = new TaskList(list1.getSize() > 0 ? List.of(list1.getTask(0), list1.getTask(1)) : List.of());
        assertEquals(2, list2.getSize());
    }
}


