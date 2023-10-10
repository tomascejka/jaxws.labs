package simplest.mrcha;

import java.io.*;
import java.net.*;

public class SocketThread extends Thread {
    private Socket socket;
 
    public SocketThread(Socket socket) {
        this.socket = socket;
    }
 
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            String text;
 
            do {
                text = reader.readLine();
                writer.println("Server: " + text);
 
            } while (!text.equals("exit"));
 
            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
