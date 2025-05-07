package assignment4;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime timestamp;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getSender() {
        return sender;
    }
    
    public String getReceiver() {
        return receiver;
    }
    
    public String getContent() {
        return content;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String format() {
        String formattedTime = timestamp.format(formatter);
        if ("ALL".equals(receiver)) {
            return String.format("[%s] %s: %s", formattedTime, sender, content);
        } else {
            return String.format("[%s] [Private from %s]: %s", formattedTime, sender, content);
        }
    }
    
    public static Message fromProtocol(String protocolString) {
        String[] parts = protocolString.split(":", 4);
        if (parts.length < 4 || !parts[0].equals("MSG")) {
            throw new IllegalArgumentException("Invalid message format");
        }
        
        return new Message(parts[1], parts[2], parts[3]);
    }
    
    public String toProtocol() {
        return String.format("MSG:%s:%s:%s", sender, receiver, content);
    }
}
