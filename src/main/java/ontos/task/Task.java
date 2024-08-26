package ontos.task;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a task with a description and completion status.
 */
public abstract class Task {
    /** String representation of the description of the task */
    protected String description;
    /** Boolean value representing if the task has been completed */
    protected boolean isDone;

    /**
     * Constructs a Task with the specified description and sets the task as not completed.
     *
     * @param description The description of the task.
     */
    protected Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructs a Task with the specified description and completion status.
     *
     * @param description The description of the task.
     * @param isDone      Whether the task is completed.
     */
    protected Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns a ToDo task. 
     * Is a factory method.
     *
     * @param description The description of the task.
     * @return A new ToDo Task.
     */
    public static Task toDo(String description) {
        return new ToDo(description);
    }

    /**
     * Returns a ToDo task with a specified completion status.
     * Is a factory method.
     *
     * @param description The description of the task.
     * @param isDone      Whether the task is completed.
     * @return A new ToDo Task.
     */
    public static Task toDo(String description, boolean isDone) {
        return new ToDo(description, isDone);
    }

    /**
     * Returns a Deadline task.
     * Is a factory method.
     *
     * @param description The description of the task.
     * @param time        The due date of the task.
     * @return A new Task representing a Deadline.
     */
    public static Task deadline(String description, LocalDate time) {
        return new Deadline(description, time);
    }

    /**
     * Returns a Deadline task with a specified completion status.
     * is a factory method
     *
     * @param description The description of the task.
     * @param isDone      Whether the task is completed.
     * @param time        The due date of the task.
     * @return A new Task representing a Deadline.
     */
    public static Task deadline(String description, boolean isDone, LocalDate time) {
        return new Deadline(description, isDone, time);
    }

    /**
     * Returns an Event task.
     * Is a factory method.
     *
     * @param description The description of the task.
     * @param start       The start date of the event.
     * @param end         The end date of the event.
     * @return A new Task representing an Event.
     */
    public static Task event(String description, LocalDate start, LocalDate end) {
        return new Event(description, start, end);
    }

    /**
     * Returns an Event task with a specified completion status.
     * Is a factory method.
     *
     * @param description The description of the task.
     * @param isDone      Whether the task is completed.
     * @param start       The start date of the event.
     * @param end         The end date of the event.
     * @return A new Task representing an Event.
     */
    public static Task event(String description, boolean isDone, LocalDate start, LocalDate end) {
        return new Event(description, isDone, start, end);
    }

    /**
     * Returns the status icon of the task, indicating whether it is completed.
     *
     * @return A string representing the status icon.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the status data of the task, used for storing and parsing tasks.
     *
     * @return A string representing the status data.
     */
    public String getStatusData() {
        return (isDone ? "1" : "0"); // modified status icon used for storing and parsing
    }

    /**
     * Marks the task as completed.
     */
    public void completeTask() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void uncompleteTask() {
        this.isDone = false;
    }

    /**
     * Returns the string representation of the task.
     *
     * @return A string representation of the task.
     */
    @Override
    public String toString() {
        String statusIcon = this.getStatusIcon();
        return "[" + statusIcon + "] " + this.description;
    }

    /**
     * Converts the task into a string representation suitable for storing in a file.
     *
     * @return A string representing the task data for storage.
     */
    abstract public String storeData();

    /**
     * Represents a ToDo task, a simple task without any time component.
     */
    protected static class ToDo extends Task {
        /**
         * Constructs a ToDo task with the specified description.
         *
         * @param description The description of the ToDo task.
         */
        public ToDo(String description) {
            super(description);
        }

        /**
         * Constructs a {@code ToDo} task with the specified description and completion status.
         *
         * @param description The description of the ToDo task.
         * @param isDone      Whether the task is completed.
         */
        public ToDo(String description, boolean isDone) {
            super(description, isDone);
        }

        /**
         * Returns the string representation of the ToDo task.
         *
         * @return A string representation of the ToDo task.
         */
        @Override
        public String toString() {
            String body = super.toString();
            return "[T]" + body;
        }

        /**
         * Converts the ToDo task into a string representation suitable for storing in a file.
         *
         * @return A string representing the ToDo task data for storage.
         */
        @Override
        public String storeData() {
            String statusIcon = this.getStatusData();
            return "T " + statusIcon + " /d" + this.description;
        }

        /**
         * Compares this ToDo task to another object for equality.
         * Used for JUnit tests.
         *
         * @param o The object to compare this ToDo task with.
         * @return {@code true} if the specified object is equal to this ToDo task; {@code false} otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ToDo toDo = (ToDo) o;
            return Objects.equals(this.description, toDo.description);
        }
    }

    /**
     * Represents a Deadline task, a task with a specific due date.
     */
    protected static class Deadline extends Task {
        /** The date that the task is due */
        protected LocalDate time;

        /**
         * Constructs a Deadline task with the specified description and due date.
         *
         * @param description The description of the Deadline task.
         * @param time        The due date of the task.
         */
        public Deadline(String description, LocalDate time) {
            super(description);
            this.time = time;
        }

        /**
         * Constructs a Deadline task with the specified description, completion status, and due date.
         *
         * @param description The description of the Deadline task.
         * @param isDone      Whether the task is completed.
         * @param time        The due date of the task.
         */
        public Deadline(String description, boolean isDone, LocalDate time) {
            super(description, isDone);
            this.time = time;
        }

        /**
         * Returns the string representation of the Deadline task.
         *
         * @return A string representation of the Deadline task.
         */
        @Override
        public String toString() {
            String body = super.toString();
            return "[D]" + body + " (by: " + time + ")";
        }

        /**
         * Converts the Deadline task into a string representation suitable for storing in a file.
         *
         * @return A string representing the Deadline task data for storage.
         */
        @Override
        public String storeData() {
            String statusIcon = this.getStatusData();
            return "D " + statusIcon + " /d" + this.description + " /t" + this.time;
        }
    }

    /**
     * Represents an Event task, a task with a start date and an end date.
     */
    protected static class Event extends Task {
        /** The date the event starts */
        protected LocalDate start;
        /** The date the event ends */
        protected LocalDate end;

        /**
         * Constructs an Event task with the specified description, start date, and end date.
         *
         * @param description The description of the Event task.
         * @param start       The start date of the event.
         * @param end         The end date of the event.
         */
        public Event(String description, LocalDate start, LocalDate end) {
            super(description);
            this.start = start;
            this.end = end;
        }

        /**
         * Constructs an Event task with the specified description, completion status, start date, and end date.
         *
         * @param description The description of the Event task.
         * @param isDone      Whether the task is completed.
         * @param start       The start date of the event.
         * @param end         The end date of the event.
         */
        public Event(String description, boolean isDone, LocalDate start, LocalDate end) {
            super(description, isDone);
            this.start = start;
            this.end = end;
        }

        /**
         * Returns the string representation of the Event task.
         *
         * @return A string representation of the Event task.
         */
        @Override
        public String toString() {
            String body = super.toString();
            return "[E]" + body + " (from: " + start + " to: " + end + ")";
        }

        /**
         * Converts the Event task into a string representation suitable for storing in a file.
         *
         * @return A string representing the Event task data for storage.
         */
        @Override
        public String storeData() {
            String statusIcon = this.getStatusData();
            return "E " + statusIcon + " /d" + this.description + " /b" + this.start + " /e" + this.end;
        }
    }
}
