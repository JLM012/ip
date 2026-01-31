package spark;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<>();

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> initialTasks) {
        this.tasks = new ArrayList<>(initialTasks);
    }

    private void checkIndex(int index) throws SparkException {
        if (index < 0 || index >= tasks.size()) {
            throw new SparkException("Task number must be between 1 and " + tasks.size() + ".");
        }
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public int getSize() {
        return tasks.size();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public Task mark(int index) throws SparkException {
        checkIndex(index);
        tasks.get(index).markAsDone();
        return tasks.get(index);
    }

    public Task unmark(int index) throws SparkException {
        checkIndex(index);
        tasks.get(index).markAsNotDone();
        return tasks.get(index);
    }

    public Task deleteTask(int index) throws SparkException {
        checkIndex(index);
        Task removed = tasks.remove(index);
        return removed;
    }

}
