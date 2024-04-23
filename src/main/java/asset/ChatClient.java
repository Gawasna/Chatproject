/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asset;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class ChatClient {
    private Socket socket;
    private PrintWriter out;
    private JList<String> clientList;
    private DefaultListModel<String> clientListModel;
    private String conversationPartner;

    public ChatClient(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);

            Thread inputThread = new Thread(() -> {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Received message: " + message);
                        handleMessage(message);
                    }
                } catch (IOException e) {
                    System.err.println("Error handling server message: " + e.getMessage());
                }
            });
            inputThread.start();

            clientListModel = new DefaultListModel<>();
            clientList = new JList<>(clientListModel);
            JScrollPane scrollPane = new JScrollPane(clientList);
            // Add the scroll pane to the GUI

            clientList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int index = clientList.locationToIndex(e.getPoint());
                        String username = clientListModel.getElementAt(index);
                        if (conversationPartner == null || !conversationPartner.equals(username)) {
                            if (conversationPartner != null) {
                                out.println("DESELECT_PARTNER " + conversationPartner);
                            }
                            out.println("SELECT_PARTNER " + username);
                            conversationPartner = username;
                        }
                    }
                }
            });

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                if (conversationPartner != null) {
                    out.println("TO: " + conversationPartner + ": " + message);
                } else {
                    out.println("TO: " + message);
                }
            }
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    public void handleMessage(String message) {
        if (message.startsWith("CLIENT_LIST:")) {
            String[] parts = message.split(":");
            clientListModel.clear();
            for (int i = 1; i < parts.length; i++) {
                clientListModel.addElement(parts[i]);
            }
        } else if (message.startsWith("SELECT_PARTNER:")) {
            String[] parts = message.split(":");
            conversationPartner = parts[1];
        } else if (message.startsWith("DESELECT_PARTNER:")) {
            conversationPartner = null;
        } else {
            System.out.println("Received message: " + message);
        }
    }

    private void disconnect() {
        if (out != null) {
            out.close();
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java ChatClient <server address> <server port>");
            return;
        }

        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);

        ChatClient client = new ChatClient(serverAddress, serverPort);
    }
}