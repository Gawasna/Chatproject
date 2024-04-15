package com.chatproject.controller;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.random.RandomGenerator;
import javax.swing.JDialog;
public class LoginHandler {
    public static void NonFunction() {
        String nonfunc = "Tính năng đang được phát triển !";
        JOptionPane.showMessageDialog(null, nonfunc);
    }
    public static boolean checkLoginSyntax(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Vui lòng nhập đầy đủ tên tài khoản và mật khẩu.");
            return false;
        }
        if (username.length() < 5 || username.contains(" ") || !username.matches("[a-zA-Z0-9]+") || !hasMinimumLetters(username) || startsWithNumber(username)) {
            System.out.println("Tên tài khoản không hợp lệ. Tên tài khoản phải có ít nhất 5 ký tự, không chứa kí tự đặc biệt và không có dấu cách. Tên tài khoản phải có ít nhất 5 chữ cái và không bắt đầu bằng số.");
            return false;
        }
        if (password.length() < 8 || password.contains(" ")) {
            System.out.println("Mật khẩu không hợp lệ. Mật khẩu phải có ít nhất 8 ký tự và không chứa dấu cách.");
            return false;
        }
        return true;
    }
    private static boolean hasMinimumLetters(String username) {
        int countLetters = 0;
        for (char c : username.toCharArray()) {
            if (Character.isLetter(c)) {
                countLetters++;
            }
        }
        return countLetters >= 5;
    }
    private static boolean startsWithNumber(String username) {
        return Character.isDigit(username.charAt(0));
    }
    public static boolean checkAccountInDatabase(String username, String password) {
        boolean result = false;
        try {
            String url = "jdbc:mysql://localhost:3306/clientchatdb";
            String dbUsername = "root";
            String dbPassword = "";
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = true; 
                System.out.println("Tài khoản tồn tại");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi hoặc tài khoản không tồn tại");
        }
        return result;
    }
    
    public String generateGuestUsername() {
        Random random = new Random();
        int randomNumber = random.nextInt(90000) + 10000;
        return "Guest@" + randomNumber;
    }
}
