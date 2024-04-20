package SampleCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChatClient extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea contentChat;
    private JTextField messageInput;
    private JButton sendButton;
    private JList<String> onlineUsersList;
    private DefaultListModel<String> onlineUsersListModel;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private User currentUser;
    public String ipv4;
    public int PORT;

    public ChatClient() {
        setTitle("Chat Client");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        contentChat = new JTextArea();
        contentChat.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(contentChat);
        chatScrollPane.setPreferredSize(new Dimension(500, 400));
        add(chatScrollPane, BorderLayout.CENTER);

        onlineUsersListModel = new DefaultListModel<>();
        onlineUsersList = new JList<>(onlineUsersListModel);
        JScrollPane userListScrollPane = new JScrollPane(onlineUsersList);
        userListScrollPane.setPreferredSize(new Dimension(100, 400));
        add(userListScrollPane, BorderLayout.WEST);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        messageInput = new JTextField();
        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        inputPanel.add(messageInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
        loginDialog();
    }

    private void loginDialog() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JTextField ipv4Field = new JTextField();
    JTextField portField = new JTextField();
    JButton loginButton = new JButton("Login");
    JButton guestButton = new JButton("Guest Login");

    panel.add(new JLabel("Username:"));
    panel.add(usernameField);
    panel.add(new JLabel("Password:"));
    panel.add(passwordField);
    panel.add(new JLabel("IPv4 Address:"));
    panel.add(ipv4Field);
    panel.add(new JLabel("Port:"));
    panel.add(portField);
    panel.add(loginButton);
    panel.add(guestButton);

        loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String ipv4 = ipv4Field.getText().trim();
            String portStr = portField.getText().trim();
            if (!username.isEmpty() && !password.isEmpty() && !ipv4.isEmpty() && !portStr.isEmpty()) {
                try {
                    int port = Integer.parseInt(portStr);
                    socket = new Socket(ipv4, port);
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer = new PrintWriter(socket.getOutputStream(), true);

                    writer.println("login:" + username + ":" + password);
                    String response = reader.readLine();
                    if (response.startsWith("login_success")) {
                        currentUser = new User(response.substring(13), User.AccountType.DATABASE_LOGIN);
                        disposeLoginDialog();
                        startServerListener();
                    } else {
                        JOptionPane.showMessageDialog(null, "Login failed. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException | IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Invalid port number or IPv4 address.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    guestButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ipv4 = ipv4Field.getText().trim();
            String portStr = portField.getText().trim();
            if (!ipv4.isEmpty() && !portStr.isEmpty()) {
                try {
                    int port = Integer.parseInt(portStr);
                    socket = new Socket(ipv4, port);
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer = new PrintWriter(socket.getOutputStream(), true);

                    writer.println("guest");
                    String response = reader.readLine();
                    if (response.startsWith("login_success")) {
                        currentUser = new User(response.substring(13), User.AccountType.GUEST_LOGIN);
                        disposeLoginDialog();
                        startServerListener();
                    } else {
                        JOptionPane.showMessageDialog(null, "Guest login failed. Please try again.", "Guest Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException | IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Invalid port number or IPv4 address.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    JOptionPane.showMessageDialog(null, panel, "Login", JOptionPane.PLAIN_MESSAGE);
}

    private void disposeLoginDialog() {
        SwingUtilities.getWindowAncestor((Component) sendButton).dispose();
    }

    private void startServerListener() {
        try {
            new Thread(new ServerListener()).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = messageInput.getText().trim();
        if (!message.isEmpty()) {
            writer.println(message);
            messageInput.setText("");
        }
    }

    private class ServerListener implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    String message = reader.readLine();
                    if (message != null) {
                        if (message.startsWith("@")) { // Message from another user
                            String[] parts = message.split(":", 2);
                            String sender = parts[0].substring(1);
                            String content = parts[1];

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String timeStamp = dateFormat.format(new Date());

                            contentChat.append("@" + sender + ":\n" + content + "\n");
                            contentChat.append("Sent at " + timeStamp + "\n\n");
                        } else { // Message from the server, which is a list of online users
                            String[] users = message.split(",");
                            onlineUsersListModel.clear();
                            for (String user : users) {
                                onlineUsersListModel.addElement(user);
                            }
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatClient().setVisible(true);
        });
    }
}
