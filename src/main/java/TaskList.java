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

    public void addTask(Task task) {
        tasks.add(task);
    }

    public int getSize() {
        return tasks.size();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public Task mark(int index) {
        tasks.get(index).markAsDone();
        return tasks.get(index);
    }

    public Task unmark(int index) {
        tasks.get(index).markAsNotDone();
        return tasks.get(index);
    }

    public Task deleteTask(int index) {
        Task removed = tasks.remove(index);
        return removed;
    }

}
