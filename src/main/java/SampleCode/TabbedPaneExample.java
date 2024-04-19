package SampleCode;
import javax.swing.*;
import java.awt.*;

public class TabbedPaneExample {
    public static void main(String[] args) {
        // Tạo một JFrame
        JFrame frame = new JFrame("Tabbed Pane Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Tạo một JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        JLabel label1 = new JLabel("This is Tab 1");
        panel1.add(label1);
        tabbedPane.addTab("Tab 1", panel1);

        // Tab 2
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.WHITE);
        JLabel label2 = new JLabel("This is Tab 2");
        panel2.add(label2);
        tabbedPane.addTab("Tab 2", panel2);

        // Tab 3
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.WHITE);
        JLabel label3 = new JLabel("This is Tab 3");
        panel3.add(label3);
        tabbedPane.addTab("Tab 3", panel3);

        // Tab 4
        JPanel panel4 = new JPanel();
        panel4.setBackground(Color.WHITE);
        JLabel label4 = new JLabel("This is Tab 4");
        panel4.add(label4);
        tabbedPane.addTab("Tab 4", panel4);

        // Thêm JTabbedPane vào JFrame
        frame.add(tabbedPane);

        // Hiển thị JFrame
        frame.setVisible(true);
    }
}
