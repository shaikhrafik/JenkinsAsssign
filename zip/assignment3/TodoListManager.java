package assignment3;

import java.util.LinkedList;
import java.util.Iterator;

public class TodoListManager {
    private LinkedList<Task> tasks;
    private int nextTaskID;
    
    public TodoListManager() {
        tasks = new LinkedList<>();
        nextTaskID = 1;
    }
    
    // Add a new task
    public int addTask(String description, String dueDate) {
        Task newTask = new Task(nextTaskID++, description, dueDate);
        tasks.add(newTask);
        return newTask.getTaskID();
    }
    
    // Remove a task by ID
    public boolean removeTask(int taskID) {
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getTaskID() == taskID) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    
    // Update a task's details
    public boolean updateTask(int taskID, String description, String dueDate, String status) {
        for (Task task : tasks) {
            if (task.getTaskID() == taskID) {
                if (description != null && !description.trim().isEmpty()) {
                    task.setDescription(description);
                }
                if (dueDate != null && !dueDate.trim().isEmpty()) {
                    task.setDueDate(dueDate);
                }
                if (status != null && !status.trim().isEmpty()) {
                    task.setStatus(status);
                }
                return true;
            }
        }
        return false;
    }
    
    // Get all tasks
    public LinkedList<Task> getAllTasks() {
        return new LinkedList<>(tasks); // Return a copy to preserve encapsulation
    }
    
    // Find a task by ID
    public Task findTask(int taskID) {
        for (Task task : tasks) {
            if (task.getTaskID() == taskID) {
                return task;
            }
        }
        return null;
    }
    
    // Get the number of tasks
    public int getTaskCount() {
        return tasks.size();
    }
    
    // Check if the task list is empty
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}