/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chatproject.controller;

/**
 *
 * @author hungl
 */
import java.awt.Component;
import javax.swing.*;

public class LookAndFeelManager {
    public static void changeLookAndFeel(JFrame frame, String lookAndFeel) throws UnsupportedLookAndFeelException {
        try {
            UIManager.setLookAndFeel(lookAndFeel);
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }

    public static void updateFrameLookAndFeel(JFrame frame, String selectedLookAndFeel) {
        JMenuBar menuBar = frame.getJMenuBar();
        if (menuBar != null) {
            for (int i = 0; i < menuBar.getMenuCount(); i++) {
                JMenu menu = menuBar.getMenu(i);
                updateMenuItemsLookAndFeel(menu, selectedLookAndFeel);
            }
        }
    }

    private static void updateMenuItemsLookAndFeel(JMenu menu, String selectedLookAndFeel) {
        for (Component menuComponent : menu.getMenuComponents()) {
            if (menuComponent instanceof JMenuItem) {
                JMenuItem jMenuItem = (JMenuItem) menuComponent;
                if (jMenuItem.getText().equals(selectedLookAndFeel)) {
                    jMenuItem.setIcon(new ImageIcon(LookAndFeelManager.class.getResource("/com/dailylog/resources/selected.png")));
                } else {
                    jMenuItem.setIcon(null);
                }
            }
        }
    }
}
