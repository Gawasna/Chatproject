/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chatproject.view;

/**
 *
 * @author hungl
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JPasswordField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

// Lớp PlaceHolder cho JPasswordField
class PlaceholderPasswordField extends JPasswordField implements FocusListener, CaretListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String placeholder;
    private boolean isPlaceholderVisible = true;
    
    public PlaceholderPasswordField(String placeholder) {
        this.placeholder = placeholder;
        addCaretListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isPlaceholderVisible && (getText().isEmpty() || hasFocus())) {
            Font sansSerifFont = new Font(Font.SANS_SERIF, Font.ITALIC, getHeight() / 2);
            g.setFont(sansSerifFont);
            g.setColor(Color.GRAY);
            int textWidth = g.getFontMetrics().stringWidth(placeholder);
            int xShift = getWidth() / 30;
            int x = xShift;
            int y = (getHeight() - g.getFontMetrics().getHeight()) / 2 + g.getFontMetrics().getAscent();
            g.drawString(placeholder, x, y);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        isPlaceholderVisible = false;
        repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getPassword().length == 0) {
            isPlaceholderVisible = true;
            repaint();
        }
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        if (getPassword().length == 0) {
            isPlaceholderVisible = true;
        } else {
            isPlaceholderVisible = false;
        }
        repaint();
    }
}
