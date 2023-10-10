package simplest.mrcha;

import java.net.*;
import java.io.*;
 
public class ConsoleClient {
 
    public static void main(String[] args) {
        //if (args.length < 2) return;
 
        String hostname = "localhost";// args[0];
        int port = 8888;//Integer.parseInt(args[1]);
 
        try (Socket socket = new Socket(hostname, port)) {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            Console console = System.console();
            String text;
 
            do {
                text = console.readLine("Enter text: ");
                writer.println(text);
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String time = reader.readLine();
                System.out.println(time);
            } while (!text.equals("bye"));
            socket.close();
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
