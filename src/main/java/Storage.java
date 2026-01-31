import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class Storage {
    private Path filePath;

    public Storage(Path filePath) {
        this.filePath = filePath;
    }

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
                Task t = parseLine(line);
                loadedTasks.add(t);
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
        String doneFlag = parts[1];
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
            DateTimeFormatter deadlineFormat = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
            LocalDateTime deadlineDateTime = LocalDateTime.parse(parts[3], deadlineFormat);
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

        if (doneFlag.equals("1")) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }

        return task;
    }

}
