/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chatproject.assets;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Set;

public class ChatClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    private JFrame frame = new JFrame("Chat Client");
    private JTextField textField = new JTextField(40);
    private JTextArea messageArea = new JTextArea(8, 40);
    private JList userList = new JList();
    private JButton sendButton = new JButton("Send");
    private DefaultListModel listModel = new DefaultListModel();

    private PrintWriter writer;

    public ChatClient() {
        userList.setModel(listModel);

        textField.setEditable(false);
        messageArea.setEditable(false);
        sendButton.setEnabled(false);

        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(userList), BorderLayout.WEST);
        frame.getContentPane().add(sendButton, BorderLayout.EAST);

        frame.pack();

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String message = textField.getText();
        writer.println(message);
        textField.setText("");
    }

    private String getUserName() {
        return textField.getText();
    }

    private void setUserName(String userName) {
        textField.setText(userName);
    }

    private void updateUserList(Set<String> userNames) {
        listModel.clear();
        for (String userName : userNames) {
            listModel.addElement(userName);
        }
    }

    private void setWritable(boolean writable) {
        textField.setEditable(writable);
        sendButton.setEnabled(writable);
    }

    private void showMessage(String message) {
        messageArea.append(message + "\n");
    }

    private void connectToServer() {
        try (
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
        ) {
            this.writer = writer;

            setUserName(getUserName());
            String serverMessage = "New user connected: " + getUserName();
            writer.println(serverMessage);

            Thread readThread = new Thread(new ReadThread(reader));
            readThread.start();

            setWritable(true);

        } catch (UnknownHostException ex) {
            System.err.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex.getMessage());
        }
    }

    private class ReadThread implements Runnable {
        private BufferedReader reader;

        public ReadThread(BufferedReader reader) {
            this.reader = reader;
        }

        public void run() {
            while (true) {
                try {
                    String response = reader.readLine();
                    if (response.startsWith("Connected users:")) {
                        updateUserList((Set<String>) Arrays.asList(response.split(":")));
                    } else if (response.startsWith("[")) {
                        showMessage(response);
                    } else if (response.startsWith("New user connected:")) {
                        showMessage(response);
                    } else if (response.startsWith("User disconnected:")) {
                        String userName = response.substring("User disconnected:".length());
                        listModel.removeElement(userName);
                        showMessage(response);
                    }
                } catch (IOException ex) {
                    System.err.println("Error reading from server: " + ex.getMessage());
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.connectToServer();
    }
}