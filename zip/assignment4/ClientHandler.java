package assignment4;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ChatRoom chatRoom;
    private User user;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean running;
    
    public ClientHandler(Socket socket, ChatRoom chatRoom) {
        this.clientSocket = socket;
        this.chatRoom = chatRoom;
        this.running = true;
    }
    
    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            
            writer.println("Welcome to the chat server!");
            writer.println("Please enter your username:");
            String username = reader.readLine();
            
            user = new User(username, clientSocket);
            
            if (!chatRoom.addUser(user)) {
                writer.println("Username already taken. Connection closed.");
                closeConnection();
                return;
            }
            
            writer.println("Connected to chat room: " + chatRoom.getName());
            writer.println("Type '/help' for commands, '/quit' to exit");
            
            for (Message msg : chatRoom.getRecentMessages(10)) {
                writer.println(msg.format());
            }
            
            String inputLine;
            while (running && (inputLine = reader.readLine()) != null) {
                if (inputLine.startsWith("/")) {
                    handleCommand(inputLine);
                } else {
                    Message message = new Message(user.getUsername(), "ALL", inputLine);
                    chatRoom.processMessage(message);
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            if (user != null) {
                chatRoom.removeUser(user.getUsername());
            }
            closeConnection();
        }
    }
    
    private void handleCommand(String command) {
        String[] parts = command.split("\\s+", 2);
        String cmd = parts[0].toLowerCase();
        
        switch (cmd) {
            case "/quit":
                writer.println("Goodbye!");
                running = false;
                break;
                
            case "/help":
                writer.println("Available commands:");
                writer.println("/help - Show this help");
                writer.println("/quit - Exit the chat");
                writer.println("/users - List all users");
                writer.println("/msg <username> <message> - Send private message");
                writer.println("/status <status> - Change your status");
                break;
                
            case "/users":
                writer.println("Users in chat room:");
                for (String name : chatRoom.getUserList()) {
                    User u = chatRoom.getUser(name);
                    writer.println("- " + name + " (" + u.getStatus() + ")");
                }
                break;
                
            case "/msg":
                if (parts.length < 2) {
                    writer.println("Usage: /msg <username> <message>");
                    return;
                }
                
                String[] msgParts = parts[1].split("\\s+", 2);
                if (msgParts.length < 2) {
                    writer.println("Usage: /msg <username> <message>");
                    return;
                }
                
                String recipient = msgParts[0];
                String messageContent = msgParts[1];
                
                Message privateMsg = new Message(user.getUsername(), recipient, messageContent);
                if (!chatRoom.sendPrivateMessage(privateMsg)) {
                    writer.println("User not found: " + recipient);
                }
                break;
                
            case "/status":
                if (parts.length < 2) {
                    writer.println("Usage: /status <status>");
                    return;
                }
                user.setStatus(parts[1]);
                writer.println("Status updated to: " + parts[1]);
                break;
                
            default:
                writer.println("Unknown command. Type /help for available commands.");
        }
    }
    
    private void closeConnection() {
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}