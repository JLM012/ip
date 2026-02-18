package spark;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Represents a list of {@link Task} objects.
 */
public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Constructs an empty {@code TaskList}.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a {@code TaskList} initialised with the given tasks.
     * @param initialTasks The initial tasks to include in the task list.
     */
    public TaskList(List<Task> initialTasks) {
        this.tasks = new ArrayList<>(initialTasks);
    }

    private void checkIndex(int index) throws SparkException {
        assert tasks != null : "tasks list should be initialized";
        assert tasks.size() >= 0 : "tasks size should not be negative";

        if (index < 0 || index >= tasks.size()) {
            throw new SparkException("Task number must be between 1 and " + tasks.size() + ".");
        }
    }

    /**
     * Adds task to the task list.
     * @param task The task to be added
     */
    public void addTask(Task task) {
        tasks.add(task);
    }


    /**
     * Returns the number of tasks in this task list.
     *
     * @return The size of the task list.
     */
    public int getSize() {
        return tasks.size();
    }


    /**
     * Returns the task at the given index.
     *
     * @param index The 0-based index of the task.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Marks the task at the given index as done.
     * @param index The task index
     * @return The task that was marked as done.
     * @throws SparkException If the index is out of range.
     */
    public Task mark(int index) throws SparkException {
        checkIndex(index);
        tasks.get(index).markAsDone();
        return tasks.get(index);
    }


    /**
     * Marks the task at the given index as not done.
     * @param index The task index
     * @return The task that was marked as not done.
     * @throws SparkException If the index is out of range.
     */
    public Task unmark(int index) throws SparkException {
        checkIndex(index);
        tasks.get(index).markAsNotDone();
        return tasks.get(index);
    }

    /**
     * Deletes and returns the task at the given index.
     * @param index The task index
     * @return The deleted task
     * @throws SparkException If the index is out of range
     */
    public Task deleteTask(int index) throws SparkException {
        checkIndex(index);
        Task removed = tasks.remove(index);
        return removed;
    }

    /**
     * Finds all matches with the given keyword.
     * @param keyword The keyword to match with
     * @return A list of tasks that match with the keyword
     */
    public List<Task> find(String keyword) {
        List<Task> matches = new ArrayList<>();
        String key = keyword.toLowerCase();

        for (Task t : tasks) {
            String description = t.getDescription();
            if (description.toLowerCase().contains(key)) {
                matches.add(t);
            }
        }

        return matches;
    }

    /**
     * Sorts the tasks in the list by date/time.
     * <p>
     * Tasks without a date/time are placed at the end of the list.
     */
    public void sortByDateTime() {
        tasks.sort(Comparator.comparing(
                Task::getDateTime,
                Comparator.nullsLast(Comparator.naturalOrder())
        ));
    }


}
