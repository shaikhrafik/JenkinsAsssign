package assignment4;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoom {
    private String name;
    private ConcurrentHashMap<String, User> users;
    private List<Message> messageHistory;
    
    public ChatRoom(String name) {
        this.name = name;
        this.users = new ConcurrentHashMap<>();
        this.messageHistory = Collections.synchronizedList(new ArrayList<>());
    }
    
    public String getName() {
        return name;
    }
    
    public boolean addUser(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        
        users.put(user.getUsername(), user);
        broadcastMessage(new Message("SERVER", "ALL", user.getUsername() + " has joined the chat."));
        return true;
    }
    
    public boolean removeUser(String username) {
        User user = users.remove(username);
        if (user != null) {
            broadcastMessage(new Message("SERVER", "ALL", username + " has left the chat."));
            user.closeConnection();
            return true;
        }
        return false;
    }
    
    public User getUser(String username) {
        return users.get(username);
    }
    
    public List<String> getUserList() {
        return new ArrayList<>(users.keySet());
    }
    
    public void broadcastMessage(Message message) {
        messageHistory.add(message);
        String formattedMessage = message.format();
        
        for (User user : users.values()) {
            try {
                user.getWriter().println(formattedMessage);
            } catch (Exception e) {
                System.err.println("Error sending message to " + user.getUsername() + ": " + e.getMessage());
            }
        }
    }
    
    public boolean sendPrivateMessage(Message message) {
        User receiver = users.get(message.getReceiver());
        if (receiver == null) {
            return false;
        }
        
        messageHistory.add(message);
        String formattedMessage = message.format();
        
        receiver.getWriter().println(formattedMessage);
        
        if (!message.getSender().equals(message.getReceiver())) {
            User sender = users.get(message.getSender());
            if (sender != null) {
                sender.getWriter().println(formattedMessage.replace("Private from", "Private to"));
            }
        }
        
        return true;
    }
    
    public void processMessage(Message message) {
        if ("ALL".equals(message.getReceiver())) {
            broadcastMessage(message);
        } else {
            sendPrivateMessage(message);
        }
    }
    
    public List<Message> getRecentMessages(int limit) {
        synchronized (messageHistory) {
            int size = messageHistory.size();
            int startIndex = Math.max(0, size - limit);
            return new ArrayList<>(messageHistory.subList(startIndex, size));
        }
    }
}
