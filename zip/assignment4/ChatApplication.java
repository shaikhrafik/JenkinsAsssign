package assignment4;

public class ChatApplication {
    private static final int DEFAULT_SERVER_PORT = 9000;
    
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("server")) {
            int port = DEFAULT_SERVER_PORT;
            if (args.length > 1) {
                try {
                    port = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid port number. Using default: " + DEFAULT_SERVER_PORT);
                }
            }
            
            ChatServer server = new ChatServer(port);
            server.start();
            
        } else {
            String hostname = "localhost";
            int port = DEFAULT_SERVER_PORT;
            
            if (args.length > 0) {
                hostname = args[0];
            }
            
            if (args.length > 1) {
                try {
                    port = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid port number. Using default: " + DEFAULT_SERVER_PORT);
                }
            }
            
            System.out.println("Connecting to chat server at " + hostname + ":" + port);
            ChatClient client = new ChatClient(hostname, port);
            client.connect();
        }
    }
}
