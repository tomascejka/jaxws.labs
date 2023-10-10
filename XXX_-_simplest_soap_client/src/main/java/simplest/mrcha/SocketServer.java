package simplest.mrcha;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	
    public static void main(String[] args) {
        //if (args.length < 1) return;
        int port = 8888;//Integer.parseInt(args[0]);
 
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                new SocketThread(socket).start();
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
}
