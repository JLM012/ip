
public class TaskList {

    //Store up to 100 Task
    private Task[] tasks = new Task[100];
    private int size = 0;

    public void addTask(String description) {
        tasks[size] = new Task(description);
        size++;
    }

    public int getSize() {
        return size;
    }

    public Task getTask(int index) {
        return tasks[index];
    }

    public Task mark(int index) {
        tasks[index].markAsDone();
        return tasks[index];
    }

    public Task unmark(int index) {
        tasks[index].markAsNotDone();
        return tasks[index];
    }

}
