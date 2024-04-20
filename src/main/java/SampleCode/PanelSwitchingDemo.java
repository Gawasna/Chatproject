package SampleCode;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelSwitchingDemo extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel, controlPanel;

    public PanelSwitchingDemo() {
        // Thiết lập JFrame
        setTitle("Panel Switching Demo");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tạo CardLayout cho mainPanel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Tạo controlPanel với 3 nút để chuyển đổi giao diện
        controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(50, 300));
        JButton button1 = new JButton("Panel 1");
        JButton button2 = new JButton("Panel 2");
        JButton button3 = new JButton("Panel 3");
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        controlPanel.add(button1);
        controlPanel.add(button2);
        controlPanel.add(button3);

        // Tạo 3 panel lớn với giao diện ngẫu nhiên
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.RED);
        panel1.add(new JLabel("Panel 1"));

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.GREEN);
        panel2.add(new JLabel("Panel 2"));

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.BLUE);
        panel3.add(new JLabel("Panel 3"));

        // Thêm các panel vào mainPanel với tên định danh
        mainPanel.add(panel1, "Panel 1");
        mainPanel.add(panel2, "Panel 2");
        mainPanel.add(panel3, "Panel 3");

        // Thêm controlPanel và mainPanel vào JFrame
        getContentPane().add(controlPanel, BorderLayout.WEST);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PanelSwitchingDemo();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Xử lý sự kiện khi người dùng nhấn vào một nút
        String command = e.getActionCommand();
        cardLayout.show(mainPanel, command);
    }
}
