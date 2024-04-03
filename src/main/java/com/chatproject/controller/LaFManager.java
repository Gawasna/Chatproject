/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chatproject.controller;

/**
 *
 * @author hungl
 */
import javax.swing.*;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

public class LaFManager {
    private static LaFManager instance;
    private List<JFrame> registeredFrames;

    private LaFManager() {
        registeredFrames = new ArrayList<>();
    }

    public static LaFManager getInstance() {
        if (instance == null) {
            instance = new LaFManager();
        }
        return instance;
    }

    public void registerFrame(JFrame frame) {
        registeredFrames.add(frame);
    }

    public void changeLookAndFeel(String lookAndFeel) throws UnsupportedLookAndFeelException {
        try {
            UIManager.setLookAndFeel(lookAndFeel);
            for (JFrame frame : registeredFrames) {
                SwingUtilities.updateComponentTreeUI(frame);
            }
            updateMenuItemState(lookAndFeel); // Cập nhật trạng thái của menu item
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }

    private void updateMenuItemState(String selectedLookAndFeel) {
        // Duyệt qua các JMenuItem trong jMenu3 (Menu "Theme")
        for (JFrame frame : registeredFrames) {
            for (Component menuComponent : frame.getJMenuBar().getComponents()) {
                if (menuComponent instanceof JMenu) {
                    JMenu menu = (JMenu) menuComponent;
                    for (Component subComponent : menu.getMenuComponents()) {
                        if (subComponent instanceof JMenuItem) {
                            JMenuItem jMenuItem = (JMenuItem) subComponent;
                            // Nếu JMenuItem đang xét chính là giao diện đã được chọn, đặt trạng thái cho nó
                            if (jMenuItem.getText().equals(selectedLookAndFeel)) {
                                // Đặt trạng thái cho JMenuItem ở đây (ví dụ: thêm dấu tích)
                                jMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dailylog/resources/selected.png")));
                            } else {
                                // Đặt trạng thái cho JMenuItem ở đây (ví dụ: xoá dấu tích)
                                jMenuItem.setIcon(null);
                            }
                        }
                    }
                }
            }
        }
    }
}

