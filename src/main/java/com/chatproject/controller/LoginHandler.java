/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author hungl
 */
public class LoginHandler {
    
    public static void NonFunction() {
        String nonfunc = "Tính năng đang được phát triển !";
        //JOptionPane op = new JOptionPane();
        JOptionPane.showMessageDialog(null, nonfunc);
    }
    private boolean FillEnough;
    //method kiểm tra 3 điều kiện

    //Kiểm tra đăng nhập hợp lệ
    /*public boolean CheckVaildInput() {
        if (FillEnough == true || TrueTypeInput == true || AccountCheck==true) {
            return true;
        } else {
        return false;
        }
    }*/
    // Method kiểm tra cú pháp đăng nhập
    public static boolean checkLoginSyntax(String username, String password) {
        // Kiểm tra xem đã nhập đủ thông tin tài khoản và mật khẩu chưa
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Vui lòng nhập đầy đủ tên tài khoản và mật khẩu.");
            return false;
        }

        // Kiểm tra cú pháp của tên tài khoản
        if (username.length() < 5 || username.contains(" ") || !username.matches("[a-zA-Z0-9]+") || !hasMinimumLetters(username) || startsWithNumber(username)) {
            System.out.println("Tên tài khoản không hợp lệ. Tên tài khoản phải có ít nhất 5 ký tự, không chứa kí tự đặc biệt và không có dấu cách. Tên tài khoản phải có ít nhất 5 chữ cái và không bắt đầu bằng số.");
            return false;
        }

        // Kiểm tra cú pháp của mật khẩu
        if (password.length() < 8 || password.contains(" ")) {
            System.out.println("Mật khẩu không hợp lệ. Mật khẩu phải có ít nhất 8 ký tự và không chứa dấu cách.");
            return false;
        }

        // Nếu tất cả các điều kiện đều đúng
        return true;
    }

// Kiểm tra xem tên tài khoản có ít nhất 5 chữ cái hay không
    private static boolean hasMinimumLetters(String username) {
        int countLetters = 0;
        for (char c : username.toCharArray()) {
            if (Character.isLetter(c)) {
                countLetters++;
            }
        }
        return countLetters >= 5;
    }

// Kiểm tra xem tên tài khoản có bắt đầu bằng số hay không
    private static boolean startsWithNumber(String username) {
        return Character.isDigit(username.charAt(0));
    }

    // Method kiểm tra tài khoản có trong cơ sở dữ liệu không
    public static boolean checkAccountInDatabase(String username, String password) {
        boolean result = false;
        // Kết nối tới cơ sở dữ liệu và kiểm tra tài khoản
        try {
            // Thay đổi thông tin kết nối dựa trên cấu hình của cơ sở dữ liệu của bạn
            String url = "jdbc:mysql://localhost:3306/your_database"; //thay cơ sở dữ liệu vào
            String dbUsername = "your_username";
            String dbPassword = "your_password";
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = true; // Tài khoản tồn tại trong cơ sở dữ liệu
                System.out.println("Tài khoản tồn tại");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi hoặc tài khoản không tồn tại");
        }
        return result;
    }
    
    public static void GuestLogin () {
        JDialog jd = new JDialog();
        jd.setVisible(true);
    }
    public String generateGuestUsername() {
        Random random = new Random();
        int randomNumber = random.nextInt(90000) + 10000; // Tạo số ngẫu nhiên từ 10000 đến 99999
        return "Guest@" + randomNumber;
    }
}
