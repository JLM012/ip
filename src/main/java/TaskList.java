
public class TaskList {

    //Store up to 100 task descriptions
    private String[] tasks = new String[100];
    private int size = 0;

    public void addTask(String task ) {
        tasks[size] = task;
        size++;
    }

    public int getSize() {
        return size;
    }

    public String getTask(int index) {
        return tasks[index];
    }

}
