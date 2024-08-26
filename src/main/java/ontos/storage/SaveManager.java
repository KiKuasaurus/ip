package ontos.storage;

import java.util.Scanner;
import java.util.HashMap;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import ontos.task.TaskList;
import ontos.task.Task;

/**
 * Reads and writes the task list from and to storage. 
 */
public class SaveManager {
    /** Path to the folder that the file will be saved */
    protected Path savePath;
    /** Path to the file that will be saved */
    protected Path saveFilePath;
    /** String representation of the name of the file that will be saved */
    protected String saveFileName;
    
    protected static HashMap<Character, Boolean> IS_DONE = new HashMap<>() {{
        put('0', false);
        put('1', true);
    }};

    /**
     * Constructs a Save Manager with the specified root path and save file name.
     *
     * @param rootPath     The root directory where the data folder will be created.
     * @param saveFileName The name of the save file (without extension).
     */
    public SaveManager(Path rootPath, String saveFileName) {
        this.savePath = rootPath.resolve("data");
        this.saveFileName = saveFileName;
        this.saveFilePath = savePath.resolve(saveFileName + ".txt");
    }

    /**
     * Creates the necessary directories and the save file if they do not already exist.
     */
    public void createSave() {
        try {
            Files.createDirectory(savePath);
            Files.createFile(saveFilePath);
        } catch (IOException e) {
            // File already exists, don't need to do anything
        }
    }

    /**
     * Loads tasks from the save file into a TaskList.
     * If the file is not found, an empty TaskList is returned.
     *
     * @return A TaskList containing the tasks loaded from the save file.
     */
    public TaskList loadSave() {
        try (Scanner sc = new Scanner(saveFilePath.toFile())) {
            TaskList tasks = new TaskList();
            while (sc.hasNextLine()) {
                try {
                    Task task = stringToTask(sc.nextLine());
                    tasks.addTask(task);
                } catch (StringIndexOutOfBoundsException | IllegalArgumentException | DateTimeParseException e) {
                    System.out.println("Data is corrupted: " + e.toString());
                }
            }
            return tasks;
        } catch (FileNotFoundException e) {
            return new TaskList();
        }
    }

    /**
     * Converts a string from the save file into a Task object.
     * 
     * @param input The stored string representation of the task.
     * @return The Task object created from the input string.
     * @throws StringIndexOutOfBoundsException If the input string is malformed.
     * @throws NumberFormatException If a number in the input string cannot be parsed.
     * @throws IllegalArgumentException If the task type or completion status is invalid.
     * @throws DateTimeParseException If a date in the input string cannot be parsed.
     */
    private Task stringToTask(String input) throws StringIndexOutOfBoundsException, 
            NumberFormatException, IllegalArgumentException, DateTimeParseException {

        Boolean isDone = IS_DONE.get(input.charAt(2));
        if (isDone == null) {
            throw new IllegalArgumentException();
        }
        if (input.startsWith("T")) {
            String description = input.substring(input.indexOf(" /d") + 3).trim();
            return Task.toDo(description, isDone);
        } else if (input.startsWith("D")) {
            int startOfDesc = input.indexOf(" /d");
            int startOfTime = input.indexOf(" /t");

            String description = input.substring(startOfDesc + 3, startOfTime);
            LocalDate time = LocalDate.parse(input.substring(startOfTime + 3).trim());

            return Task.deadline(description, isDone, time);
        } else if (input.startsWith("E")) {
            int startOfDesc = input.indexOf(" /d");
            int startOfStart = input.indexOf(" /b");
            int startOfEnd = input.indexOf(" /e");

            String description = input.substring(startOfDesc + 3, startOfStart);
            LocalDate start = LocalDate.parse(input.substring(startOfStart + 3, startOfEnd));
            LocalDate end = LocalDate.parse(input.substring(startOfEnd + 3).trim());

            return Task.event(description, isDone, start, end);
        } else {
            throw new IllegalArgumentException("Unsupported task type.");
        }
    }

    /**
     * Writes the tasks from the given TaskList to the save file.
     *
     * @param tasks The TaskList containing the tasks to be saved.
     * @throws IOException If an I/O error occurs while writing to the save file.
     */
    public void writeToSave(TaskList tasks) throws IOException {
        FileWriter fw = new FileWriter(saveFilePath.toString());
        String[] tasksToAdd = tasks.toSave();
        for (String task : tasksToAdd) {
            fw.write(task + System.lineSeparator());
        }
        fw.close();
    }
}
