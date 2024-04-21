/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chatproject.assets;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient {
    private BufferedReader reader;
    private PrintWriter writer;
    private JFrame frame = new JFrame("Chat Client");
    private JTextField textField = new JTextField(40);
    private JTextArea textArea = new JTextArea(8, 40);
    private DefaultListModel<String> userListModel = new DefaultListModel<>();
    private JList<String> userList = new JList<>(userListModel);

    public ChatClient() {
        textArea.setEditable(false);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.getContentPane().add(new JScrollPane(userList), BorderLayout.EAST);
        frame.pack();

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writer.println(textField.getText());
                textField.setText("");
            }
        });
    }

    private String getUsername() {
        return JOptionPane.showInputDialog(frame, "Enter your username:");
    }

    private void run() {
        String serverAddress = JOptionPane.showInputDialog(frame, "Enter IP Address of the Server:");
        try {
            Socket socket = new Socket(serverAddress, 12345);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String line = reader.readLine();
                if (line.startsWith("Enter your username:")) {
                    writer.println(getUsername());
                } else if (line.startsWith("null has left the chat")) {
                    break;
                } else if (line.startsWith("USERLIST:")) {
                    updateUsers(line.substring(9));
                } else {
                    textArea.append(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUsers(String userListString) {
        String[] users = userListString.split(",");
        userListModel.clear();
        for (String user : users) {
            userListModel.addElement(user);
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }
}
