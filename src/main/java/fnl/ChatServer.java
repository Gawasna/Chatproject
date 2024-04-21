package fnl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChatServer {

    private static final int PORT = 12345;
    //private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Set<User> users = new HashSet<>();
    private static Map<String, PrintWriter> userWriters = new HashMap<>();
    private static HashMap<String, ClientHandler> clients = new HashMap<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Server console");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextArea logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(400, 300);
        frame.setVisible(true);

        try {
            ServerSocket serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName("0.0.0.0"));
            logArea.append("Server started on port " + PORT + "\n");

            while (true) {
                //Socket socket = serverSocket.accept();
                
                //ClientHandler clientHandler = new ClientHandler(socket, logArea);
                //clients.put(clientHandler.getUsername(), clientHandler);
                //new Thread(clientHandler).start();
                
                new ClientHandler(serverSocket.accept()).start();
                logArea.append("New client connected: " + serverSocket + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;
        private User currentUser;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    String loginMessage = reader.readLine();
                    if (loginMessage != null) {
                        if (loginMessage.equals("guest")) {
                            currentUser = new User("Guest@" + generateRandomNumber(), User.AccountType.GUEST_LOGIN);
                            writer.println("login_success:" + currentUser.getUsername());
                            break;
                        } else if (loginMessage.startsWith("login:")) {
                            String[] parts = loginMessage.split(":");
                            String username = parts[1];
                            String password = parts[2];
                            if (checkLogin(username, password)) {
                                currentUser = new User(username, User.AccountType.DATABASE_LOGIN);
                                writer.println("login_success:" + currentUser.getUsername());
                                break;
                            } else {
                                writer.println("login_fail");
                            }
                        }
                    }
                }

                users.add(currentUser);
                userWriters.put(currentUser.getUsername(), writer);
                broadcastUserList();

                String message;
                while ((message = reader.readLine()) != null) {
                    if (message.equalsIgnoreCase("exit")) {
                        break;
                    }
                    if (currentUser.getAccountType() == User.AccountType.DATABASE_LOGIN) {
                        if (message.startsWith("@")) { // Direct message
                            String[] parts = message.split(" ", 2);
                            String recipient = parts[0].substring(1);
                            String content = parts[1];
                            sendMessageToUser(recipient, currentUser.getUsername(), content);
                        } else if (message.equalsIgnoreCase("general chat")) { // General chat
                            broadcastMessage(currentUser.getUsername(), "entered General Chat");
                        }
                    } else { // GUEST_LOGIN
                        if (message.equalsIgnoreCase("general chat")) { // General chat
                            broadcastMessage(currentUser.getUsername(), "entered General Chat");
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (currentUser != null) {
                    users.remove(currentUser);
                    userWriters.remove(currentUser.getUsername());
                    broadcastUserList();
                }
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private boolean checkLogin(String username, String password) {
            try {
            String url = "jdbc:mysql://localhost:3306/clientchatdb";
            String dbUsername = "root";
            String dbPassword = "";
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Tài khoản tồn tại");
                return true;
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi hoặc tài khoản không tồn tại");
            return false;
        }
            return false;
        }
        //Đăng nhập khách ngẫu nhiên
        private boolean randomLogin(String username) {
        	try {
        		
        	}
        }

        private void broadcastUserList() {
            StringBuilder userList = new StringBuilder();
            for (User user : users) {
                userList.append(user.getUsername()).append(",");
            }
            for (PrintWriter writer : userWriters.values()) {
                writer.println(userList);
            }
        }

        private void broadcastMessage(String sender, String message) {
            for (PrintWriter writer : userWriters.values()) {
                writer.println("@" + sender + ":" + message);
            }
        }

        private void sendMessageToUser(String recipient, String sender, String message) {
            PrintWriter writer = userWriters.get(recipient);
            if (writer != null) {
                writer.println("@" + sender + ":" + message);
            }
        }

        private String generateRandomNumber() {
            Random rand = new Random();
            int randomNumber = rand.nextInt(90000) + 10000;
            return String.valueOf(randomNumber);
        }
    }
}
