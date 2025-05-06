package assignment3;

import java.util.Scanner;

public class TodoListController {
    private TodoListManager manager;
    private TodoListView view;
    private Scanner scanner;
    
    public TodoListController(TodoListManager manager, TodoListView view) {
        this.manager = manager;
        this.view = view;
        this.scanner = new Scanner(System.in);
    }
    
    // Main run method
    public void run() {
        boolean running = true;
        
        view.displayMessage("Welcome to the To-Do List Application!");
        
        while (running) {
            view.displayMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    removeTask();
                    break;
                case 3:
                    updateTask();
                    break;
                case 4:
                    displayAllTasks();
                    break;
                case 5:
                    findTask();
                    break;
                case 6:
                    view.displayMessage("Thank you for using the To-Do List Application. Goodbye!");
                    running = false;
                    break;
                default:
                    view.displayMessage("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
    
    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }
    
    private void addTask() {
        view.displayMessage("Enter task description: ");
        String description = scanner.nextLine();
        
        view.displayMessage("Enter due date (e.g., 2025-04-15): ");
        String dueDate = scanner.nextLine();
        
        int taskID = manager.addTask(description, dueDate);
        view.displayTaskAdded(taskID);
    }
    
    private void removeTask() {
        view.displayMessage("Enter task ID to remove: ");
        try {
            int taskID = Integer.parseInt(scanner.nextLine());
            boolean removed = manager.removeTask(taskID);
            
            if (removed) {
                view.displayMessage("Task removed successfully.");
            } else {
                view.displayMessage("Task not found.");
            }
        } catch (NumberFormatException e) {
            view.displayMessage("Invalid task ID. Please enter a number.");
        }
    }
    
    private void updateTask() {
        view.displayMessage("Enter task ID to update: ");
        try {
            int taskID = Integer.parseInt(scanner.nextLine());
            Task task = manager.findTask(taskID);
            
            if (task == null) {
                view.displayMessage("Task not found.");
                return;
            }
            
            view.displayMessage("\nCurrent task details:");
            view.displayTask(task);
            
            view.displayMessage("\nEnter new description (press Enter to keep current): ");
            String description = scanner.nextLine();
            
            view.displayMessage("Enter new due date (press Enter to keep current): ");
            String dueDate = scanner.nextLine();
            
            view.displayMessage("Enter new status (Pending/In Progress/Completed) (press Enter to keep current): ");
            String status = scanner.nextLine();
            
            boolean updated = manager.updateTask(taskID, description, dueDate, status);
            
            if (updated) {
                view.displayMessage("Task updated successfully.");
            } else {
                view.displayMessage("Failed to update task.");
            }
        } catch (NumberFormatException e) {
            view.displayMessage("Invalid task ID. Please enter a number.");
        }
    }
    
    private void displayAllTasks() {
        view.displayAllTasks(manager.getAllTasks());
    }
    
    private void findTask() {
        view.displayMessage("Enter task ID to find: ");
        try {
            int taskID = Integer.parseInt(scanner.nextLine());
            Task task = manager.findTask(taskID);
            view.displayTask(task);
        } catch (NumberFormatException e) {
            view.displayMessage("Invalid task ID. Please enter a number.");
        }
    }
}
