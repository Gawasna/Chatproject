/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SampleCode;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;

public class ChatAppExample extends JFrame {
    private JTextPane partnerChatPane;
    private JTextPane ownChatPane;

    public ChatAppExample() {
        setTitle("Chat Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Tạo JTextPane cho tin nhắn của đối tác
        partnerChatPane = new JTextPane();
        partnerChatPane.setContentType("text/html");
        partnerChatPane.setEditable(false);

        // Tạo JTextPane cho tin nhắn của bản thân
        ownChatPane = new JTextPane();
        ownChatPane.setContentType("text/html");
        ownChatPane.setEditable(false);

        // Tạo một JScrollPane cho mỗi JTextPane
        JScrollPane partnerScrollPane = new JScrollPane(partnerChatPane);
        JScrollPane ownScrollPane = new JScrollPane(ownChatPane);

        // Thiết lập bố trí của các JTextPane trong JFrame
        setLayout(new GridLayout(1, 2));
        add(partnerScrollPane);
        add(ownScrollPane);

        // Hiển thị giao diện
        setVisible(true);
    }

    // Phương thức để hiển thị tin nhắn của đối tác
    public void displayPartnerMessage(String message) {
        String html = "<div style='text-align:left;color:blue;'>" + message + "</div>";
        appendToPane(partnerChatPane, html);
    }

    // Phương thức để hiển thị tin nhắn của bản thân
    public void displayOwnMessage(String message) {
        String html = "<div style='text-align:right;color:green;'>" + message + "</div>";
        appendToPane(ownChatPane, html);
    }

    // Phương thức để thêm HTML vào JTextPane
    private void appendToPane(JTextPane textPane, String html) {
        HTMLDocument doc = (HTMLDocument) textPane.getDocument();
        try {
            doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), html);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatAppExample::new);
    }
}
