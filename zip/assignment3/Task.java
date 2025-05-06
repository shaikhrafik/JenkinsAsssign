package assignment3;

public class Task {
    private int taskID;
    private String description;
    private String dueDate;
    private String status;
    
    public Task(int taskID, String description, String dueDate) {
        this.taskID = taskID;
        this.description = description;
        this.dueDate = dueDate;
        this.status = "Pending"; // Default status
    }
    
    // Getters and setters
    public int getTaskID() {
        return taskID;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Task ID: " + taskID + 
               "\nDescription: " + description + 
               "\nDue Date: " + dueDate + 
               "\nStatus: " + status;
    }
}