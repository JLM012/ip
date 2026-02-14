package spark;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

/**
 * Handles saving and loading of tasks to and from a save file on disk.
 * <p>
 * The save file is stored at the configured Path.
 * <p>
 * This class is responsible for:
 *  <ul>
 *    <li>Creating the data folder if it does not exist.</li>
 *    <li>Writing the current task list to disk.</li>
 *    <li>Reading tasks from disk and converting each line back into a {@link Task}.</li>
 *  </ul>
 */
public class Storage {
    private Path filePath;
    private static final DateTimeFormatter DEADLINE_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    /**
     * Creates a {@code Storage} that reads/writes tasks at the given file path.
     * @param filePath Path to the save file {@code data/spark.txt}.
     */
    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the given task list to disk.
     * @param tasks The taskList to be saved
     * @throws SparkException If an {@link IOException} occurs while writing the file.
     */
    public void save(TaskList tasks) throws SparkException {
        try {
            ensureDataFolderExists();

            ArrayList<String> lines = new ArrayList<>();
            for (int i = 0; i < tasks.getSize(); i++) {
                Task t = tasks.getTask(i);
                lines.add(t.toSaveString());
            }

            Files.write(filePath, lines, CREATE, TRUNCATE_EXISTING);

        } catch (IOException e) {
            throw new SparkException("Failed to save tasks: " + e.getMessage());
        }


    }

    /**
     * Loads tasks from disk and returns them as a list.
     * <p>
     * If the save file does not exist, returns an empty list.
     * @return A list of loaded tasks. Empty list if no save file is found.
     * @throws SparkException If an {@link IOException} occurs while reading the file,
     * or if any line in the file is corrupted.
     */
    public List<Task> load() throws SparkException {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            List<Task> loadedTasks = new ArrayList<>();
            for (String line : lines) {
                if (line.isEmpty()) {
                    continue;
                }
                Task task = parseLine(line);
                loadedTasks.add(task);
            }

            return loadedTasks;

        } catch (IOException e) {
            throw new SparkException("Failed to load tasks: " + e.getMessage());
        }
    }

    private void ensureDataFolderExists() throws IOException {
        Path data = filePath.getParent();
        if (!Files.exists(data)) {
            Files.createDirectories(data);
        }
    }

    private Task parseLine(String line) throws SparkException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new SparkException("Saved file was corrupted");
        }

        String taskType = parts[0];
        String doneStatus = parts[1];
        String description = parts[2];

        Task task;
        switch (taskType) {
        case "T":
            task = new Todo(description);
            break;

        case "D":
            if (parts.length < 4) {
                throw new SparkException("Deadline task data was corrupted");
            }
            LocalDateTime deadlineDateTime = LocalDateTime.parse(parts[3], DEADLINE_FORMAT);
            task = new Deadline(description, deadlineDateTime);
            break;

        case "E":
            if (parts.length < 5) {
                throw new SparkException("Event task data was corrupted");
            }
            task = new Event(description, parts[3], parts[4]);
            break;

        default:
            throw new SparkException("Unknown task type in save data: " + taskType);
        }

        if (doneStatus.equals("1")) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }

        return task;
    }

}
