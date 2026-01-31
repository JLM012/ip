package spark;

import java.util.ArrayList;
import java.util.List;

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

    public int getSize() {
        return tasks.size();
    }

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

}
