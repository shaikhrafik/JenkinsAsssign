package assignment4;

import java.io.*;
import java.net.*;

public class ChatClient {
    private String hostname;
    private int port;
    private String username;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private BufferedReader consoleReader;
    private boolean running;
    
    public ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public void connect() {
        try {
            socket = new Socket(hostname, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            MessageReceiver messageReceiver = new MessageReceiver();
            Thread receiverThread = new Thread(messageReceiver);
            receiverThread.setDaemon(true);
            receiverThread.start();
            
            String welcomeMsg = reader.readLine();
            System.out.println(welcomeMsg);
            
            String usernamePrompt = reader.readLine();
            System.out.println(usernamePrompt);
            
            username = consoleReader.readLine();
            writer.println(username);
            
            running = true;
            String input;
            while (running && (input = consoleReader.readLine()) != null) {
                if ("/quit".equals(input)) {
                    writer.println(input);
                    break;
                }
                writer.println(input);
            }
            
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        } finally {
            disconnect();
        }
    }
    
    public void disconnect() {
        running = false;
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error during disconnect: " + e.getMessage());
        }
    }
    
    private class MessageReceiver implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while (running && (message = reader.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                if (running) {
                    System.err.println("Connection to server lost: " + e.getMessage());
                    disconnect();
                }
            }
        }
    }
}
