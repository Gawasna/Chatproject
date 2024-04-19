/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SampleCode;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ServerManager extends JFrame {
    private JTextField portField;
    private JButton startButton;
    private JTextArea logArea;
    private JTable clientTable;
    private DefaultTableModel tableModel;

    private Server server;

    public ServerManager() {
        setTitle("Server Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for IP and Port settings
        JPanel settingsPanel = new JPanel(new FlowLayout());
        settingsPanel.add(new JLabel("Port: "));
        portField = new JTextField("12345", 8);
        settingsPanel.add(portField);

        // Start button
        startButton = new JButton("Start Server");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startButton.getText().equals("Start Server")) {
                    startServer();
                    startButton.setText("Stop Server");
                } else {
                    stopServer();
                    startButton.setText("Start Server");
                }
            }
        });
        settingsPanel.add(startButton);
        add(settingsPanel, BorderLayout.NORTH);

        // Log area
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        // Client table
        tableModel = new DefaultTableModel(new String[]{"Client Address", "Status"}, 0);
        clientTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(clientTable);
        add(tableScrollPane, BorderLayout.SOUTH);
    }

    private void startServer() {
        int port = Integer.parseInt(portField.getText());
        server = new Server(port);
        log("Server started on port " + port);
        server.start();
    }

    private void stopServer() {
        server.stop();
        log("Server stopped");
    }

    private void log(String message) {
        logArea.append(message + "\n");
    }

    // Method to add client to the table
    public void addClient(String address) {
        tableModel.addRow(new String[]{address, "Connected"});
    }

    // Method to remove client from the table
    public void removeClient(String address) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(address)) {
                tableModel.removeRow(i);
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ServerManager serverManager = new ServerManager();
                serverManager.setVisible(true);
            }
        });
    }
}
