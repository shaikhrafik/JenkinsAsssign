package assignment4;

import java.io.*;
import java.net.*;

public class User {
    private String username;
    private String status;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    
    public User(String username, Socket socket) throws IOException {
        this.username = username;
        this.status = "Online";
        this.socket = socket;
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    public PrintWriter getWriter() {
        return writer;
    }
    
    public BufferedReader getReader() {
        return reader;
    }
    
    public void closeConnection() {
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error closing user resources: " + e.getMessage());
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return username.equals(user.username);
    }
    
    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
