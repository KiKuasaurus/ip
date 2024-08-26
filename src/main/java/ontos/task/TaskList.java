package ontos.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    /** ArrayList used to store tasks */
    protected ArrayList<Task> taskList;

    /**
     * Constructs an empty task list.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added to the list.
     */
    public void addTask(Task task) {
        this.taskList.add(task);
    }

    /**
     * Marks the task at the specified index as completed.
     *
     * @param index The index of the task to be marked as completed (1-based index).
     * @throws IndexOutOfBoundsException If the index is out of range (index < 1 or index > size()).
     */
    public void completeTaskAt(int index) throws IndexOutOfBoundsException {
        this.taskList.get(index - 1).completeTask();
    }

    /**
     * Marks the task at the specified index as incomplete.
     *
     * @param index The index of the task to be marked as incomplete (1-based index).
     * @throws IndexOutOfBoundsException If the index is out of range (index < 1 or index > size()).
     */
    public void uncompleteTaskAt(int index) throws IndexOutOfBoundsException {
        this.taskList.get(index - 1).uncompleteTask();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks in the list.
     */
    public int getSize() {
        return taskList.size();
    }

    /**
     * Removes the task at the specified index from the list and returns it.
     *
     * @param index The index of the task to be removed (1-based index).
     * @return The removed task.
     * @throws IndexOutOfBoundsException If the index is out of range (index < 1 or index > size()).
     */
    public Task removeTaskAt(int index) throws IndexOutOfBoundsException {
        Task removedTask = taskList.get(index - 1);
        taskList.remove(index - 1);
        return removedTask;
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index The index of the task to be retrieved (1-based index).
     * @return The task at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of range (index < 1 or index > size()).
     */
    public Task getTaskAt(int index) {
        return taskList.get(index - 1);
    }

    /**
     * Returns the string representation of the task at the specified index.
     *
     * @param index The index of the task to be converted to a string (1-based index).
     * @return The string representation of the task.
     * @throws IndexOutOfBoundsException If the index is out of range (index < 1 or index > size()).
     */
    public String getTaskString(int index) {
        return taskList.get(index - 1).toString();
    }

    /**
     * Returns a string representation of the task list, with each task on a new line.
     *
     * @return The string representation of the task list.
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.getSize(); i++) {
            int index = i + 1;
            output.append(" ").append(index).append(". ").append(taskList.get(i).toString());
            if (index < this.getSize()) {
                output.append("\n");
            }
        }
        return output.toString();
    }

    /**
     * Returns an array of strings representing the data of each task in the list.
     * This is used to save the task list to a file.
     *
     * @return An array of strings representing the task data.
     */
    public String[] toSave() {
        String[] output = new String[this.getSize()];
        for (int i = 0; i < this.getSize(); i++) {
            output[i] = taskList.get(i).storeData();
        }
        return output;
    }
}
