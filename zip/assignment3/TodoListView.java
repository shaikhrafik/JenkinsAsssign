package assignment3;

import java.util.LinkedList;

public class TodoListView {
    
    // Display the main menu
    public void displayMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Add Task");
        System.out.println("2. Remove Task");
        System.out.println("3. Update Task");
        System.out.println("4. Display All Tasks");
        System.out.println("5. Find Task");
        System.out.println("6. Exit");
        System.out.print("Enter your choice (1-6): ");
    }
    
    // Display all tasks
    public void displayAllTasks(LinkedList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks in the list.");
            return;
        }
        
        System.out.println("\n===== TO-DO LIST =====");
        for (Task task : tasks) {
            System.out.println("\n" + task);
            System.out.println("---------------------");
        }
    }
    
    // Display a single task
    public void displayTask(Task task) {
        if (task != null) {
            System.out.println("\nTask found:");
            System.out.println(task);
        } else {
            System.out.println("Task not found.");
        }
    }
    
    // Display messages
    public void displayMessage(String message) {
        System.out.println(message);
    }
    
    // Display task added confirmation
    public void displayTaskAdded(int taskID) {
        System.out.println("Task added successfully with ID: " + taskID);
    }
}