/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chatproject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

public class LookAndFeelManager {
    public static final String DEFAULT_LAF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    public static final String NIMBUS_LAF = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
    public static final String WINDOW_LAF = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    public static final String WINDOW_CLASSIC_LAF = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";

    private static String currentLookAndFeel;
    private static List<javax.swing.JFrame> registeredFrames = new ArrayList<>();

    public static void registerFrame(javax.swing.JFrame frame) {
        registeredFrames.add(frame);
    }

    public static void changeLookAndFeelForAllFrames(String lookAndFeel) throws ClassNotFoundException {
        try {
            for (javax.swing.JFrame frame : registeredFrames) {
                javax.swing.UIManager.setLookAndFeel(lookAndFeel);
                javax.swing.SwingUtilities.updateComponentTreeUI(frame);
            }
            currentLookAndFeel = lookAndFeel;
        } catch (UnsupportedLookAndFeelException e) {
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(LookAndFeelManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getCurrentLookAndFeel() {
        return currentLookAndFeel;
    }
}
