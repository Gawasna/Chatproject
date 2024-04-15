package SampleCode;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<User> users = new HashSet<>();
    private static Map<String, PrintWriter> userWriters = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running...");
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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
            // You need to implement your own logic to check login from database
            // For demo purposes, we assume all logins are successful
            return true;
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
