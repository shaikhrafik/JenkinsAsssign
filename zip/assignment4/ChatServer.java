package assignment4;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ChatServer {
    private int port;
    private ServerSocket serverSocket;
    private boolean running;
    private ChatRoom chatRoom;
    private ExecutorService threadPool;
    
    public ChatServer(int port) {
        this.port = port;
        this.chatRoom = new ChatRoom("Main Room");
        this.threadPool = Executors.newCachedThreadPool();
    }
    
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            System.out.println("Chat server started on port " + port);
            
            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                    
                    ClientHandler clientHandler = new ClientHandler(clientSocket, chatRoom);
                    threadPool.execute(clientHandler);
                    
                } catch (IOException e) {
                    if (!running) break;
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start server: " + e.getMessage());
        } finally {
            stop();
        }
    }
    
    public void stop() {
        running = false;
        threadPool.shutdown();
        
        try {
            if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
        }
        
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server: " + e.getMessage());
        }
        
        System.out.println("Server stopped");
    }
}
