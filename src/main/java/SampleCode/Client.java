/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SampleCode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private String serverAddress;
    private int serverPort;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void start() {
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to server");

            // Start listening for messages from the server
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String message;
                    try {
                        while ((message = in.readLine()) != null) {
                            System.out.println("Server: " + message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            // Read input from user and send to server
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String input;
            while ((input = userInput.readLine()) != null) {
                out.println(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String serverAddress = "localhost"; // Địa chỉ IP của máy chủ
        int serverPort = 12345; // Cổng của máy chủ
        Client client = new Client(serverAddress, serverPort);
        client.start();
    }
}
