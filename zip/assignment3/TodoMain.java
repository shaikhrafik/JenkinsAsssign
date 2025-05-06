package assignment3;

public class TodoMain {
    public static void main(String[] args) {
        
        TodoListManager manager = new TodoListManager();
        TodoListView view = new TodoListView();
        TodoListController controller = new TodoListController(manager, view);
        
        
        controller.run();
    }
}
