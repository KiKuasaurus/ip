import java.util.Scanner;
import java.util.ArrayList;

public class Ontos {
    public static String hello = " Hello! I'm Ontos \n"
            + " What can I do for you?\n";
    public static String line = "____________________________________________________________\n";
    public static String bye = " Bye. Hope to see you again soon!\n";
    public static String listPrompt = " Here are the tasks in your list:";
    public static String completeTaskPrompt = " Nice! I've marked this task as done:\n   ";
    public static String uncompleteTaskPrompt = " OK, I've marked this task as not done yet:\n   ";
    public static String taskAdded = " Got it. I've added this task:\n";

    public static void main(String[] args) {
        // String logo = " ____        _        \n"
        //         + "|  _ \\ _   _| | _____ \n"
        //         + "| | | | | | | |/ / _ \\\n"
        //         + "| |_| | |_| |   <  __/\n"
        //         + "|____/ \\__,_|_|\\_\\___|\n";
        
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println(line + hello + line);

        while (true){
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println(line + bye + line);
                break;
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println(line + listPrompt);
                for (int i = 0; i < tasks.size(); i++) {
                    int j = i + 1;
                    System.out.println(" " + j + ". " + tasks.get(i).toString());
                }
                System.out.println(line);
            } else if (input.startsWith("mark")) {
                int index = 0;
                try {
                    index = Integer.parseInt(input.split(" ")[1]) - 1;
                } catch (Exception e) {
                    System.out.println(line 
                        + "The correct usage of 'mark' is: mark n, where n is a natural number (ℕ).\n" 
                        + line);
                    continue;
                }

                try {
                    tasks.get(index).completeTask();
                } catch (Exception e) {
                    System.out.println(line + "I'm sorry, but this task doesn't exist.\n" + line);
                    continue;
                }
                System.out.println(line + completeTaskPrompt + tasks.get(index).toString() + "\n" + line); 
            } else if (input.startsWith("unmark")) {
                int index = 0;
                try {
                    index = Integer.parseInt(input.split(" ")[1]) - 1;
                } catch (Exception e) {
                    System.out.println(line 
                        + "The correct usage of 'unmark' is: unmark n, where n is a natural number (ℕ).\\n"
                        + line);
                    continue;
                }

                try {
                    tasks.get(index).uncompleteTask();
                } catch (Exception e) {
                    System.out.println(line + "I'm sorry, but this task doesn't exist.\n" + line);
                    continue;
                }
                System.out.println(line + uncompleteTaskPrompt + tasks.get(index).toString() + "\n" + line); 
            } else if (input.startsWith("todo")) {
                try {
                    tasks.add(Task.toDo(input.split(" ", 2)[1]));
                } catch (Exception e) {
                    System.out.println(line + " OOPS!!! The description of a todo cannot be empty.\n" + line);
                    continue;
                }

                System.out.println(line 
                        + taskAdded 
                        + " " + tasks.get(tasks.size() - 1).toString() + "\n" 
                        + " Now you have " + tasks.size() + " tasks in the list.\n" 
                        + line);
            } else if (input.startsWith("deadline")) {
                try {
                    int startOfDesc = input.indexOf(" ");
                    int endOfDesc = input.indexOf(" /by");

                    String description = input.substring(startOfDesc, endOfDesc).trim();
                    String dueBy = input.substring(endOfDesc + 4).trim();

                    tasks.add(Task.deadline(description, dueBy));
                } catch (Exception e) {
                    System.out.println(line + " OOPS!!! The description of a deadline cannot be empty.\n" + line);
                    continue;
                }

                System.out.println(line 
                        + taskAdded 
                        + " " + tasks.get(tasks.size() - 1).toString() + "\n" 
                        + " Now you have " + tasks.size() + " tasks in the list.\n" 
                        + line);
            } else if (input.startsWith("event")) {
                try {
                    int startOfDesc = input.indexOf(" ");
                    int endOfDesc = input.indexOf(" /from");
                    int endOfFrom = input.indexOf(" /to");

                    String description = input.substring(startOfDesc, endOfDesc).trim();
                    String start = input.substring(endOfDesc + 6, endOfFrom).trim();
                    String end = input.substring(endOfFrom + 4).trim();

                    tasks.add(Task.event(description, start, end));
                } catch (Exception e) {
                System.out.println(line + " OOPS!!! The description of a event cannot be empty.\n" + line);
                continue;
                }

                System.out.println(line 
                        + taskAdded 
                        + " " + tasks.get(tasks.size() - 1).toString() + "\n" 
                        + " Now you have " + tasks.size() + " tasks in the list.\n" 
                        + line);
            } else if (input.startsWith("delete")) {
                int index = 0;
                try {
                    index = Integer.parseInt(input.split(" ")[1]) - 1;
                } catch (Exception e) {
                    System.out.println(line 
                            + "The correct usage of 'delete' is: delete n, where n is a natural number (ℕ).\\n" 
                            + line);
                    continue;
                }
                
                Task task = null;
                try {
                    task = tasks.get(index);
                    tasks.remove(index);
                } catch (Exception e) {
                    System.out.println(line + "I'm sorry, but this task doesn't exist.\n" + line);
                    continue;
                }
                System.out.println(line 
                        + " Noted. I've removed this task:\n" 
                        + " " + task.toString() + "\n"
                        + line);
            } else {
                System.out.println(line + " OOPS!!! I'm sorry, but I don't know what that means :-(" + line);
            }
        }
        sc.close();
    }
}
